package com.yihuisoft.yihuisoftservice.controller;

import com.yihuisoft.common.InDTO;
import com.yihuisoft.common.OutDTO;
import com.yihuisoft.common.exception.ApplicationException;
import com.yihuisoft.common.util.*;
import com.yihuisoft.yihuisoftservice.ApplicationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共调用Controller类
 * @author tonywu
 * @version v1.0.0
 */
@RestController
//@RequestMapping(value="/Call")
public class CallController extends BaseController {

    /**包头配置项*/
    @Value("${package.pre}")
    private String packagePre;

    /**是否需要进行sign验证*/
    @Value("${sign.enable}")
    private Integer signEnable;

    /**密钥key*/
    @Value("${sign.key}")
    private String signkey;

    /**是否需要进行token验证*/
    @Value("${token.enable}")
    private Integer tokenEnable;

    /**过滤拦截方法*/
    @Value("${filter.method}")
    private String filterethod;

    /**token 过期设置，单位为天*/
    @Value("${token.day}")
    private Integer tokenDay;


    /**
     * 公共方法调用 移动端
     * @param inDTO
     * @return
     */
    @RequestMapping(value="/CallAppMethod", method = RequestMethod.POST)
    public OutDTO callAppMethod(@RequestBody InDTO inDTO) {
        OutDTO outDTO = new OutDTO();
        try {
            if(tokenEnable == ComConst.NO){
                outDTO = (OutDTO)runMethod(inDTO);
                outDTO.setErrorCode("ERRORCODE0000", getErrorMessage("ERRORCODE0000",null));
            }else if(tokenEnable == ComConst.YES){
                String checkToken = checkToken(inDTO);
                if("1".equalsIgnoreCase(checkToken)){//需要返回token给前端，不需要验证token
                    String createToken = getCreateToken(inDTO);
                    inDTO.put("token",createToken);
                    outDTO = (OutDTO)runMethod(inDTO);
                    outDTO.put("token",createToken);
                    outDTO.setErrorCode("ERRORCODE0000", getErrorMessage("ERRORCODE0000",null));
                }else if("2".equalsIgnoreCase(checkToken) || "3".equalsIgnoreCase(checkToken)){//token 时间未超时 验证通过 或  token 超时时间不进行验证 token验证通过
                    outDTO = (OutDTO)runMethod(inDTO);
                    outDTO.setErrorCode("ERRORCODE0000",getErrorMessage("ERRORCODE0000",null));
                }else{
                    outDTO.setErrorCode(checkToken, getErrorMessage(checkToken,null));
                }
            }else{
                String errMsg = getErrorMessage("ERRORCODE0042",null);
                outDTO.setErrorCode("ERRORCODE0042",errMsg);
            }
            return outDTO;
        }
        catch (java.lang.reflect.UndeclaredThrowableException ute){
            Throwable throwable = ute.getUndeclaredThrowable();
            //捕获应用程序异常
            if(throwable instanceof ApplicationException){
                ApplicationException ae = (ApplicationException)throwable;
                String errMsg = getErrorMessage(ae.getErrCode(),ae.getErrMsgParam());
                outDTO.setErrorCode(ae.getErrCode(),errMsg);
                //ae.printStackTrace();
                logger.error(errMsg,ae);
            }
        }
        catch (Exception e) {
            String errMsg = getErrorMessage("ERRORCODE0001",null);
            outDTO.setErrorCode("ERRORCODE0001", errMsg);
            //e.printStackTrace();
            logger.error(errMsg,e);
        }
        return outDTO;
    }

    /**
     * token 加密
     * @param inDTO
     * @return
     */
    private String getCreateToken(InDTO inDTO) {
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("iat", DateUtil.getTimeStampByDate(new Date()));
        m.put("exp",DateUtil.getTimeStampByDate(DateUtil.getAfterDay(tokenDay)));
        m.put("iss","yihui");
        m.put("Email","yihui@yihuisoft.com");
        m.put("callerId",inDTO.getCallerId());
        return JavaWebToken.createJavaWebToken(m,signkey);
    }


