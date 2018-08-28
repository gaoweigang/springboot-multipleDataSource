package com.gwg.shiro.web.config.jdbc;

/**
 * 数据源的类别：master/slave
 */
public enum DataSourceType {
    MASTER("master"), SLAVE("slave");

    private String code;

    DataSourceType(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}