package com.example.evoting.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.evoting.R;
import com.example.evoting.models.ElectionListDataView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class VoterElectionListAdapter extends RecyclerView.Adapter<VoterElectionListAdapter.ViewHolder> {
    private List<ElectionListDataView> listdata;

    onItemClickListner mListner;

    // RecyclerView recyclerView;
    public VoterElectionListAdapter(List<ElectionListDataView> listdata, onItemClickListner listner) {
        this.listdata = listdata;
        this.mListner = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.voterelectionlist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ElectionListDataView electionListDataView = listdata.get(position);
        holder.txt_title.setText(electionListDataView.getTitle());
        holder.txt_startdate.setText(parseDate(electionListDataView.getStartDate()));
        holder.txt_enddate.setText(parseDate(electionListDataView.getEndDate()));


        String S = printDifference(new Date(),parseDate1(electionListDataView.getStartDate()));

        holder.txt_timer.setText(S);

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title, txt_startdate, txt_enddate, txt_minage, txt_timer;
        Button btn_participate;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_startdate = (TextView) itemView.findViewById(R.id.txt_startdate);
            txt_enddate = (TextView) itemView.findViewById(R.id.txt_enddate);
            txt_timer = (TextView) itemView.findViewById(R.id.txt_timer);
        }
    }

    public interface onItemClickListner {
        public void setItemClickListener(int pos, ElectionListDataView electionListDataView);
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


    public Date parseDate1(String dt) {

        try {

            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy hh:mm aa");

            Date date = input.parse(dt);

            return date;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();

    }

    public String printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

       return String.format("%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

    }
}  