    /**
     * token 校验
     * @param inDTO
     * @return
     */
    private String checkToken(InDTO inDTO) {
        String[] filterthodList = filterethod.split(",");
        for (int i = 0; i < filterthodList.length; i++) {
            if (inDTO.getFunctionName().concat(".".concat(inDTO.getMethodName())).equalsIgnoreCase(filterthodList[i])){
                //过滤方法名，不需要验证token
                return "1";
            }
        }
        //验证token
        if(StringUtil.notEmpty(inDTO.getSecurityStr())){
            Map<String, Object> jwtClaims = JavaWebToken.parserJavaWebToken(inDTO.getSecurityStr(),signkey);
            if(jwtClaims!=null){
                String issValue = jwtClaims.get("exp").toString();
                if(StringUtil.notEmpty(issValue)){
                    if(DateUtil.getTimeStampByDate(new Date()) > Long.parseLong(issValue)){
                        return "ERRORCODE0044";//token 时间已超时 验证不过
                    }else{
                        return "2";//token 时间未超时 验证通过
                    }
                }else{
                    return "3";// token 超时时间不进行验证 token验证通过
                }
            }else{
                return "ERRORCODE0045";//token验证不合法
            }
        }else{
            return "ERRORCODE0046";//token为空，验证不过
        }
    }

    /**
         * 公共方法调用 pc端
         * @param inDTO
         * @return
         */
    @RequestMapping(value="/CallMethod", method = RequestMethod.POST)
    public OutDTO callMethod(@RequestBody InDTO inDTO) {
        OutDTO outDTO = new OutDTO();
        try {
            if(signEnable == ComConst.NO){
                outDTO = (OutDTO)runMethod(inDTO);
                outDTO.setErrorCode("ERRORCODE0000", getErrorMessage("ERRORCODE0000",null));
            }else if(signEnable == ComConst.YES){
                String md5Value = getMD5String(inDTO,signkey);
                if(inDTO.getSecurityStr().equalsIgnoreCase(md5Value)){
                    outDTO = (OutDTO)runMethod(inDTO);
                    outDTO.setErrorCode("ERRORCODE0000", getErrorMessage("ERRORCODE0000",null));
                }else{
                    String errMsg = getErrorMessage("ERRORCODE0041",null);
                    outDTO.setErrorCode("ERRORCODE0041",errMsg);
                }
            }else{
                String errMsg = getErrorMessage("ERRORCODE0042",null);
                outDTO.setErrorCode("ERRORCODE0042",errMsg);
            }
            return outDTO;
        }
        catch (java.lang.reflect.UndeclaredThrowableException ute){
            Throwable throwable = ute.getUndeclaredThrowable();
            //捕获应用程序异常
            if(throwable instanceof ApplicationException){
                ApplicationException ae = (ApplicationException)throwable;
                String errMsg = getErrorMessage(ae.getErrCode(),ae.getErrMsgParam());
                outDTO.setErrorCode(ae.getErrCode(),errMsg);
                //ae.printStackTrace();
                logger.error(errMsg,ae);
            }
        }
        catch (Exception e) {
            String errMsg = getErrorMessage("ERRORCODE0001",null);
            outDTO.setErrorCode("ERRORCODE0001", errMsg);
            //e.printStackTrace();
            logger.error(errMsg,e);
        }
        return outDTO;
    }

    /**
     * @author:luweibin
     * @param inDTO
     * @param signkey
     * @return
     */
    private String getMD5String(InDTO inDTO,String signkey) {
        return MD5.md5(inDTO.getFunctionName().concat(inDTO.getMethodName().concat(signkey)));
    }

    /**
     * @author:luweibin
     * @param inDTO
     * @return
     */
    private Object runMethod(InDTO inDTO){
        Object servicebeanTmp = ApplicationUtil.getBeanByClass(packagePre + inDTO.getFunctionName());
        Method mh = ReflectionUtils.findMethod(servicebeanTmp.getClass(), inDTO.getMethodName(), new Class[]{InDTO.class});
        return ReflectionUtils.invokeMethod(mh, servicebeanTmp, inDTO);
    }
}
