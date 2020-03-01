package com.example.evoting.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.evoting.R;
import com.example.evoting.models.ElectionListDataView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ElectionListAdapter extends RecyclerView.Adapter<ElectionListAdapter.ViewHolder> {
    private List<ElectionListDataView> listdata;

    // RecyclerView recyclerView;
    public ElectionListAdapter(List<ElectionListDataView> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_electionlist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ElectionListDataView electionListDataView = listdata.get(position);
        holder.txt_title.setText(electionListDataView.getTitle());
        holder.txt_startdate.setText(parseDate(electionListDataView.getStartDate()));
        holder.txt_enddate.setText(parseDate(electionListDataView.getEndDate()));
        holder.txt_minage.setText("Minimum Age : "+electionListDataView.getMinAge());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title, txt_startdate, txt_enddate, txt_minage;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_startdate = (TextView) itemView.findViewById(R.id.txt_startdate);
            txt_enddate = (TextView) itemView.findViewById(R.id.txt_enddate);
            txt_minage = (TextView) itemView.findViewById(R.id.txt_minage);

        }
    }


    public String parseDate(String dt) {

        try {

            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy hh:mm aa");

            Date date = input.parse(dt);

            return output.format(date);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }
}  
