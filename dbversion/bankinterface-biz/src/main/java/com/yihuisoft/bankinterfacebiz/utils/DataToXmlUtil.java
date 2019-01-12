package com.yihuisoft.bankinterfacebiz.utils;

import com.yihuisoft.bankinterfacebiz.entity.BankData;
import com.yihuisoft.common.InDTO;
import com.yihuisoft.bankinterfacebiz.entity.BankMapping;
import org.dom4j.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataToXmlUtil {

    public String rspXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<service>" +
            "<SYS_HEAD>" +
            "<SvcCd>301030018</SvcCd>" +
            "<SvcScn>05</SvcScn>" +
            "<CnsmSysId>304400</CnsmSysId>" +
            "<CnsmSysSeqNo>30440020180123114153610422</CnsmSysSeqNo>" +
            "<TranRetSt>S</TranRetSt>" +
            "<RetInf>" +
            "<RetCd>000000</RetCd>" +
            "<RetMsg>交易成功</RetMsg>" +
            "</RetInf>" +
            "<SrcSysSeqNo>30440020180123114153610422</SrcSysSeqNo>" +
            "<SrcSysId>304400</SrcSysId>" +
            "</SYS_HEAD>" +
            "<APP_HEAD></APP_HEAD>" +
            "<BODY>" +
            "<VerNo>1.0</VerNo>" +
            "<VrfyNo>304400</VrfyNo>" +
            "<TranCd>qurgoods</TranCd>" +
            "<RqsTm>114153</RqsTm>" +
            "<TotRcrdNum>8</TotRcrdNum>" +
            "<TotPgNo>1</TotPgNo>" +
            "<PgRcrdNo>8</PgRcrdNo>" +
            "<GdsMssgArray>" +
            "<CmdtyCd>17080001</CmdtyCd>" +
            "<CmdtyNm>健康关怀</CmdtyNm>" +
            "<CmdtyDsc>高端客户兑换该权益后本人或家庭核心成员可享受一次高端健康体检服务，由专家对体检报告进行解读。                                              </CmdtyDsc>" +
            "<CmdtyPntsPrc>90.0</CmdtyPntsPrc>" +
            "<CmdtyCashPrc>0.0</CmdtyCashPrc>" +
            "<GdsTm>20170809 17:04:34</GdsTm>" +
            "<CmdtyLvlScr></CmdtyLvlScr>" +
            "<PhotoNo>tj</PhotoNo>" +
            "<CnsmRuleID></CnsmRuleID>" +
            "<CnsmRuleDsc></CnsmRuleDsc>" +
            "</GdsMssgArray>" +
            "<GdsMssgArray>" +
            "<CmdtyCd>17080006</CmdtyCd>" +
            "<CmdtyNm>300元健康礼包</CmdtyNm>" +
            "<CmdtyDsc>财富中心健康大礼包，含健康管理、疾病诊疗、急救通道三大类别，可选其中一项                                    </CmdtyDsc>" +
            "<CmdtyPntsPrc>6.0</CmdtyPntsPrc>" +
            "<CmdtyCashPrc>0.0</CmdtyCashPrc>" +
            "<GdsTm>20170809 17:04:34</GdsTm>" +
            "<CmdtyLvlScr></CmdtyLvlScr>" +
            "<PhotoNo>tj</PhotoNo>" +
            "<CnsmRuleID></CnsmRuleID>" +
            "<CnsmRuleDsc></CnsmRuleDsc>" +
            "</GdsMssgArray>" +
            "<GdsMssgArray>" +
            "<CmdtyCd>17080007</CmdtyCd>" +
            "<CmdtyNm>500元健康礼包</CmdtyNm>" +
            "<CmdtyDsc>财富中心健康大礼包，含健康管理、疾病诊疗、急救通道三大类别，可选其中两项                                    </CmdtyDsc>" +
            "<CmdtyPntsPrc>10.0</CmdtyPntsPrc>" +
            "<CmdtyCashPrc>0.0</CmdtyCashPrc>" +
            "<GdsTm>20170809 17:04:34</GdsTm>" +
            "<CmdtyLvlScr></CmdtyLvlScr>" +
            "<PhotoNo>tj</PhotoNo>" +
            "<CnsmRuleID></CnsmRuleID>" +
            "<CnsmRuleDsc></CnsmRuleDsc>" +
            "</GdsMssgArray>" +
            "<GdsMssgArray>" +
            "<CmdtyCd>17080008</CmdtyCd>" +
            "<CmdtyNm>800元健康礼包</CmdtyNm>" +
            "<CmdtyDsc>财富中心健康大礼包，含健康管理、疾病诊疗、急救通道三大类别，可选其中三项                                    </CmdtyDsc>" +
            "<CmdtyPntsPrc>16.0</CmdtyPntsPrc>" +
            "<CmdtyCashPrc>0.0</CmdtyCashPrc>" +
            "<GdsTm>20170809 17:04:34</GdsTm>" +
            "<CmdtyLvlScr></CmdtyLvlScr>" +
            "<PhotoNo>tj</PhotoNo>" +
            "<CnsmRuleID></CnsmRuleID>" +
            "<CnsmRuleDsc></CnsmRuleDsc>" +
            "</GdsMssgArray>" +
            "<GdsMssgArray>" +
            "<CmdtyCd>17080010</CmdtyCd>" +
            "<CmdtyNm>保管箱C箱</CmdtyNm>" +
            "<CmdtyDsc>保管箱业务是我行接受客户委托，以出租保管箱的形式代客户保管文件、有价证券以及贵重物品的服务项目。服务特色安全私属：安全、保密、防火、防潮、防盗，是存放贵重物品和重要档案的最佳场所。存取便利：专柜专人开箱服务。租期灵活：分A-F六种规格，最大的高120cm，最小的高8cm，分三个月、六个月、一年、一年以上等不同期限。手续简便：持身份证、兰州银行卡即可办理。保管箱地址：兰州银行天河管理行（位于兰州市城关区东岗西路449" +
            "号，飞天大酒店西侧）。咨询电话：0931-8722971。本权益服务提供的保管箱C箱尺寸为200mm×320mm×610mm，期限为1年。" +
            "</CmdtyDsc>" +
            "<CmdtyPntsPrc>30.0</CmdtyPntsPrc>" +
            "<CmdtyCashPrc>0.0</CmdtyCashPrc>" +
            "<GdsTm>20170809 17:04:34</GdsTm>" +
            "<CmdtyLvlScr></CmdtyLvlScr>" +
            "<PhotoNo>bxx</PhotoNo>" +
            "<CnsmRuleID></CnsmRuleID>" +
            "<CnsmRuleDsc></CnsmRuleDsc>" +
            "</GdsMssgArray>" +
            "<GdsMssgArray>" +
            "<CmdtyCd>17080009</CmdtyCd>" +
            "<CmdtyNm>1000元健康礼包</CmdtyNm>" +
            "<CmdtyDsc>财富中心健康大礼包，含健康管理、疾病诊疗、急救通道三大类别，可选其中四项。                                     </CmdtyDsc>" +
            "<CmdtyPntsPrc>20.0</CmdtyPntsPrc>" +
            "<CmdtyCashPrc>0.0</CmdtyCashPrc>" +
            "<GdsTm>20170809 17:04:34</GdsTm>" +
            "<CmdtyLvlScr></CmdtyLvlScr>" +
            "<PhotoNo>tj</PhotoNo>" +
            "<CnsmRuleID></CnsmRuleID>" +
            "<CnsmRuleDsc></CnsmRuleDsc>" +
            "</GdsMssgArray>" +
            "<GdsMssgArray>" +
            "<CmdtyCd>17080002</CmdtyCd>" +
            "<CmdtyNm>中川机场贵宾厅</CmdtyNm>" +
            "<CmdtyDsc>专属贵宾室候机休息，提供贵宾通道、专用安检通道、代客托运行李等服务；提供电视、时事报刊、杂志、上网等服务项目，提供五种（含）以上冷热饮品和小食品；提供航班信息问讯及登机引导服务。" +
            "</CmdtyDsc>" +
            "<CmdtyPntsPrc>2.0</CmdtyPntsPrc>" +
            "<CmdtyCashPrc>0.0</CmdtyCashPrc>" +
            "<GdsTm>20170828 16:56:27</GdsTm>" +
            "<CmdtyLvlScr></CmdtyLvlScr>" +
            "<PhotoNo>zcjc</PhotoNo>" +
            "<CnsmRuleID></CnsmRuleID>" +
            "<CnsmRuleDsc></CnsmRuleDsc>" +
            "</GdsMssgArray>" +
            "<GdsMssgArray>" +
            "<CmdtyCd>17090001</CmdtyCd>" +
            "<CmdtyNm>西客站贵宾厅</CmdtyNm>" +
            "<CmdtyDsc>兰州西客站二楼兰州银行VIP休息室休息（包括茶点，电视报刊、互联网商务、登车提醒、行李寄存及协助）独立贵宾通道、送站服务等服务。" +
            "</CmdtyDsc>" +
            "<CmdtyPntsPrc>1.0</CmdtyPntsPrc>" +
            "<CmdtyCashPrc>0.0</CmdtyCashPrc>" +
            "<GdsTm>20170901 10:35:06</GdsTm>" +
            "<CmdtyLvlScr></CmdtyLvlScr>" +
            "<PhotoNo>xkz</PhotoNo>" +
            "<CnsmRuleID></CnsmRuleID>" +
            "<CnsmRuleDsc></CnsmRuleDsc>" +
            "</GdsMssgArray>" +
            "</BODY>" +
            "</service>";



    public String getSendXml(String struc, InDTO inDTO){


        return  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+toXmlStr(struc,inDTO);
    }


    /***
     * 发送报文
     * 将 struc （是发送报文） 与  inDTO 绑定在一起
     * @param struc
     * @param inDTO
     * @return
     */
    private String toXmlStr(String struc, InDTO inDTO) {
        StringBuffer sb = new StringBuffer();

        String[] one = struc.split("\n");
        int index = 0;
        while ( index <one.length) {
            // [0]  标签  1 注释  2 译会对应属性      3 默认值         4 标识
            String[] two = one[index].split(",");
            if (two[4].equals("0")) {
                if (!(inDTO.get(two[2]) == null || inDTO.get(two[2]).equals(""))) {
                    sb.append("<" + two[0] + ">" + inDTO.get(two[2]) + "</" + two[0] + ">");
                } else {
                    if (!(two[3] == null || two[3].equals(""))) {
                        sb.append("<" + two[0] + ">" + two[3] + "</" + two[0] + ">");
                    } else {
                        //如果这里   命名标准的话，就 不用首字母小写了。   （是用银行的  标签值为主    后面看情况可以换成译会的）
                        String tempStr = (String)inDTO.get(String.valueOf(two[0].charAt(0)).toLowerCase() + two[0].substring(1));
                        // 参数为空处理  可以不要处理的
                        if(tempStr==null){
                            tempStr="";
                        }else if(tempStr.equals("")){
                            tempStr="";
                        }
                        sb.append("<" + two[0] + ">" + tempStr+ "</" + two[0] + ">");
                    }
                }
            } else if (two[4].equals("1")) {
                // [0]  标签  1 注释  2 译会对应属性      3 默认值         4 标识
                sb.append("<" + two[0] + ">");

              Object obj =  inDTO.get(two[2]);
              if(obj==null){
                  obj = inDTO.get(two[0]);
              }
              if(obj!=null){ //议会属性
                  InDTO inDTO1  = new InDTO();
                  String tempString  ="";
                  String crrOneStr = one[index];
                  while(true){
                      index++;
                      tempString +=one[index]+"\n";
                      String [] strings = one[index].split(",");
                      if(strings[0].equals(two[0])){
                          break;
                      }
                  }
                      if(obj.getClass().getName().equals("java.util.HashMap")){
                          inDTO1.setData((Map<String, Object>) obj);
                          String str = toXmlStr(tempString,inDTO1);
                          sb.append(str);
                      }else if(obj.getClass().getName().equals("java.util.ArrayList")) {
                          List<Map<String, Object>> mapArrayList  =  (ArrayList<Map<String, Object> >)obj;
                          inDTO1.setData(mapArrayList.get(0));
                          String str = toXmlStr(tempString,inDTO1);
                          sb.append(str);
                          tempString = crrOneStr+"\n"+tempString;
                          for(int i=1;i<mapArrayList.size();i++){
                              inDTO1.setData(mapArrayList.get(i));
                              String xstr = toXmlStr(tempString,inDTO1);
                              sb.append(xstr);
                          }

                      }
              }
            } else if (two[4].equals("-1")) {
                sb.append("</" + two[0] + ">");
            }
            index++;
        }
        return sb.toString();
    }



    /**
     * 得到银行的数据之后 转为 OutDTO
     *
     * @param xmlStr
     * @param struc
     * @return
     * @throws Exception
     */
    public Map<String, Object> strToMap(String xmlStr, String struc) throws Exception {
        Map<String, Object> smap = new HashMap<>();
        // 将字符串转为XML;//获取document对象,如果文档无节点，则会抛出Exception提前结束
        Document document = DocumentHelper.parseText(xmlStr);
        //获取根节点
        Element root = document.getRootElement();

        String[] one = struc.split("\n");
        int index = 0;

        while (index < one.length) {
            String str = one[index];
            // [0]  标签  1 备注  2 标识  3 译会对应属性   4 默认值
            String[] twoss = str.split(",");
            String[] two = new String[5];
            System.arraycopy(twoss, 0, two, 0, twoss.length);

            // ---
            String tempStr = two[2];
            //有与译会对应的属性
            if (tempStr != null) {
                tempStr = two[0];

                List<Element> elelist = root.selectNodes("//" + tempStr);
                List<Map<String, Object>> yihuiList = new ArrayList();

                if (elelist.size() == 1) {
                    //遍历当前节点的所有属性
                    for (Element node : elelist) {
                        //所有一级子节点的list
                        List<Element> listElement = node.elements();
                        if (listElement.size() <= 0) {
                            if (two[4].equals("0")) {
                                smap.put(two[2], node.getText());
                            } else {
                                //这里应该是一个对象
                                List<String[]> strList = new ArrayList<>();
                                while (true) {
                                    ++index;
                                    String tempSstr = one[index];
                                    // [0]  标签  1 备注  2 标识  3 译会对应属性   4 默认值
                                    String[] regs = tempSstr.split(",");
                                    String[] copyArr = new String[5];
                                    System.arraycopy(regs, 0, copyArr, 0, regs.length);

                                    if (two[0].equals(copyArr[0])) {
                                        break;
                                    } else {
                                        strList.add(copyArr);
                                    }
                                }
                                smap.put(two[2], modelToMapObj(xmlStr, node, strList));
                            }
                        }
                    }

                } else { //有很多
                    List<String[]> strList = new ArrayList<>();
                    while (true) {
                        ++index;
                        String tempSstr = one[index];
                        // [0]  标签  1 备注  2 标识  3 译会对应属性   4 默认值
                        String[] regs = tempSstr.split(",");
                        String[] copyArr = new String[5];
                        System.arraycopy(regs, 0, copyArr, 0, regs.length);

                        if (two[0].equals(copyArr[0])) {
                            break;
                        } else {
                            strList.add(copyArr);
                        }
                    }
                    //遍历当前节点的所有属性
                    for (Element node : elelist) {
                        yihuiList.add(modelToMapObj(xmlStr, node, strList));
                    }
                    smap.put(two[2], yihuiList);

                }
            }

            // --

            index++;
        }


        return smap;

    }


    /**
     * 将传进来的参数转为  Map
     *
     * @param xmlStr
     * @param node
     * @param strList
     * @return
     * @throws Exception
     */
    private Map<String, Object> modelToMapObj(String xmlStr, Element node, List<String[]> strList) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        int index = 0;

        while (index < strList.size()) {
            String[] two = strList.get(index);
            String nodeStr = node.element(two[0]).getText();
            if (two[4].equals("0")) {
                dataMap.put(two[2], nodeStr);
            } else if (two[4].equals("1")) {

                List<String[]> temList = new ArrayList<>();
                while (true) {
                    index++;
                    // [0]  标签  1 备注  2 标识  3 译会对应属性   4 默认值
                    String[] copyArr = strList.get(index);
                    if (two[0].equals(copyArr[0])) {
                        break;
                    } else {
                        temList.add(copyArr);
                    }
                }
                dataMap.put(two[2], modelToMapObj(xmlStr, node, temList));
            }
            index++;
        }
        return dataMap;
    }


    public static List<BankData> formatArrayStr(String struc) {
        List<BankData> list = new ArrayList<>();
        String[] one = struc.split("\n");
        int index = 0;
        while (index < one.length) {
            String str = one[index];
            // [0]  标签  1 备注  2 标识  3 译会对应属性   4 默认值
            String[] twoss = str.split(",");
            String[] two = new String[5];
            System.arraycopy(twoss, 0, two, 0, twoss.length);
            list.add(new BankData(two[0], two[1], two[4], two[2], two[3]));
            index++;
        }
        return list;
    }
}
