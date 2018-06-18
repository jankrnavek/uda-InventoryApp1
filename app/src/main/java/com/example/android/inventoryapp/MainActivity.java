package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.inventoryapp.data.DatabaseContract.ProductTable;
import com.example.android.inventoryapp.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelpder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelpder = new DatabaseHelper(this);
        queryData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        queryData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_activity.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                // Respond to a click on the "Insert fake product" menu option
                insertData();
                queryData();
                return true;
            // Respond to a click on the "Delete all products" menu option
            case R.id.action_delete_all_entries:
                deleteAllProducts();
                queryData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertData() {
        // Gets the database in write mode
        SQLiteDatabase db = dbHelpder.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and attributes are the values.
        ContentValues values = new ContentValues();
        values.put(ProductTable.COLUMN_NAME_PRODUCT_NAME, "some name");
        values.put(ProductTable.COLUMN_NAME_PRICE, 100);
        values.put(ProductTable.COLUMN_NAME_QUANTITY, 77);
        values.put(ProductTable.COLUMN_NAME_SUPLIER_NAME, "some sup name");
        values.put(ProductTable.COLUMN_NAME_SUPLIER_PHONE_NUMBER, "+123 123456");

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(ProductTable.TABLE_NAME, null, values);
    }

    private void deleteAllProducts() {
        // Gets the database in write mode
        SQLiteDatabase db = dbHelpder.getWritableDatabase();
        db.delete(ProductTable.TABLE_NAME, null, null);

    }

    private void queryData() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelpder.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProductTable._ID,
                ProductTable.COLUMN_NAME_PRODUCT_NAME,
                ProductTable.COLUMN_NAME_PRICE,
                ProductTable.COLUMN_NAME_QUANTITY,
                ProductTable.COLUMN_NAME_SUPLIER_NAME,
                ProductTable.COLUMN_NAME_SUPLIER_PHONE_NUMBER,
        };

        // Perform a query on the pets table
        Cursor cursor = db.query(
                ProductTable.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_product);

        try {
            // Create a header in the Text View that looks like this:
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The product table contains " + cursor.getCount() + " products.\n\n");
            displayView.append(ProductTable._ID + " | " +
                    ProductTable.COLUMN_NAME_PRODUCT_NAME + " | " +
                    ProductTable.COLUMN_NAME_PRICE + " | " +
                    ProductTable.COLUMN_NAME_QUANTITY + " | " +
                    ProductTable.COLUMN_NAME_SUPLIER_NAME + " | " +
                    ProductTable.COLUMN_NAME_SUPLIER_PHONE_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ProductTable._ID);
            int productNameColumnIndex = cursor.getColumnIndex(ProductTable.COLUMN_NAME_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductTable.COLUMN_NAME_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductTable.COLUMN_NAME_QUANTITY);
            int suplierNameColumnIndex = cursor.getColumnIndex(ProductTable.COLUMN_NAME_SUPLIER_NAME);
            int suplierPhoneNumberColumnIndex = cursor.getColumnIndex(ProductTable.COLUMN_NAME_SUPLIER_PHONE_NUMBER);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSuplierName = cursor.getString(suplierNameColumnIndex);
                String currentSuplierPhoneNumber = cursor.getString(suplierPhoneNumberColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentProductName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSuplierName + " - " +
                        currentSuplierPhoneNumber));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
