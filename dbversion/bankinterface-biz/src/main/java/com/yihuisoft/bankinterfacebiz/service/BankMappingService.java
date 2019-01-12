package com.yihuisoft.bankinterfacebiz.service;

import com.yihuisoft.bankinterfacebiz.entity.BankData;
import com.yihuisoft.common.InDTO;
import com.yihuisoft.common.OutDTO;
import com.yihuisoft.bankinterfacebiz.entity.BankMapping;
import com.yihuisoft.bankinterfacebiz.mapper.bank.BankMappingMapper;
import com.yihuisoft.bankinterfacebiz.utils.DataToXmlUtil;
import com.yihuisoft.common.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 银行  的xml 与译会的  属性对应 结构体现  Service
 *
 * @author isst
 * @version v1.0.0
 */
@Service
public class BankMappingService {

    @Autowired
    private BankMappingMapper bankMappingMapper;

    /***
     *当前的 调用  接口  调用的时候，不会管理 inDTO种的参数
     * @param inDTO    公共的InDTO
     * @return 公共的  OutDTO
     * @throws Exception   可能遇到一次 抛出由 让上一级处理
     */
    public OutDTO run(InDTO inDTO) throws Exception {


        ///  Long tranCode = Long.valueOf(String.valueOf(inDTO.get("interfaceNo")));


        DataToXmlUtil util = new DataToXmlUtil();

        OutDTO tempOutData = queryBankMapping(inDTO);
        Map<String, Object> map = tempOutData.getData();

        String reqXml =  map.get("send").toString();
        String receive =  map.get("receive").toString();

        String inXml = util.getSendXml(reqXml, inDTO);
        //银行发送
        /***
         * 这个地方发送银行报文，然后接收
         */
        System.out.println(inXml);

        /**
         * //第一个参数是 银行给的 ，第二个参数  是   配置好的
         */
        Map<String, Object> outMap = util.strToMap(util.rspXml, receive);

        OutDTO outDTO = new OutDTO();
        outDTO.setData(outMap);
        return outDTO;

    }

