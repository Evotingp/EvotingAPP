package com.example.evoting.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.evoting.R;
import com.example.evoting.models.CandidateListData;
import com.example.evoting.models.ElectionListDataView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AllCandidateListAdapter extends RecyclerView.Adapter<AllCandidateListAdapter.ViewHolder> {
    private List<CandidateListData> listdata;

    onItemClickListner mListner;
    boolean isElectionStarted;

    // RecyclerView recyclerView;
    public AllCandidateListAdapter(List<CandidateListData> listdata, onItemClickListner listner) {
        this.listdata = listdata;
        this.mListner = listner;
    }

    // RecyclerView recyclerView;
    public AllCandidateListAdapter(List<CandidateListData> listdata, onItemClickListner listner,boolean b) {
        this.listdata = listdata;
        this.mListner = listner;
        this.isElectionStarted = b;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_candidatelist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CandidateListData candidateListData = listdata.get(position);


        holder.txt_partyname.setText(candidateListData.getPartyName());
        holder.txt_canname.setText(candidateListData.getFirstName() +" "+candidateListData.getMiddleName() +" "+candidateListData.getLastName());
        //holder.img_party.setText(candidateListData.getPartyLogo());
        //holder.img_candidate.setText(candidateListData.getCphoto());

        if (isElectionStarted) {
            holder.btn_vote.setVisibility(View.VISIBLE);
        }
        else {
            holder.btn_vote.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_partyname, txt_canname;
        public ImageView img_party, img_candidate;
        public Button btn_vote;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_partyname = (TextView) itemView.findViewById(R.id.txt_partyname);
            txt_canname = (TextView) itemView.findViewById(R.id.txt_canname);
            img_party = (ImageView) itemView.findViewById(R.id.img_party);
            img_candidate = (ImageView) itemView.findViewById(R.id.img_candidate);
            btn_vote = (Button) itemView.findViewById(R.id.btn_vote);
        }
    }

    public interface onItemClickListner {
        public void setItemClickListener(int pos, CandidateListData candidateListData);
    }

}  
