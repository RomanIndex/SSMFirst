package com.ssm.common.multiDataSource;

public class DataSourceTypeManager {

    /*private static final ThreadLocal<DataSourceEnum> contextHolder = new ThreadLocal<>();

    public static void set(DataSourceEnum type){
        contextHolder.set(type);
    }

    public static DataSourceEnum get(){
        return contextHolder.get();
    }*/

    private static final ThreadLocal<DataSourceEnum> dataSourceTypes = new ThreadLocal<DataSourceEnum>(){
        @Override
        protected DataSourceEnum initialValue(){
            return DataSourceEnum.aliMysql;
        }
    };

    public static DataSourceEnum get(){
        return dataSourceTypes.get();
    }

    public static void set(DataSourceEnum dataSourceType){
        dataSourceTypes.set(dataSourceType);
    }

    /*public static void reset(){
        dataSourceTypes.set(DataSourceEnum.aliMysql);
    }*/

    public static void clearCustom() {
        dataSourceTypes.remove();
     }
}
