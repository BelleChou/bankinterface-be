package com.yihuisoft.bankinterfacebiz.test;

import com.yihuisoft.bankinterfacebiz.entity.BankData;
import com.yihuisoft.bankinterfacebiz.service.BankMappingService;
import com.yihuisoft.bankinterfacebiz.utils.DataToXmlUtil;
import com.yihuisoft.common.InDTO;
import com.yihuisoft.common.OutDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *  用来测试   的
 *
 *
 *  删除  传一个id 就好了。
 */
public class TesetSendList extends BaseTest {
    @Autowired
    BankMappingService bankMappingService;


    /***
     *  测试   整个发送  与 接收的流程
     * @throws Exception
     */
    @Test
    public void testProcedure() throws Exception{
        InDTO inDTO = new InDTO();
        Map<String,Object> map = new HashMap<>();
        map.put("interfaceNo", "10086");
        map.put("a","a元素的值");
        Map<String,Object> bmap = new HashMap<>();
        bmap.put("c","第一个对象c元素的值");
        bmap.put("d","第一个对象d元素的值");
        bmap.put("yh","对象中的议会值");
        map.put("yihui","Mapp议会");
        map.put("yhObj",bmap); //使用议会属性
        //map.put("b",bmap);   //使用标签属性
        inDTO.setData(map);
        OutDTO outDTO= bankMappingService.queryBankMapping(inDTO);
        DataToXmlUtil du = new DataToXmlUtil();
        String xml = du.getSendXml(outDTO.get("send").toString(),inDTO);
        System.out.println("发送的报文：\n"+xml);
        //发送  -------------------------
        Map<String, Object> outMap =  du.strToMap(repXml,outDTO.get("send").toString());

        System.out.println(outMap.size());


    }




    /***
     *  测试一个对象
     * @throws Exception
     */
    @Test
    public void bankTests() throws Exception {
//        services,这个是交易号,,,1
//        a,测试a,yihui,default,0
//        b,测试一个对象,def,1
//        c,测试c,yh,def,0
//        d,cesDD,yh,def,0
//        b,测试一个对象,def,-1
//        services,这个是交易号,,,-1

        InDTO inDTO = new InDTO();
        Map<String,Object> map = new HashMap<>();
        map.put("interfaceNo", "10086");
        map.put("a","a元素的值");

        //对象
        Map<String,Object> bmap = new HashMap<>();
        bmap.put("c","第一个对象c元素的值");
        bmap.put("d","第一个对象d元素的值");
        bmap.put("yh","对象中的议会值");

        map.put("yihui","Mapp议会");


        map.put("yhObj",bmap); //使用议会属性
        //map.put("b",bmap);   //使用标签属性
        inDTO.setData(map);

        OutDTO outDTO= bankMappingService.queryBankMapping(inDTO);


        DataToXmlUtil du = new DataToXmlUtil();
        String xml = du.getSendXml(outDTO.get("send").toString(),inDTO);
        System.out.println(xml);


        //OutDTO outDTO = bankMappingService.run(inDTO);

     //   System.out.println("得到map中的数据大小" + outDTO.getData().size());


    }
    /***
     *  测试一个集合
     * @throws Exception
     */
    @Test
    public void bankTestsList() throws Exception {
//        services,这个是交易号,,,1
//        a,测试a,yihui,default,0
//        b,测试一个对象,def,1
//        c,测试c,yh,def,0
//        d,cesDD,yh,def,0
//        b,测试一个对象,def,-1
//        services,这个是交易号,,,-1

        InDTO inDTO = new InDTO();
        Map<String,Object> map = new HashMap<>();
        map.put("interfaceNo", "10086");
        map.put("a","a元素的值");

        //对象
        Map<String,Object> bmap = new HashMap<>();
        bmap.put("c","第一个对象c元素的值");
        bmap.put("d","第一个对象d元素的值");
        bmap.put("yh","对象中的议会值");
        Map<String,Object> m2 = new HashMap<>();
        m2.put("c","第一个对象c元素的值");
        m2.put("d","第一个对象d元素的值");
        m2.put("yh","m2");
        Map<String,Object> m3 = new HashMap<>();
        m3.put("c","第一个对象c元素的值");
        m3.put("d","第一个对象d元素的值");
        m3.put("yh","m3");

        List< Map<String,Object>> mapList = new ArrayList<>();
        mapList.add(bmap);
        mapList.add(m2);
        mapList.add(m3);

        map.put("yihui","Mapp议会");


        map.put("yhObj",mapList);  //使用议会属性
        //map.put("b",mapList);  //使用标签属性

        inDTO.setData(map);

        OutDTO outDTO= bankMappingService.queryBankMapping(inDTO);


        DataToXmlUtil du = new DataToXmlUtil();
        String xml = du.getSendXml(outDTO.get("send").toString(),inDTO);
        System.out.println(xml);


        //OutDTO outDTO = bankMappingService.run(inDTO);

     //   System.out.println("得到map中的数据大小" + outDTO.getData().size());


    }


    String repXml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<services><a>Mapp议会</a><b><c>对象中的议会值</c><d>对象中的议会值</d></b><b><c>m2</c><d>m2</d></b><b><c>m3</c><d>m3</d></b></services>";

}
