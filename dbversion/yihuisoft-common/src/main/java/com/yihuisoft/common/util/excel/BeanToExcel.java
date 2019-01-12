package com.yihuisoft.common.util.excel;

import com.yihuisoft.common.util.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Bean到Excel类
 * @author tonywu
 * @version v1.0.0
 */
public class BeanToExcel {
    /**
     * 获得Workbook对象
     *
     * @param list 数据集合
     * @return Workbook
     * @throws Exception
     */
    public static <T> Workbook getWorkBook(List<T> list,ExcelDataFormatter edf) throws Exception {
        // 创建工作簿
        Workbook wb = new XSSFWorkbook();
//        Workbook wb = new SXSSFWorkbook();//poi读写出现问题

        if (list == null || list.size() == 0)
            return wb;

        // 创建一个工作表sheet
        Sheet sheet = wb.createSheet();
        // 申明行
        Row row = sheet.createRow(0);
        // 申明单元格
        Cell cell = null;

        CreationHelper createHelper = wb.getCreationHelper();

        Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(list.get(0).getClass());

        XSSFCellStyle titleStyle = (XSSFCellStyle) wb.createCellStyle();
        titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        // 设置前景色
        titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(159, 213, 183)));
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);

        Font font = wb.createFont();
        font.setColor(HSSFColor.BROWN.index);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 设置字体
        titleStyle.setFont(font);

        int columnIndex = 0;
        Excel excel = null;
        for (Field field : fields) {
            field.setAccessible(true);
            excel = field.getAnnotation(Excel.class);
            if (excel == null || excel.skip()) {
                continue;
            }
            // 列宽注意乘256
            sheet.setColumnWidth(columnIndex, excel.width() * 256);
            // 写入标题
            cell = row.createCell(columnIndex);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(excel.name());

            columnIndex++;
        }

        int rowIndex = 1;

        CellStyle cs = wb.createCellStyle();

        for (T t : list) {
            row = sheet.createRow(rowIndex);
            columnIndex = 0;
            Object o = null;
            for (Field field : fields) {

                field.setAccessible(true);

                // 忽略标记skip的字段
                excel = field.getAnnotation(Excel.class);
                if (excel == null || excel.skip()) {
                    continue;
                }
                // 数据
                cell = row.createCell(columnIndex);

                o = field.get(t);

                // 如果数据为空，跳过
                if (o == null)
                    continue;

                // 处理日期类型
                if (o instanceof Date) {
                    // excel.dateFormat()获取注解的日期格式，默认yyyy-MM-dd HH:mm:ss
                    cs.setDataFormat(createHelper.createDataFormat().getFormat(excel.dateFormat()));
                    cell.setCellStyle(cs);
                    cell.setCellValue((Date) field.get(t));
                } else if (o instanceof Double || o instanceof Float) {// 浮点数
                    cell.setCellValue(field.get(t).toString());
                    if (excel.precision() != -1) {
                        cell.setCellValue(new BigDecimal(field.get(t).toString()).setScale(excel.precision(), excel.round() ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_FLOOR).toString());
                    }
                } else if (o instanceof BigDecimal) {// BigDecimal
                    cell.setCellValue((field.get(t).toString()));
                    if (excel.precision() != -1) {
                        cell.setCellValue(new BigDecimal(field.get(t).toString()).setScale(excel.precision(), excel.round() ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_FLOOR).toString());
                    }
                } else if (o instanceof Boolean) {// 布尔类型
                    Boolean bool = (Boolean) field.get(t);
                    if (edf == null) {
                        cell.setCellValue(bool);
                    } else {
                        Map<String, String> map = edf.get(field.getName());
                        if (map == null) {
                            cell.setCellValue(bool);
                        } else {
                            cell.setCellValue(map.get(bool.toString().toLowerCase()));
                        }
                    }

                } else if (o instanceof Integer) {// 整型

                    Integer intValue = (Integer) field.get(t);
                    String intValueStr = String.valueOf(excel.isAmount()?new BigDecimal(intValue).divide(new BigDecimal(100)).toString():intValue.toString());
                    if (edf == null) {
                        cell.setCellValue(intValueStr);
                    } else {
                        Map<String, String> map = edf.get(field.getName());
                        if (map == null) {
                            cell.setCellValue(intValueStr);
                        } else {
                            cell.setCellValue(map.get(intValue.toString()));
                        }
                    }
                } else {
                    cell.setCellValue(field.get(t).toString());
                }

                columnIndex++;
            }

            rowIndex++;
        }

        return wb;
    }

    /**
     * 将数据写入到EXCEL文档
     *
     * @param list     数据集合
     * @param edf      数据格式化，比如有些数字代表的状态，像是0:女，1：男，或者0：正常，1：锁定，变成可读的文字
     *                 该字段仅仅针对Boolean,Integer两种类型作处理
     * @param filePath 文件路径
     * @throws Exception
     */
    public static <T> void writeToFile(List<T> list, ExcelDataFormatter edf, String filePath) throws Exception {
        FileOutputStream out = null;
        try {
            // 创建并获取工作簿对象
            Workbook wb = getWorkBook(list, edf);
            // 写入到文件
            out = new FileOutputStream(filePath);
            wb.write(out);
            out.close();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e);
        } finally {
            try {
                if(out!=null){
                    out.close();}
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println(e);
            }
        }
    }

    /**
     * 将数据写入到EXCEL文档
     *
     * @param list     数据集合
     * @param edf      数据格式化，比如有些数字代表的状态，像是0:女，1：男，或者0：正常，1：锁定，变成可读的文字
     *                 该字段仅仅针对Boolean,Integer两种类型作处理
     * @param fileName 文件路径
     * @throws Exception
     * @update:gwn
     */
//    public static <T> void writeToFile(List<T> list, ExcelDataFormatter edf, HttpServletResponse response, String fileName) throws Exception {
//        // 创建并获取工作簿对象
//        Workbook wb = getWorkBook(list, edf);
//        // 写入到文件
//        //FileOutputStream out = new FileOutputStream(filePath);
//        OutputStream out = null;
//        try {
//            out = response.getOutputStream();
//            response.setContentType("application/x-msdownload");
//            response.setHeader("Content-Disposition", "attachment; filename="
//                    + URLEncoder.encode(fileName + ".xls", "UTF-8"));
//            wb.write(out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
    public static <T> void writeToFile(List<T> list, ExcelDataFormatter edf, HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {

        final String userAgent = request.getHeader("USER-AGENT");

        // 创建并获取工作簿对象
        Workbook wb = getWorkBook(list, edf);
        // 写入到文件
        //FileOutputStream out = new FileOutputStream(filePath);
        OutputStream out = null;
        try {
            String finalFileName = null;
            if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                finalFileName = new String(fileName.getBytes("ISO8859-1"), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
            }


            out = response.getOutputStream();
            response.setContentType("application/x-msdownload");
//            response.setHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");
            if(list!=null &&list.size()>0){

                wb.write(out);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e);
        } finally {
            try {
                if(out!=null){
                out.close();}
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println(e);
            }
        }




    }

}