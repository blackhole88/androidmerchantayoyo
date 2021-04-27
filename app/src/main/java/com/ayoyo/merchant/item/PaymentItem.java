package com.ayoyo.merchant.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.activity.PaymentMethodActivity;
import com.ayoyo.merchant.models.PaymentMethodModel;
import com.ayoyo.merchant.utils.Utility;

import java.util.List;

public class PaymentItem extends RecyclerView.Adapter<PaymentItem.ItemRowHolder> {

    private List<PaymentMethodModel> dataList;
    private Context mContext;
    private OnPaymentClickListener listener;

    public interface OnPaymentClickListener{
        public void onPaymentClick(PaymentMethodModel model);


    }

    public PaymentItem(Context context, List<PaymentMethodModel> dataList, OnPaymentClickListener listener) {
        this.dataList = dataList;
        this.mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PaymentItem.ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paymentmethod, parent, false);
        return new PaymentItem.ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentItem.ItemRowHolder holder, final int position) {
        final PaymentMethodModel singleItem = dataList.get(position);

        if(singleItem.methodName!=null){
            holder.methodName.setText(singleItem.methodName);
        }
        if(singleItem.value!=null) {
            holder.value.setVisibility(View.VISIBLE);
            Utility.currencyTXT(holder.value, singleItem.value, mContext);

        }
        else holder.value.setVisibility(View.GONE);

        if(singleItem.isActive){
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
        else{
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            deselect();
            dataList.get(position).isActive = true;
            PaymentMethodActivity.namaMethod = dataList.get(position).methodName;
            listener.onPaymentClick(dataList.get(position));
            notifyDataSetChanged();
        });

    }

    public void deselect(){
        for (int i =0;i<dataList.size();i++){
            dataList.get(i).isActive = false;
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView methodName, value;



        ItemRowHolder(View itemView) {
            super(itemView);
            methodName = itemView.findViewById(R.id.tvNamaMetode);
            value = itemView.findViewById(R.id.tvValue);

        }
    }
}
