package com.ayoyo.merchant.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.models.WalletModel;
import com.ayoyo.merchant.utils.Utility;

import java.util.List;

public class RiwayatPenarikanItem extends RecyclerView.Adapter<RiwayatPenarikanItem.ItemRowHolder> {

    private List<WalletModel> dataList;
    private Context mContext;

    public RiwayatPenarikanItem(Context context, List<WalletModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penarikan, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final WalletModel singleItem = dataList.get(position);
        if(position>0) {
            Utility.currencyTXT(holder.tersedia, singleItem.getTersedia(), mContext);
            Utility.currencyTXT(holder.penarikan, singleItem.getJumlah(), mContext);

            holder.tanggal.setText(singleItem.getWaktu());

        }
        else{
            holder.tersedia.setText(singleItem.getTersedia());
            holder.penarikan.setText(singleItem.getJumlah());
            holder.tanggal.setText(singleItem.getWaktu());
        }





    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView tersedia, penarikan, tanggal;


        ItemRowHolder(View itemView) {
            super(itemView);
            tersedia = itemView.findViewById(R.id.tvTersedia);
            penarikan = itemView.findViewById(R.id.tvPenarikan);
            tanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
