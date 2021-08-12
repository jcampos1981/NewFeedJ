package com.crdesigns.newfeedj;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {
    String api = "420a2bac1afd4f16bb103eda221757c1";
    ArrayList<ModelClass> modelClassArrayList;
    Adapter adapter;
    String country="us";
    private RecyclerView recyclerViewofhome;

    ArrayList<ModelClass> artics;
    private ModelClass md;

    SwipeRefreshLayout sr;
    MySQLiteHelper db;
    private String author, title, description,url,urlToImage,publishedAt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.favoritefragment, null);

        sr=v.findViewById(R.id.swiperefresh);

        recyclerViewofhome=v.findViewById(R.id.recyclerviewofhome);
        modelClassArrayList= new ArrayList<>();
        recyclerViewofhome.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(getContext(),modelClassArrayList);
        recyclerViewofhome.setAdapter(adapter);
        db = new MySQLiteHelper(this.getContext());
        findNews();

        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findNews();
                sr.setRefreshing(false);
            }
        });

        return  v;
    }
    private void findNews() {
        artics = new ArrayList<ModelClass>();
        List<String> lables = db.getFavorites();
        if(lables.size() >= 1) {
            if (!lables.get(0).contains("null")) {
                for (int i = 0; i < lables.size(); i++) {
                    String[] separated = lables.get(i).split("\\|");
                    assignVar(separated);
                }
            }
        }
        if(artics!=null) {
            adapter.setData(artics);
        }
    }
    private void assignVar(String[] str) {
        if (str.length != 0) {
            author = str[0];
            title = str[1];
            description = str[2];
            url = str[3];
            urlToImage = str[4];
            publishedAt = str[5];

            md = new ModelClass(author, title, description, url, urlToImage, publishedAt);
            artics.add(md);
        }
    }

}