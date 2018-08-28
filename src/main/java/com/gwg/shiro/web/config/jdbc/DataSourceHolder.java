package com.gwg.shiro.web.config.jdbc;

public class DataSourceHolder {

    private static ThreadLocal<String> dsTypeThreadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return DataSourceType.SLAVE.getCode();
        }
    };

    public static String getDataSourceType() {
         return dsTypeThreadLocal.get();
    }
    public static void setDataSourceType(String source) {
        dsTypeThreadLocal.set(source);
    }
}
