package com.crdesigns.newfeedj;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<ModelClass> modelClassArrayList;
    MySQLiteHelper db = new MySQLiteHelper(context);
    String stringUrl, stringUrlSource;

    public Adapter(Context context, ArrayList<ModelClass> modelClassArrayList) {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
        db = new MySQLiteHelper(context);
    }

    public void setData(ArrayList<ModelClass> lst){
        this.modelClassArrayList.clear();
        modelClassArrayList.addAll(lst);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoActivity.class);
                context.startActivity(intent);

                //Intent intent=new Intent(context,webView.class);
                //intent.putExtra("url",modelClassArrayList.get(position).getUrl());
                //context.startActivity(intent);
            }
        });
        holder.mtime.setText("Published At:-" + modelClassArrayList.get(position).getPublishedAt());
        holder.mauthor.setText(modelClassArrayList.get(position).getAuthor());
        holder.mheading.setText(modelClassArrayList.get(position).getTitle());
        //holder.mcontent.setText(modelClassArrayList.get(position).getDescription());
        Glide.with(context).load(modelClassArrayList.get(position).getUrlToImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mheading,mcontent,mauthor,mtime;
        CardView cardView;
        ImageView imageView;
        Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mheading=itemView.findViewById(R.id.mainheading);
            //mcontent=itemView.findViewById(R.id.content);
            mauthor=itemView.findViewById(R.id.author);
            mtime=itemView.findViewById(R.id.time);
            imageView=itemView.findViewById(R.id.imageview);
            cardView=itemView.findViewById(R.id.cardview);
            favBtn=itemView.findViewById(R.id.favBtn);

            //add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(context,modelClassArrayList.get(position).getAuthor(),Toast.LENGTH_LONG).show();
                    db.addFavorites(new FavoritesClass(modelClassArrayList.get(position).getAuthor(),modelClassArrayList.get(position).getTitle(),"",modelClassArrayList.get(position).getUrl(), modelClassArrayList.get(position).getUrlToImage(),modelClassArrayList.get(position).getPublishedAt()));
                    favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24);
                }
            });

        }
    }
}