    /**
     * 根据 interfaceNo 查询  单个  BanknMapping
     *
     * @param inDTO
     * @return OutDTO    (send 与 receive  是 List 型)
     * @throws Exception
     */
    public OutDTO queryOneBankMapping(InDTO inDTO) throws Exception {
        Map<String, Object> date = new HashMap<>();

        String interfaceNo =  inDTO.get("interfaceNo").toString();
        if (interfaceNo.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"InterfaceNo"});
        }

        BankMapping bankMapping = bankMappingMapper.findByBankMappingInterfaceNo(interfaceNo);

        if (bankMapping == null) {
            throw new ApplicationException("ERRORCODE0020");
        }
        date.put("id", bankMapping.getId());
        date.put("interfaceNo", bankMapping.getInterfaceNo());
        date.put("remarks", bankMapping.getRemarks());
        date.put("writer", bankMapping.getWriter());
        date.put("createDate", bankMapping.getCreateDate());
        date.put("modifyTime", bankMapping.getModifyTime());
        date.put("send", DataToXmlUtil.formatArrayStr(bankMapping.getSend()));
        date.put("receive", DataToXmlUtil.formatArrayStr(bankMapping.getReceive()));

        OutDTO outDTO = new OutDTO();
        outDTO.setData(date);
        return outDTO;

    }

    /**
     * 根据 interfaceNo 查询  单个  BanknMapping  与  queryOneBankMapping（list）   一直    一个  发送和接收是  String
     * 另一个是  List
     *
     * @param inDTO
     * @return OutDTO               (send 与 receive  是String型)
     * @throws Exception
     */
    public OutDTO queryBankMapping(InDTO inDTO) throws Exception {
        Map<String, Object> date = new HashMap<>();

        String interfaceNo =  inDTO.get("interfaceNo").toString();
        System.out.println("查询的值是："+interfaceNo);
        if (interfaceNo.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"InterfaceNo"});
        }

        BankMapping bankMapping = bankMappingMapper.findByBankMappingInterfaceNo(interfaceNo);

        if (bankMapping == null) {
            throw new ApplicationException("ERRORCODE0020");
        }
        date.put("id", bankMapping.getId());
        date.put("interfaceNo", bankMapping.getInterfaceNo());
        date.put("remarks", bankMapping.getRemarks());
        date.put("writer", bankMapping.getWriter());
        date.put("createDate", bankMapping.getCreateDate());
        date.put("modifyTime", bankMapping.getModifyTime());
        date.put("send", bankMapping.getSend());
        date.put("receive", bankMapping.getReceive());

        OutDTO outDTO = new OutDTO();
        outDTO.setData(date);
        return outDTO;

    }

    /**
     * 增加一个新的 BankMapping   银行映射
     *
     * @param inDTO
     * @return OutDTO
     * @throws Exception
     */
    public OutDTO addBankMapping(InDTO inDTO) throws Exception {
        Map<String, Object> outMap = new HashMap<>(2);

        String interfaceNo =  inDTO.get("interfaceNo").toString();
        if (interfaceNo.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"InterfaceNo"});
        }
        String remarks =  inDTO.get("remarks").toString();
        if (remarks.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"remarks"});
        }
        String writer =  inDTO.get("writer").toString();
        if (writer.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"writer"});
        }
        // 时间会自己记录下来


        List<LinkedHashMap<String,String>>  linkedSend = (List<LinkedHashMap<String,String>>)inDTO.get("send");

        if (linkedSend.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"send"});
        }
        List<LinkedHashMap<String,String>>  linkedReceive = (List<LinkedHashMap<String,String>>)inDTO.get("receive");
        if (linkedReceive.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"receive"});
        }

        List<BankData> receive =  new ArrayList<>();


        List<BankData> send = new ArrayList<>();

        for (LinkedHashMap<String,String> linkedHashMap :linkedSend){
            send.add(new BankData(linkedHashMap.get("eleName"),linkedHashMap.get("eleRemarks"),linkedHashMap.get("eleFlag"),linkedHashMap.get("eleYihui"),linkedHashMap.get("eleDefault")));
        }
        for (LinkedHashMap<String,String> linkedHashMap :linkedReceive){
            receive.add(new BankData(linkedHashMap.get("eleName"),linkedHashMap.get("eleRemarks"),linkedHashMap.get("eleFlag"),linkedHashMap.get("eleYihui"),linkedHashMap.get("eleDefault")));
        }

        StringBuffer stringBuffer = new StringBuffer();
        for (BankData bankData : send) {
            stringBuffer.append(bankData.formatBankData());
        }
        StringBuffer stringBuffer1 = new StringBuffer();
        for (BankData bankData : receive) {
            stringBuffer1.append(bankData.formatBankData());
        }
        int rresult = bankMappingMapper.addOneBankMapping(new BankMapping(interfaceNo, remarks, writer, stringBuffer.toString(), stringBuffer1.toString()));

        if (rresult == 1) {

            outMap.put("message", "SUCCESS_MESSAGE");
            outMap.put("codeType", "SUCCESS_CODE");
        } else {
            outMap.put("codeType", "QUERY_FAIL_CODE");
            outMap.put("message", "QUERY_FAIL_MESSAGE");
        }

        OutDTO o = new OutDTO();
        o.setData(outMap);
        return o;
    }

    /**
     * author：BelleChou
     * 增加一条记录  send和receive是String类型
     * @param inDTO
     * @return
     * @throws Exception
     */
    public OutDTO addBankMappingString(InDTO inDTO) throws Exception {
        Map<String, Object> outMap = new HashMap<>(2);

        String interfaceNo =  inDTO.get("interfaceNo").toString();
        if (interfaceNo.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"InterfaceNo"});
        }
        String remarks =  inDTO.get("remarks").toString();
        if (remarks.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"remarks"});
        }
        String writer =  inDTO.get("writer").toString();
        if (writer.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"writer"});
        }
        // 时间会自己记录下来


        String  sendString = inDTO.get("send").toString();
        String receiveString = inDTO.get("receive").toString();
        if (sendString.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"send"});
        }

        if (receiveString.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"receive"});
        }

        int rresult = bankMappingMapper.addOneBankMapping(new BankMapping(interfaceNo, remarks, writer, sendString, receiveString));

        if (rresult == 1) {

            outMap.put("message", "SUCCESS_MESSAGE");
            outMap.put("codeType", "SUCCESS_CODE");
        } else {
            outMap.put("codeType", "QUERY_FAIL_CODE");
            outMap.put("message", "QUERY_FAIL_MESSAGE");
        }

        OutDTO o = new OutDTO();
        o.setData(outMap);
        return o;
    }




    /**
     * 更新 （修改） bankMapping 数据
     *
     * @param inDTO
     * @return OutDTO
     * @throws Exception
     */
    public OutDTO updateBankMapping(InDTO inDTO) throws Exception {
        Map<String, Object> outMap = new HashMap<>(2);

        String id =  inDTO.get("id").toString();
        if (id.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"id"});
        }
        String interfaceNo =  inDTO.get("interfaceNo").toString();
        String remarks =  inDTO.get("remarks").toString();
        String writer =  inDTO.get("writer").toString();
