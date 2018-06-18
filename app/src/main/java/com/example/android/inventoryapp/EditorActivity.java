package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.inventoryapp.data.DatabaseContract.ProductTable;
import com.example.android.inventoryapp.data.DatabaseHelper;

public class EditorActivity extends AppCompatActivity {
    private EditText mNameTextEdit;
    private EditText mQuantityTextEdit;
    private EditText mPriceTextEdit;
    private EditText mSupplierNameTextEdit;
    private EditText mSuppliearPhoneTextEdit;

    DatabaseHelper dbHelpder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameTextEdit = (EditText) findViewById(R.id.edit_product_name);
        mQuantityTextEdit = (EditText) findViewById(R.id.edit_quantity);
        mPriceTextEdit = (EditText) findViewById(R.id.edit_price);
        mSupplierNameTextEdit = (EditText) findViewById(R.id.edit_supplier_name);
        mSuppliearPhoneTextEdit = (EditText) findViewById(R.id.edit_supplier_phone_number);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelpder = new DatabaseHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save product to database
                insertProduct();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void insertProduct() {
        // Gets the database in write mode
        SQLiteDatabase db = dbHelpder.getWritableDatabase();

        String name = mNameTextEdit.getText().toString().trim();

        String sPrice = mPriceTextEdit.getText().toString().trim();
        int price = Integer.parseInt(sPrice);

        String sQuantity = mQuantityTextEdit.getText().toString().trim();
        int quantity = Integer.parseInt(sQuantity);

        String supplierName = mSupplierNameTextEdit.getText().toString().trim();

        String supplierPhone = mSuppliearPhoneTextEdit.getText().toString().trim();


        // Create a ContentValues object where column names are the keys,
        // and attributes are the values.
        ContentValues values = new ContentValues();
        values.put(ProductTable.COLUMN_NAME_PRODUCT_NAME, name);
        values.put(ProductTable.COLUMN_NAME_PRICE, price);
        values.put(ProductTable.COLUMN_NAME_QUANTITY, quantity);
        values.put(ProductTable.COLUMN_NAME_SUPLIER_NAME, supplierName);
        values.put(ProductTable.COLUMN_NAME_SUPLIER_PHONE_NUMBER, supplierPhone);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(ProductTable.TABLE_NAME, null, values);
    }

}
