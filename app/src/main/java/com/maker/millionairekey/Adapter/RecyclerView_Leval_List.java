package com.maker.millionairekey.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maker.millionairekey.Activity.LevalListActivity;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.Leveldatum;

import java.util.List;

public class RecyclerView_Leval_List extends RecyclerView.Adapter<RecyclerView_Leval_List.ViewHolder> {

    List<Leveldatum> leval_list;
    LevalListActivity levalListActivity;

    public RecyclerView_Leval_List(List<Leveldatum> list, LevalListActivity levalListActivity) {
        this.leval_list = list;
        this.levalListActivity = levalListActivity;
    }

    @Override
    public RecyclerView_Leval_List.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leval_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView_Leval_List.ViewHolder holder, int position) {
        holder.tvLeval.setText(leval_list.get(position).getMobile());
        holder.tvCount.setText(leval_list.get(position).getUsername());
        holder.txt_status.setText(leval_list.get(position).getActiveStatus());

//        if (holder.getAdapterPosition()%4==0)
//        {
//            holder.layout.setBackgroundResource(R.drawable.c1);
//        }else {
//            if (holder.getAdapterPosition()%4==1)
//            {
//                holder.layout.setBackgroundResource(R.drawable.c4);
//            }else {
//                if (holder.getAdapterPosition()%4==2)
//                {
//                    holder.layout.setBackgroundResource(R.drawable.c1);
//                }else {
//                    holder.layout.setBackgroundResource(R.drawable.c4);
//                }
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return leval_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLeval, tvCount, txt_status;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLeval = (TextView) itemView.findViewById(R.id.tvLeval);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            txt_status = itemView.findViewById(R.id.txt_status);

        }
    }
}
