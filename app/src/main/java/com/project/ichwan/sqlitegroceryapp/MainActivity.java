package com.project.ichwan.sqlitegroceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTitle;
    TextView txAmount;
    private int amount = 0;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper helper = new DBHelper(this);
        database = helper.getWritableDatabase();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShopAdapter(this,getitem());
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        edtTitle = findViewById(R.id.input_text);
        txAmount = findViewById(R.id.tx_amount);

        findViewById(R.id.btn_min).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_plus:
                increase();
                break;
            case R.id.btn_min:
                decrease();
                break;
            case R.id.btn_add:
                addItem();
                break;
        }

    }

    private void increase() {
        amount++;
        txAmount.setText(String.valueOf(amount));
    }

    private void decrease() {
        if (amount > 0) {
            amount--;
            txAmount.setText(String.valueOf(amount));
        } else {
            Toast.makeText(this, "Harus lebih dari nol!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteItem(long id){
        database.delete(Contract.ShopEntry.TABLE_NAME,
                Contract.ShopEntry._ID + "=" + id,null);

        adapter.swapCursor(getitem());
    }

    private void addItem() {
        if (edtTitle.getText().toString().trim().length() == 0 || amount == 0){
            return;
        }

        String name = edtTitle.getText().toString();
        ContentValues values = new ContentValues();
        values.put(Contract.ShopEntry.COLUMN_NAME,name);
        values.put(Contract.ShopEntry.COLUMN_AMOUNT, amount);

        database.insert(Contract.ShopEntry.TABLE_NAME,null,values);
        adapter.swapCursor(getitem());
        edtTitle.getText().clear();
    }

    private Cursor getitem(){
        return database.query(Contract.ShopEntry.TABLE_NAME,null,null,null,null,null,
                Contract.ShopEntry.COLUMN_TIMESTAMP + " DESC");
    }
}