//        List<BankData> send = (List<BankData>) inDTO.get("send");
//        List<BankData> receive = (List<BankData>) inDTO.get("receive");
        List<LinkedHashMap<String,String>>  linkedSend = (List<LinkedHashMap<String,String>>)inDTO.get("send");
        List<LinkedHashMap<String,String>>  linkedReceive = (List<LinkedHashMap<String,String>>)inDTO.get("receive");

        List<BankData> receive =  new ArrayList<>();


        List<BankData> send = new ArrayList<>();

        for (LinkedHashMap<String,String> linkedHashMap :linkedSend){
            send.add(new BankData(linkedHashMap.get("eleName"),linkedHashMap.get("eleRemarks"),linkedHashMap.get("eleFlag"),linkedHashMap.get("eleYihui"),linkedHashMap.get("eleDefault")));
        }
        for (LinkedHashMap<String,String> linkedHashMap :linkedReceive){
            receive.add(new BankData(linkedHashMap.get("eleName"),linkedHashMap.get("eleRemarks"),linkedHashMap.get("eleFlag"),linkedHashMap.get("eleYihui"),linkedHashMap.get("eleDefault")));
        }


        StringBuffer stringBuffer = new StringBuffer();

        if (send != null) {
            for (BankData bankData : send) {
                stringBuffer.append(bankData.formatBankData());
            }
        }
        StringBuffer stringBuffer1 = new StringBuffer();
        if (send != null) {
            for (BankData bankData : receive) {
                stringBuffer1.append(bankData.formatBankData());
            }
        }

        int rresult = bankMappingMapper.updateOneBankMapping(new BankMapping(Long.valueOf(id), interfaceNo, remarks, writer, stringBuffer.length() <= 0 ? null : stringBuffer.toString(), stringBuffer1.length() <= 0 ? null : stringBuffer1.toString().toString()));

        if (rresult == 1) {

            outMap.put("message", "SUCCESS_MESSAGE");
            outMap.put("codeType", "SUCCESS_CODE");
        } else {
            outMap.put("codeType", "QUERY_FAIL_CODE");
            outMap.put("message", "QUERY_FAIL_MESSAGE");
        }


        OutDTO o = new OutDTO();
        o.setData(outMap);
        return o;
    }

    /**
     * 更新  其中send和receive是String类型
     * @param inDTO
     * @return
     * @throws Exception
     */
    public OutDTO updateBankMappingString(InDTO inDTO) throws Exception {
        Map<String, Object> outMap = new HashMap<>(2);

        String id =  inDTO.get("id").toString();
        if (id.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"id"});
        }
        String interfaceNo = (String) inDTO.get("interfaceNo");
        String remarks = (String)  inDTO.get("remarks");
        String writer = (String)  inDTO.get("writer");
        String send = (String)  inDTO.get("send");
        String receive = (String)  inDTO.get("receive");


        int rresult = bankMappingMapper.updateOneBankMapping(new BankMapping(Long.valueOf(id), interfaceNo, remarks, writer, send, receive));

        if (rresult == 1) {

            outMap.put("message", "SUCCESS_MESSAGE");
            outMap.put("codeType", "SUCCESS_CODE");
        } else {
            outMap.put("codeType", "QUERY_FAIL_CODE");
            outMap.put("message", "QUERY_FAIL_MESSAGE");
        }


        OutDTO o = new OutDTO();
        o.setData(outMap);
        return o;
    }

    /**
     * @param inDTO 输入
     * @return result
     * @author lican
     * @describe 查询消息发布信息(分页)
     */
    public OutDTO queryPage(InDTO inDTO) {
        OutDTO result = new OutDTO();
        Map<String, Object> moduleMap = inDTO.getData();
        //获取数据总记录数
        int sumCount = bankMappingMapper.selectCountBankMapping(moduleMap);
        //算出分页起始值
        Integer pageNo = inDTO.getPageNo();
        //分页显示条数
        Integer pageSize = inDTO.getPageSize();
        int start = ((pageNo - 1) * pageSize) + 1;
        int end = pageNo * pageSize;
        moduleMap.put("start", start);
        moduleMap.put("end", end);
        Map<String, Object> map = new HashMap<>(3);
        //获取分页数据
        List<BankMapping> banknoList = bankMappingMapper.selectPageBankMappingList(moduleMap);
        //目标数据
        if (banknoList != null && !banknoList.isEmpty()) {
            map.put("list", banknoList);
            map.put("message", "SUCCESS_MESSAGE");
            map.put("codeType", "SUCCESS_CODE");
            result.setSumCount(sumCount);
        } else {
            map.put("list", banknoList);
            map.put("codeType", "QUERY_FAIL_CODE");
            map.put("message", "QUERY_FAIL_MESSAGE");
        }
        result.setData(map);
        return result;
    }


    /**
     * 根据id删除一条数据
     *
     * @param inDTO
     * @return
     * @throws Exception
     */
    public OutDTO delOneBankMapping(InDTO inDTO) throws Exception {
        OutDTO result = new OutDTO();
        Map<String, Object> map = new HashMap<>(2);


        String id =  inDTO.get("id").toString();
        if (id.isEmpty()) {
            throw new ApplicationException("ERRORCODE0010", new String[]{"id"});
        }

        //目标数据
        if (bankMappingMapper.delOneBankMapping(Long.valueOf(id)) == 1) {
            map.put("message", "SUCCESS_MESSAGE");
            map.put("codeType", "SUCCESS_CODE");
        } else {
            map.put("codeType", "QUERY_FAIL_CODE");
            map.put("message", "QUERY_FAIL_MESSAGE");
        }
        result.setData(map);
        return result;
    }


}