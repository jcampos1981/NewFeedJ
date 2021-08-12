package com.crdesigns.newfeedj;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    static String api = "420a2bac1afd4f16bb103eda221757c1";
    ArrayList<ModelClass> modelClassArrayList;
    Adapter adapter;
    String country="us";
    private RecyclerView recyclerViewofhome;
    SwipeRefreshLayout sr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.homefragment, null);

        sr=v.findViewById(R.id.swiperefresh);

        recyclerViewofhome=v.findViewById(R.id.recyclerviewofhome);
        modelClassArrayList= new ArrayList<>();
        recyclerViewofhome.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(getContext(),modelClassArrayList);
        recyclerViewofhome.setAdapter(adapter);

        findNews();

        //This is the Search filter -Start
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    findNewsSearch();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sr.setRefreshing(false);
            }
        });
        //This is the Search filter - End

        return  v;
    }

    private void findNews() {
        ApiUtilities.getApiInterface().getNews(country, 100, api).enqueue(new Callback<mainNews>() {
            @Override
            public void onResponse(Call<mainNews> call, Response<mainNews> response) {
                if(response.isSuccessful()){
                    modelClassArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<mainNews> call, Throwable t) {

            }
        });

    }

    public void findNewsSearch() throws UnsupportedEncodingException {

        //Come from Search or Channel

        Context context = getActivity();
        SharedPreferences settings = context.getSharedPreferences("PREFS", 0);
        String s = settings.getString("searchval", null);
        String c = settings.getString("channelname", null);

        if(s.length()>1){
            String encodedQueryString = URLEncoder.encode(s,"UTF-8"); //Encoded for API requirement
            ApiUtilities.getApiInterface().getNewsEverything(encodedQueryString, 10, api).enqueue(new Callback<mainNews>() {
                @Override
                public void onResponse(Call<mainNews> call, Response<mainNews> response) {
                    if(response.isSuccessful()){
                        modelClassArrayList.clear();
                        modelClassArrayList.addAll(response.body().getArticles());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(context,"Results for: " + s,Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<mainNews> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                }
            });
        }
        else{
            ApiUtilities.getApiInterface().getNewsChannel("us",c, 10, api).enqueue(new Callback<mainNews>() {
                @Override
                public void onResponse(Call<mainNews> call, Response<mainNews> response) {
                    if(response.isSuccessful()){
                        modelClassArrayList.clear();
                        modelClassArrayList.addAll(response.body().getArticles());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(context,"Results for: " + c + " filter.",Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<mainNews> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                }
            });
        }


    }

}
