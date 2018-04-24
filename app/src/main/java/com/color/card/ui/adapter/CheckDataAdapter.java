package com.color.card.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.color.card.R;
import com.color.card.entity.CheckData;
import com.color.card.util.DateUtil;

import java.util.List;

/**
 * @author yqy
 * @date on 2018/4/17
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class CheckDataAdapter extends RecyclerView.Adapter<CheckDataAdapter.MyViewHolder> {

    private Context mContext;

    private List<CheckData> mDatas;


    public void setComments(List<CheckData> comments) {
        if (this.mDatas != null) {
            this.mDatas.clear();
        }
        this.mDatas = comments;
        this.notifyDataSetChanged();
    }

    public CheckDataAdapter(Context context) {
        this.mContext = context;
    }


    @NonNull
    @Override
    public CheckDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_checkdata_item, parent, false);
        MyViewHolder myViewHolderw = new MyViewHolder(view);
        return myViewHolderw;

    }

    @Override
    public void onBindViewHolder(@NonNull final CheckDataAdapter.MyViewHolder holder, final int position) {
        final CheckData comments = mDatas.get(position);
        if (comments != null) {
            holder.tv_takeTime.setText((comments.getShowTime()));
            String tag = "";
            if (!TextUtils.isEmpty(comments.getTakeTime())) {
                if (comments.getTakeTime().equals("0")) {
                    tag = "凌晨";
                } else if (comments.getTakeTime().equals("1")) {
                    tag = "早餐前";
                } else if (comments.getTakeTime().equals("2")) {
                    tag = "早餐后";
                } else if (comments.getTakeTime().equals("3")) {
                    tag = "午餐前";
                } else if (comments.getTakeTime().equals("4")) {
                    tag = "午餐后";
                } else if (comments.getTakeTime().equals("5")) {
                    tag = "晚餐前";
                } else if (comments.getTakeTime().equals("6")) {
                    tag = "晚餐后";
                } else {
                    tag = "睡前";
                }
            }
            holder.tv_bq.setText(tag);
            holder.tv_value.setText(comments.getValue() + "mmol/L");
            TextView tv_value_level = holder.tv_value_level;
            if (comments.getValueLevel().equals("0")) {
                tv_value_level.setText("偏低");
                tv_value_level.setTextColor(Color.parseColor("#F04F41"));
            } else if (comments.getValueLevel().equals("1")) {
                tv_value_level.setText("正常");
                tv_value_level.setTextColor(Color.parseColor("#2ECE63"));
            } else if (comments.getValueLevel().equals("2")) {
                tv_value_level.setText("临界值");
                tv_value_level.setTextColor(Color.parseColor("#FD792F"));
            } else {
                tv_value_level.setText("偏高");
                tv_value_level.setTextColor(Color.parseColor("#F04F41"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_takeTime;

        TextView tv_bq;

        TextView tv_value;

        TextView tv_value_level;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_takeTime = (TextView) itemView.findViewById(R.id.tv_takeTime);
            tv_bq = itemView.findViewById(R.id.tv_bq);
            tv_value = itemView.findViewById(R.id.tv_value);
            tv_value_level = itemView.findViewById(R.id.tv_value_level);
        }

    }

}
