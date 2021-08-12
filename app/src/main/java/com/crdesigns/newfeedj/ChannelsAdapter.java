package com.crdesigns.newfeedj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.Viewholder> {

    private Context context;
    private ArrayList<ChannelsModel> ChannelsModelArrayList;

    // Constructor
    public ChannelsAdapter(Context context, ArrayList<ChannelsModel> ChannelsModelArrayList) {
        this.context = context;
        this.ChannelsModelArrayList = ChannelsModelArrayList;
    }

    @NonNull
    @Override
    public ChannelsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category, null, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelsAdapter.Viewholder holder, int position) {

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String channelName = holder.channelNameTV.getText().toString();
                Toast.makeText(context,"Go to Swipe down Home News List please..."+ channelName + " Channel.",Toast.LENGTH_LONG).show();

                //Sending preferences to Homefragment
                SharedPreferences settings = context.getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("channelname", channelName);
                editor.putString("searchval", "");
                editor.commit();

                //Intent intent = new Intent(context, VideoActivity.class);
                //context.startActivity(intent);

                //Intent intent=new Intent(context,webView.class);
                //intent.putExtra("url",modelClassArrayList.get(position).getUrl());
                //context.startActivity(intent);
            }
        });

        // to set data to textview and imageview of each card layout
        ChannelsModel model = ChannelsModelArrayList.get(position);
        holder.channelNameTV.setText(model.getName());
        holder.channelIV.setImageResource(model.getImage());


    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return ChannelsModelArrayList.size();
    }

    // View holder class for initializing of 
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        public CardView cardview;
        private ImageView channelIV;
        private TextView channelNameTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            channelIV = itemView.findViewById(R.id.idIVChannelImage);
            channelNameTV = itemView.findViewById(R.id.idTVChannelName);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }
}
