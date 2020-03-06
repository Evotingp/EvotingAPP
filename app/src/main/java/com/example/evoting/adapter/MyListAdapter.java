package com.example.evoting.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;  
import android.widget.TextView;  
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.example.evoting.R;
import com.example.evoting.models.FeedPostResponseVo;
import com.example.evoting.models.FeedPostResultVo;
import com.example.evoting.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private List<FeedPostResultVo> listdata;
  
   // RecyclerView recyclerView;  
    public MyListAdapter(List<FeedPostResultVo> listdata) {
        this.listdata = listdata;  
    }  
    @Override  
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());  
        View listItem= layoutInflater.inflate(R.layout.layout_post_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);  
        return viewHolder;  
    }  
  
    @Override  
    public void onBindViewHolder(ViewHolder holder, int position) {  

        FeedPostResultVo feedPostResultVo = listdata.get(position);
        holder.txt_title.setText(feedPostResultVo.getTitle());
        holder.txt_PostedDate.setText(feedPostResultVo.getPostedDate());
        holder.txt_Description.setText(feedPostResultVo.getDescription());
        holder.txt_name.setText(feedPostResultVo.getCname());

        if (!TextUtils.isEmpty(feedPostResultVo.getImage()))
        {
            Picasso.get().load(Constants.IMAGE_Url + feedPostResultVo.getImage()).into(holder.imageView1);
        }




    }  
  
  
    @Override  
    public int getItemCount() {  
        return listdata.size();
    }  
  
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView1;
        public TextView txt_title,txt_PostedDate,txt_Description,txt_name;
        public ViewHolder(View itemView) {  
            super(itemView);
            txt_title=(TextView)itemView.findViewById(R.id.txt_title);
            txt_name=(TextView)itemView.findViewById(R.id.txt_name);
            txt_PostedDate=(TextView)itemView.findViewById(R.id.txt_PostedDate);
            txt_Description=(TextView)itemView.findViewById(R.id.txt_Description);
            imageView1=(ImageView)itemView.findViewById(R.id.imageView1);

        }  
    }  
}  
