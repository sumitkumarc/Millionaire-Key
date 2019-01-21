package com.maker.millionairekey.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.maker.millionairekey.Activity.DashboardActivity;
import com.maker.millionairekey.Activity.LevalListActivity;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.Leveldatum;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;

public class RecyclerView_Leval extends RecyclerView.Adapter<RecyclerView_Leval.ViewHolder> {
    List<Leveldatum> leval_list;
    Activity activity;
    int largeValue = 0;
    private Timer timer;
    int totalcountuser = 0;

    public RecyclerView_Leval(List<Leveldatum> list, DashboardActivity dashboardActivity, int largeMember) {
        this.leval_list = list;
        this.activity = dashboardActivity;
        this.largeValue = largeMember;

    }


    @Override
    public RecyclerView_Leval.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leval_card1, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView_Leval.ViewHolder holder, final int position) {
//        for (int i = 0; i < leval_list.size(); i++) {
//            this.totalcountuser += Float.parseFloat(leval_list.get(i).getCount());
//        }

        holder.roundCornerProgressBar.setProgressColor(Color.parseColor("#ed3b27"));
        holder.roundCornerProgressBar.setProgressBackgroundColor(Color.parseColor("#eeeeee"));
        holder.roundCornerProgressBar.setMax(100);

        float count = Float.parseFloat(leval_list.get(position).getCount());
        DecimalFormat precision = new DecimalFormat("0.00");
        float percentage = Float.parseFloat(precision.format(((count / largeValue) * 100)));
        //float percentage = ((count / largeValue) * 100);
        holder.roundCornerProgressBar.setProgress(percentage);
        holder.number_progress_bar.setProgress((int) percentage);
        holder.number_progress_bar.setProgress((int) percentage);
        holder.tvLeval.setText(leval_list.get(position).getLevelno());
        holder.tvCount.setText(leval_list.get(position).getCount());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(leval_list.get(position).getCount());

                if (count > 0) {
                    Intent intent = new Intent(activity, LevalListActivity.class);
                    intent.putExtra("LEVAL_ID", leval_list.get(position).getId());
                    intent.putExtra("LEVAL_NAME", leval_list.get(position).getLevelno());
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity, "Data Not Available..", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        if (holder.getAdapterPosition() % 4 == 0) {
//            holder.layout.setBackgroundResource(R.drawable.c1);
//        } else {
//            if (holder.getAdapterPosition() % 4 == 1) {
//                holder.layout.setBackgroundResource(R.drawable.c4);
//            } else {
//                if (holder.getAdapterPosition() % 4 == 2) {
//                    holder.layout.setBackgroundResource(R.drawable.c1);
//                } else {
//                    holder.layout.setBackgroundResource(R.drawable.c4);
//                }
//           }
        //      }
    }

    @Override
    public int getItemCount() {
        return leval_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLeval, tvCount;
        LinearLayout layout;
        RoundCornerProgressBar roundCornerProgressBar;
        NumberProgressBar number_progress_bar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLeval = (TextView) itemView.findViewById(R.id.tvLeval);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            number_progress_bar = (NumberProgressBar) itemView.findViewById(R.id.number_progress_bar);
            roundCornerProgressBar = (RoundCornerProgressBar) itemView.findViewById(R.id.rpBar);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
