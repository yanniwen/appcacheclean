package com.webeye.cleantools.dao;

/**
 * Created by yanni on 15/4/16.
 */
public class SQL {
    public static final String TABLE_APP_CACHE = "appcache";

    public static final String PRIMARY_KEY = "_id";

    public static final String CACHE_ITEM_NAME = "item_name";
    public static final String CACHE_PACKAGE_NAME = "package_name";
    public static final String CACHE_DIR = "dir";
    public static final String CACHE_SUB_DIR = "sub_dir";
    public static final String CACHE_REMOVE_DIR = "remove_dir";
    public static final String CACHE_REGULAR = "regular";

    public static final String CREATE_TABLE_APPCACHE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_APP_CACHE + "(" +
                    PRIMARY_KEY + " VARCHAR PRIMARY KEY, " +
                    CACHE_ITEM_NAME + " VARCHAR, " +
                    CACHE_PACKAGE_NAME + " VARCHAR, " +
                    CACHE_DIR + " VARCHAR, " +
                    CACHE_SUB_DIR + " VARCHAR, " +
                    CACHE_REMOVE_DIR + " INT, " +
                    CACHE_REGULAR + " INT " +
                    ")";

    public static final String MOCK_CACHE_DATA =
            "INSERT INTO " + TABLE_APP_CACHE + "(" + PRIMARY_KEY + "," +  CACHE_ITEM_NAME + "," +
                    CACHE_PACKAGE_NAME + "," + CACHE_DIR + "," + CACHE_SUB_DIR + "," +
                    CACHE_REMOVE_DIR + "," + CACHE_REGULAR + ") VALUES(null,'MicroMsg'," +
                    "'com.tencent.mm','/Tencent/MicroMsg','/[0-9a-zA-Z]{32}/avatar',1,1);";
}