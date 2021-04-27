package com.ayoyo.merchant.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.json.mlm.MlmDownline;

import java.util.List;

public class MlmItem extends RecyclerView.Adapter<MlmItem.ItemRowHolder> {

    public static List<MlmDownline> dataList;
    private Context mContext;

    public MlmItem(Context context, List<MlmDownline> dataList) {
        this.dataList = dataList;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commision, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final MlmDownline singleItem = dataList.get(position);
        holder.name.setText(singleItem.fullnama);
        holder.ref.setText("(Ref"+singleItem.level+")");
        holder.commission.setText(singleItem.komisi);






    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView name, ref, commission;


        ItemRowHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvMlmName);
            ref = itemView.findViewById(R.id.tvMlmRef);
            commission = itemView.findViewById(R.id.tvMlmComission);
        }
    }
}
