package com.yihuisoft.bankinterfacebiz.mapper.bank;


import com.yihuisoft.bankinterfacebiz.entity.BankMapping;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

/**
 * 银行  的xml 与译会的  属性对应 结构体现  mapper接口类
 *
 * @author isst
 * @version v1.0.0
 */
@Mapper
public interface BankMappingMapper {


    /***
     * 根据   interfaceNo 查询出一条记录  之中有发送
     * @param interfaceNo
     * @return Bankno
     */
    BankMapping findByBankMappingInterfaceNo(String interfaceNo);

    /***
     * 增加一个   银行映射
     * @param bankMapping
     */
    int addOneBankMapping(BankMapping bankMapping);

    /***
     * 修改   银行映射
     * @param bankMapping
     */
    int updateOneBankMapping(BankMapping bankMapping);

    /**
     * 根据id 删除 一条记录
     * @param id
     * @return
     */
    int delOneBankMapping(Long id);


    /**
     * 条件查询用户
     *
     * @param map map集合
     * @return List
     */
    List<BankMapping> selectPageBankMappingList(Map<String, Object> map);

    /**
     * 查询总记录数
     * @param map map集合
     * @return int
     */
    int selectCountBankMapping(Map<String, Object> map);

}