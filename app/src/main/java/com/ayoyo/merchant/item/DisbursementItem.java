package com.ayoyo.merchant.item;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.json.linkaja.LinkajaDisbursementRequest;
import com.ayoyo.merchant.json.linkaja.LinkajaDisbursementResponse;
import com.ayoyo.merchant.json.linkaja.ListTransaksiLinkajaResponse;
import com.ayoyo.merchant.models.User;
import com.ayoyo.merchant.utils.Utility;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisbursementItem extends RecyclerView.Adapter<DisbursementItem.ItemRowHolder> {

    private List<ListTransaksiLinkajaResponse.LinkAjaTransaksi> dataList;
    private Context mContext;
    RelativeLayout loading;

    public DisbursementItem(Context context, List<ListTransaksiLinkajaResponse.LinkAjaTransaksi> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        loading = ((Activity)mContext).findViewById(R.id.rlprogress);
    }

    @NonNull
    @Override
    public DisbursementItem.ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linkaja_disbursement, parent, false);
        return new DisbursementItem.ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DisbursementItem.ItemRowHolder holder, final int position) {
        final ListTransaksiLinkajaResponse.LinkAjaTransaksi singleItem = dataList.get(position);

        holder.tvId.setText("#"+singleItem.id);
        holder.tvTanggal.setText(singleItem.waktu_order);
        Utility.currencyTXT(holder.tvTotal, singleItem.total, mContext);
        holder.tvStatus.setText(singleItem.status);

        if(singleItem.status.toLowerCase().equals("belum")) {
            holder.tvStatus.setText("Belum Ditarik");
            holder.tvTarik.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(v -> {

                loading.setVisibility(View.VISIBLE);
                User loginUser = BaseApp.getInstance(mContext).getLoginUser();
                MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
                LinkajaDisbursementRequest request = new LinkajaDisbursementRequest();
                request.id_user = loginUser.getId();
                request.no_linkaja = loginUser.getNoTelepon();
                request.tipe_user = "merchant";
                request.transaksi = new ArrayList<>();
                request.transaksi.add(Integer.parseInt(singleItem.id));
                service.postDisbursement(request).enqueue(new Callback<LinkajaDisbursementResponse>() {
                    @Override
                    public void onResponse(Call<LinkajaDisbursementResponse> call, Response<LinkajaDisbursementResponse> response) {
                        loading.setVisibility(View.GONE);
                        if(response.isSuccessful() && response.body()!=null){
                            Toast.makeText(mContext, response.body().data, Toast.LENGTH_LONG).show();
                            ((Activity)mContext).recreate();
                        }
                    }

                    @Override
                    public void onFailure(Call<LinkajaDisbursementResponse> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(mContext, "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }


    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvTanggal, tvTotal, tvStatus, tvTarik;



        ItemRowHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTarik = itemView.findViewById(R.id.tvTarik);
        }
    }
}

