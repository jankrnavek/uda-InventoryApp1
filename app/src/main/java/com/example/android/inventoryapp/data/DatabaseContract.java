package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

public final class DatabaseContract {

    public static final String LOG_TAG = DatabaseContract.class.getSimpleName();

    // Database file name.
    public static final String DATABASE_NAME = "products.db";

    // Database version
    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    // To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
    private DatabaseContract() {
    }

    public static abstract class ProductTable implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_SUPLIER_NAME = "suplier_name";
        public static final String COLUMN_NAME_SUPLIER_PHONE_NUMBER = "suplier_phone_number";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PRICE + INTEGER_TYPE + COMMA_SEP +
                COLUMN_NAME_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                COLUMN_NAME_SUPLIER_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SUPLIER_PHONE_NUMBER + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}