package com.maker.millionairekey.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maker.millionairekey.Activity.NewsActivity;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.Classdatum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sumit on 8/12/2018.
 */

public class RecyclerView_NewsAdapter extends RecyclerView.Adapter<RecyclerView_NewsAdapter.ViewHolder> {

    List<Classdatum> newslist;
    Context dactivity;

    public RecyclerView_NewsAdapter(NewsActivity newsActivity, List<Classdatum> data) {
        this.dactivity = newsActivity;
        this.newslist = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_eventt_data, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            String date = newslist.get(position).getOndate();
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            try {
                newDate = spf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd/MM/yyyy");
            date = spf.format(newDate);
            System.out.println(date);

            holder.txt_des.setText("" + newslist.get(position).getNews());
            holder.tv_day.setText("Date : " + date);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return newslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_des;
        TextView tv_day;


        public ViewHolder(View itemView) {
            super(itemView);
            txt_des = (TextView) itemView.findViewById(R.id.txt_des);
            tv_day = (TextView) itemView.findViewById(R.id.tv_day);

        }
    }
}
