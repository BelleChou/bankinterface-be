package com.yihuisoft.bankinterfacebiz.test;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestXmlToMap {
    public static String reqXml = "services,这个是交易号,1,\n" +
            "SYS_HEAD,系统头部,1\n" +
            "SvcCd,服务代码,0,\n" +
            "SvcScn,服务场景,0\n" +
            "CnsmSysId,调用方系统Id,0\n" +
            "CnsmSysSeqNo,调用方系统流水号,0\n" +
            "TranRetSt,交易返回状态,0\n" +
            "RetInf,交易返回信息数组,1\n" +
            "RetCd,返回码,0\n" +
            "RetMsg,交易返回信息,0\n" +
            "RetInf,交易返回信息数组,2\n" +
            "SrcSysSeqNo,源发起系统流水号,0\n" +
            "SrcSysId,源发起系统Id,0\n" +
            "SYS_HEAD,系统头部,2\n" +
            "APP_HEAD,app头部,1\n" +
            "BranchId,编号,0\n" +
            "TlrNo,代码,0\n" +
            "TlrPswd，密码,0\n" +
            "TlrLvl,等级,0\n" +
            "TlrTp,标题,0\n" +
            "BsnSeqNo,编号,0\n" +
            "APP_HEAD,app头部,2\n" +
            "BODY,交易码,1\n" +
            "CstIdTp,客户标识类型,0,CstIdTpYiHui,默认值CstIdTp\n" +
            "CstId,客户标识,0,CstIdYiHui,默认值CstId\n" +
            "LoctStr,定位串,0,LoctStrYiHui,默认值LoctStr\n" +
            "QryLnNum,查询行数,0,QryLnNumYiHui,默认值QryLnNum\n" +
            "BODY,交易码,2\n" +
            "services,这个是交易号,2";


    @Test
    public void testXml() throws Exception {


    }

    Map<String, Object> smap = new HashMap<>();

    public void getNodes(Element node) {


    }
}
