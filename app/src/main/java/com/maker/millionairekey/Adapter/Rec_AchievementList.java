package com.maker.millionairekey.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.maker.millionairekey.Activity.AchievementActivity;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.Achievementdatum;
import com.maker.millionairekey.Rest.Leveldatum;

import java.util.List;

public class Rec_AchievementList extends RecyclerView.Adapter<Rec_AchievementList.ViewHolder> {


    List<Achievementdatum> Achievement_list;
    Activity activity;


    public Rec_AchievementList(List<Achievementdatum> list, AchievementActivity achievementActivity) {
        this.Achievement_list = list;
        this.activity = achievementActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_achievement_item, parent, false);

        return new Rec_AchievementList.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_user_count.setText(Achievement_list.get(position).getNoOfUser().toString());
        holder.txt_achie_amount.setText(Achievement_list.get(position).getAmount().toString());
        holder.ratingbar.setRating(Achievement_list.get(position).getNoOfStar());
        holder.txt_achname.setText(Achievement_list.get(position).getLevel().toString());
//        LayerDrawable stars = (LayerDrawable) holder.ratingbar.getProgressDrawable();
//        stars.getDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public int getItemCount() {
        return Achievement_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_user_count, txt_achie_amount, txt_achname;
        RatingBar ratingbar;


        public ViewHolder(View itemView) {
            super(itemView);
            txt_user_count = (TextView) itemView.findViewById(R.id.txt_user_count);
            txt_achie_amount = (TextView) itemView.findViewById(R.id.txt_achie_amount);
            ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            txt_achname = (TextView) itemView.findViewById(R.id.txt_achname);

        }
    }
}
