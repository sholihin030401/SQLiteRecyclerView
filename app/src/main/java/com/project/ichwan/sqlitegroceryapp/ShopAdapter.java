package com.project.ichwan.sqlitegroceryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopHolder> {

    private Context context;
    private Cursor cursor;

    public ShopAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ShopAdapter.ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false);

        return new ShopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ShopHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String nama = cursor.getString(cursor.getColumnIndex(Contract.ShopEntry.COLUMN_NAME));
        int jumlah = cursor.getInt(cursor.getColumnIndex(Contract.ShopEntry.COLUMN_AMOUNT));
        long id = cursor.getLong(cursor.getColumnIndex(Contract.ShopEntry._ID));

        holder.titleText.setText(nama);
        holder.countText.setText(String.valueOf(jumlah));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ShopHolder extends RecyclerView.ViewHolder {

        public TextView titleText;
        public TextView countText;

        public ShopHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.tx_item);
            countText = itemView.findViewById(R.id.tx_amount_item);
        }
    }

    public void swapCursor(Cursor mCursor){
        if (cursor != null){
            cursor.close();
        }

        cursor = mCursor;

        if (mCursor != null){
            notifyDataSetChanged();
        }
    }
}
