package com.yihuisoft.yihuisoftservice.service;

import com.yihuisoft.common.InDTO;
import com.yihuisoft.common.OutDTO;
import com.yihuisoft.common.exception.ApplicationException;
import com.yihuisoft.yihuisoftservice.ApplicationUtil;
import com.yihuisoft.yihuisoftservice.CacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Cache服务类
 * @author tonywu
 * @version v1.0.0
 */
@Service
public class CacheService {
    /**包头配置*/
    @Value("${package.pre}")
    private String packagePre;

    /***
     * 写入缓存
     * @param inDTO jobId runDate(YYYYMMDD格式)
     * @return
     * @throws Exception
     */
    public OutDTO put(InDTO inDTO) throws Exception {

        OutDTO result = new OutDTO();

        //取得缓存名称
        String cacheName = (String)inDTO.get("cacheName");
        if(StringUtils.isEmpty(cacheName)) {
            throw new ApplicationException("ERRORCODE0010",new String[]{"cacheName"});
        }
        //取得缓存cachekey
        String cachekey = (String)inDTO.get("cachekey");
        if(StringUtils.isEmpty(cachekey)) {
            throw new ApplicationException("ERRORCODE0010",new String[]{"cachekey"});
        }
        //取得用于缓存的服务名称
        String cacheServiceName = (String)inDTO.get("cacheServiceName");
        if(StringUtils.isEmpty(cacheName)) {
            throw new ApplicationException("ERRORCODE0010",new String[]{"cacheServiceName"});
        }
        //取得用于缓存服务的方法名称
        String cacheMethodName = (String)inDTO.get("cacheMethodName");
        if(StringUtils.isEmpty(cacheMethodName)) {
            throw new ApplicationException("ERRORCODE0010",new String[]{"cacheMethodName"});
        }
        Object servicebeanTmp = ApplicationUtil.getBeanByClass(packagePre + cacheServiceName);
        Method mh = ReflectionUtils.findMethod(servicebeanTmp.getClass(), cacheMethodName, new Class[]{InDTO.class});
        //ReflectionUtils.declaresException(mh,ApplicationException.class);
        Object obj = ReflectionUtils.invokeMethod(mh, servicebeanTmp, inDTO);
        if(obj == null) {
            throw new ApplicationException("ERRORCODE0020");
        }
        CacheUtils.put(cacheName,cachekey,obj);

        return result;
    }

    /***
     * 读缓存
     * @param inDTO
     * @return
     * @throws Exception
     */
    public OutDTO get(InDTO inDTO) throws Exception {

        OutDTO result = new OutDTO();

        //取得缓存名称
        String cacheName = (String)inDTO.get("cacheName");
        if(StringUtils.isEmpty(cacheName)) {
            throw new ApplicationException("ERRORCODE0010",new String[]{"cacheName"});
        }
        //取得缓存cachekey
        String cachekey = (String)inDTO.get("cachekey");
        if(StringUtils.isEmpty(cachekey)) {
            throw new ApplicationException("ERRORCODE0010",new String[]{"cachekey"});
        }
        result.put(cachekey, CacheUtils.get(cacheName,cachekey));

        return result;
    }

    /***
     * 清空缓存
     * @param inDTO
     * @return
     * @throws Exception
     */
    public OutDTO removeAll(InDTO inDTO) throws Exception {
        OutDTO result = new OutDTO();
        CacheUtils.removeAll();
        return result;
    }

    /***
     * 获取getManager
     * @param inDTO
     * @return
     * @throws Exception
     */
    public OutDTO getManager(InDTO inDTO) throws Exception {
        OutDTO result = new OutDTO();
        result.put("getManager",CacheUtils.getManager());
        return result;
    }

}
