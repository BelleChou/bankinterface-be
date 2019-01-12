package com.yihuisoft.bankinterfacebiz.entity;

import java.io.Serializable;
import java.util.List;

/***
 * 这个用于   拆分数据
 * @author isst
 * @version v1.0
 */
public class BankData implements Serializable {

    /*** // CstIdTp,客户标识类型,0,CstIdTpYiHui,默认值CstIdTp */
    /**
     * //名字
     */
    private String eleName;
    /**
     * //注解
     */
    private String eleRemarks;
    /**
     * //元素标识
     */
    private String eleFlag;
    /**
     * //OutDTO  或者 InDTO
     */
    private String eleYihui;
    /***   // 元素的默认值*/
    private String eleDefault;


    public String formatBankData() {

        return eleName + "," + (eleRemarks == null ? "无" : eleRemarks) + "," + eleFlag + "," + (eleYihui == null ? "" : eleYihui) + "," + (eleDefault == null ? "" : eleDefault) + "\n";
    }

    public BankData() {
    }

    public BankData(String eleName, String eleRemarks, String eleFlag, String eleYihui, String eleDefault) {
        this.eleName = eleName;
        this.eleRemarks = eleRemarks;
        this.eleFlag = eleFlag;
        this.eleYihui = eleYihui;
        this.eleDefault = eleDefault;
    }

    public BankData(String eleName, String eleRemarks, String eleFlag, String eleYihui) {
        this.eleName = eleName;
        this.eleRemarks = eleRemarks;
        this.eleFlag = eleFlag;
        this.eleYihui = eleYihui;
    }


    public BankData(String eleName, String eleRemarks, String eleFlag) {
        this.eleName = eleName;
        this.eleRemarks = eleRemarks;
        this.eleFlag = eleFlag;
    }


    public String getEleName() {
        return eleName;
    }

    public void setEleName(String eleName) {
        this.eleName = eleName;
    }

    public String getEleRemarks() {
        return eleRemarks;
    }

    public void setEleRemarks(String eleRemarks) {
        this.eleRemarks = eleRemarks;
    }

    public String getEleFlag() {
        return eleFlag;
    }

    public void setEleFlag(String eleFlag) {
        this.eleFlag = eleFlag;
    }

    public String getEleYihui() {
        return eleYihui;
    }

    public void setEleYihui(String eleYihui) {
        this.eleYihui = eleYihui;
    }

    public String getEleDefault() {
        return eleDefault;
    }

    public void setEleDefault(String eleDefault) {
        this.eleDefault = eleDefault;
    }


}
