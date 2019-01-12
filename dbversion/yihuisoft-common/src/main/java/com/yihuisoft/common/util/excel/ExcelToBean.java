package com.yihuisoft.common.util.excel;

import com.yihuisoft.common.util.ReflectUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读Excel到Bean类
 * @author tonywu
 * @version v1.0.0
 */
public class ExcelToBean{

    /**日期格式*/
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**次数*/
    private int etimes = 0;


    /**
     * 从文件读取数据，最好是所有的单元格都是文本格式，日期格式要求yyyy-MM-dd HH:mm:ss,布尔类型0：真，1：假
     *
     * @param edf
     *            数据格式化
     *
     * @param file
     *            Excel文件，支持xlsx后缀，xls的没写，基本一样
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> readFromFile(ExcelDataFormatter edf, File file, Class<?> clazz) throws Exception {
        Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(clazz);

        Map<String, String> textToKey = new HashMap<String, String>();

        Excel excel = null;
        for (Field field : fields) {
            excel = field.getAnnotation(Excel.class);
            if (excel == null || excel.skip()) {
                continue;
            }
            textToKey.put(excel.name(), field.getName());
        }

        InputStream is = new FileInputStream(file);

        Workbook wb = new XSSFWorkbook(is);

        Sheet sheet = wb.getSheetAt(0);
        Row title = sheet.getRow(0);
        // 标题数组，后面用到，根据索引去标题名称，通过标题名称去字段名称用到 textToKey
        String[] titles = new String[title.getPhysicalNumberOfCells()];
        for (int i = 0; i < title.getPhysicalNumberOfCells(); i++) {
            titles[i] = title.getCell(i).getStringCellValue();
        }

        List<E> list = new ArrayList<E>();

        E e = null;

        int rowIndex = 0;
        int columnCount = titles.length;
        Cell cell = null;
        Row row = null;

        for (Iterator<Row> it = sheet.rowIterator(); it.hasNext();) {

            row = it.next();
            if (rowIndex++ == 0) {
                continue;
            }

            if (row == null) {
                break;
            }

            e = (E)clazz.newInstance();

            for (int i = 0; i < columnCount; i++) {
                cell = row.getCell(i);
                if(null==cell)continue;
                etimes = 0;
                readCellContent(textToKey.get(titles[i]), fields, cell, e, edf);
            }
            list.add(e);
        }
        return list;
    }

    /**
     * main函数
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    }

    /**
     * 从单元格读取数据，根据不同的数据类型，使用不同的方式读取<br>
     * 有时候经常和我们期待的数据格式不一样，会报异常，<br>
     * 我们这里采取强硬的方式<br>
     * 使用各种方法，知道尝试到读到数据为止，然后根据Bean的数据类型，进行相应的转换<br>
     * 如果尝试完了（总共7次），还是不能得到数据，那么抛个异常出来，没办法了
     *
     * @param key
     *            当前单元格对应的Bean字段
     * @param fields
     *            Bean所有的字段数组
     * @param cell
     *            单元格对象
     * @param obj
     * @throws Exception
     */
    public void readCellContent(String key, Field[] fields, Cell cell, Object obj, ExcelDataFormatter edf) throws Exception {

        Object o = null;

        try {
            switch (cell.getCellType()) {
                case XSSFCell.CELL_TYPE_BOOLEAN:
                    o = cell.getBooleanCellValue();
                    break;
                case XSSFCell.CELL_TYPE_NUMERIC:
                    o = cell.getNumericCellValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        o = DateUtil.getJavaDate(cell.getNumericCellValue());
                    }
                    break;
                case XSSFCell.CELL_TYPE_STRING:
                    o = cell.getStringCellValue();
                    break;
                case XSSFCell.CELL_TYPE_ERROR:
                    o = cell.getErrorCellValue();
                    break;
                case XSSFCell.CELL_TYPE_BLANK:
                    o = null;
                    break;
                case XSSFCell.CELL_TYPE_FORMULA:
                    o = cell.getCellFormula();
                    break;
                default:
                    o = null;
                    break;
            }

            if (o == null)
                return;

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equals(key)) {
                    Boolean bool = true;
                    Map<String, String> map = null;
                    if (edf == null) {
                        bool = false;
                    } else {
                        map = edf.get(field.getName());
                        if (map == null) {
                            bool = false;
                        }
                    }

                    if (field.getType().equals(Date.class)) {
                        if (o.getClass().equals(Date.class)) {
                            field.set(obj, o);
                        } else {
                            //field.set(obj, sdf.parse(o.toString()));
                            field.set(obj,new SimpleDateFormat(DATE_FORMAT).format(o.toString()));
                        }
                    } else if (field.getType().equals(String.class)) {
                        if (o.getClass().equals(String.class)) {
                            field.set(obj, o);
                        } else {
                            field.set(obj, o.toString());
                        }
                    } else if (field.getType().equals(Long.class)) {
                        if (o.getClass().equals(Long.class)) {
                            field.set(obj, o);
                        } else {
                            field.set(obj, Long.parseLong(o.toString()));
                        }
                    } else if (field.getType().equals(Integer.class)) {
                        if (o.getClass().equals(Integer.class)) {
                            field.set(obj, o);
                        } else {
                            // 检查是否需要转换
                            String ostr = o.toString();
                            ostr = ostr.split("\\.").length>0?ostr.split("\\.")[0]:ostr;
                            if (bool) {
                                field.set(obj, map.get(ostr) != null ? Integer.parseInt(map.get(ostr)) : Integer.parseInt(ostr));
                            } else {
                                field.set(obj, Integer.parseInt(ostr));
                            }

                        }
                    } else if (field.getType().equals(BigDecimal.class)) {
                        if (o.getClass().equals(BigDecimal.class)) {
                            field.set(obj, o);
                        } else {
                            field.set(obj, BigDecimal.valueOf(Double.parseDouble(o.toString())));
                        }
                    } else if (field.getType().equals(Boolean.class)) {
                        if (o.getClass().equals(Boolean.class)) {
                            field.set(obj, o);
                        } else {
                            // 检查是否需要转换
                            if (bool) {
                                field.set(obj, map.get(o.toString()) != null ? Boolean.parseBoolean(map.get(o.toString())) : Boolean.parseBoolean(o.toString()));
                            } else {
                                field.set(obj, Boolean.parseBoolean(o.toString()));
                            }
                        }
                    } else if (field.getType().equals(Float.class)) {
                        if (o.getClass().equals(Float.class)) {
                            field.set(obj, o);
                        } else {
                            field.set(obj, Float.parseFloat(o.toString()));
                        }
                    } else if (field.getType().equals(Double.class)) {
                        if (o.getClass().equals(Double.class)) {
                            field.set(obj, o);
                        } else {
                            field.set(obj, Double.parseDouble(o.toString()));
                        }

                    }

                }
            }

        } catch (RuntimeException e){
            //throw e;
            System.out.println(e);
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println(ex);
            // 如果还是读到的数据格式还是不对，只能放弃了
            if (etimes > 7) {
                //throw ex;
                System.out.println(ex);
            }
            etimes++;
            if (o == null) {
                readCellContent(key, fields, cell, obj, edf);
            }
        }
    }

}
