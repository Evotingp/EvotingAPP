package com.example.evoting.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.evoting.R;
import com.example.evoting.models.ElectionResultVo;
import com.example.evoting.models.FeedPostResultVo;
import com.example.evoting.utils.AESEncyption;
import com.example.evoting.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ElectionResultAdapter extends RecyclerView.Adapter<ElectionResultAdapter.ViewHolder> {
    private List<ElectionResultVo> listdata;

    // RecyclerView recyclerView;
    public ElectionResultAdapter(List<ElectionResultVo> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_electionresult_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ElectionResultVo electionResultVo = listdata.get(position);
        try {
            holder.txt_title.setText(electionResultVo.getTitle());
            holder.txt_date.setText(parseDate(electionResultVo.getStartDate()) + "\nto\n" + parseDate(electionResultVo.getEndDate()));

            String[] data = AESEncyption.decrypt(electionResultVo.getVoteData()).split("\\|\\|");
            if (data != null) {
                if (data.length >= 3) {
                    holder.txt_candetails.setText(data[1]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title, txt_date, txt_candetails;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_candetails = (TextView) itemView.findViewById(R.id.txt_candetails);

        }
    }

    public String parseDate(String dt) {
        try {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy hh:mm aa");
            Date date = input.parse(dt);
            return output.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}  
