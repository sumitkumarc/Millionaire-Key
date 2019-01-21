package com.maker.millionairekey.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maker.millionairekey.Activity.TransactionActivity;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.Classdatum;

import java.util.List;

public class RecyclerView_Transaction extends RecyclerView.Adapter<RecyclerView_Transaction.ViewHolder> {
    List<Classdatum> newslist;
    Context dactivity;

    public RecyclerView_Transaction(TransactionActivity transactionActivity, List<Classdatum> data) {
        this.dactivity = transactionActivity;
        this.newslist = data;
    }

    @NonNull
    @Override
    public RecyclerView_Transaction.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_transaction_data, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_Transaction.ViewHolder holder, int position) {
        holder.tv_Request_id.setText("" + newslist.get(position).getTransactionno());
        holder.tv_Withrow_bal.setText("-" + newslist.get(position).getAmount());
        holder.tv_Cur_bal.setText(newslist.get(position).getTransactionno());
        holder.tv_Date.setText("Date : " + newslist.get(position).getOndate());
        holder.tv_status.setText("Status : " + newslist.get(position).getStatus());
        if (newslist.get(position).getStatus().equals("approved")) {
            holder.tv_status.setTextColor(Color.GREEN);
        } else {
            holder.tv_status.setTextColor(Color.RED);
        }
        if (holder.getAdapterPosition() % 4 == 0) {
            holder.layout.setBackgroundResource(R.drawable.tra);
        } else {
            if (holder.getAdapterPosition() % 4 == 1) {
                holder.layout.setBackgroundResource(R.drawable.tra);
            } else {
                if (holder.getAdapterPosition() % 4 == 2) {
                    holder.layout.setBackgroundResource(R.drawable.tra);
                } else {
                    holder.layout.setBackgroundResource(R.drawable.tra);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return newslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Request_id, tv_Withrow_bal, tv_Cur_bal, tv_Date, tv_status;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_Request_id = (TextView) itemView.findViewById(R.id.tv_Request_id);
            tv_Withrow_bal = (TextView) itemView.findViewById(R.id.tv_Withrow_bal);
            tv_Cur_bal = (TextView) itemView.findViewById(R.id.tv_Cur_bal);
            tv_Date = (TextView) itemView.findViewById(R.id.tv_Date);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
