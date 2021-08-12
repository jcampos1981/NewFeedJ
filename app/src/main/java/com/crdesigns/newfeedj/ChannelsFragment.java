package com.crdesigns.newfeedj;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;

public class ChannelsFragment extends Fragment {
    private RecyclerView courseRV;

    // Arraylist for storing data
    private ArrayList<ChannelsModel> channelsModelArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.channels_main, null);
        courseRV=v.findViewById(R.id.idRVCourse);

        // here we have created new array list and added data to it.
        channelsModelArrayList = new ArrayList<>();
        channelsModelArrayList.add(new ChannelsModel("Business", R.drawable.ic_business_24));
        channelsModelArrayList.add(new ChannelsModel("Entertainment", R.drawable.ic_entretainment_24));
        channelsModelArrayList.add(new ChannelsModel("General", R.drawable.ic_general_24));
        channelsModelArrayList.add(new ChannelsModel("Health", R.drawable.ic_health_24));
        channelsModelArrayList.add(new ChannelsModel("Science", R.drawable.ic_science_24));
        channelsModelArrayList.add(new ChannelsModel("Sports", R.drawable.ic_sport_24));
        channelsModelArrayList.add(new ChannelsModel("Technology", R.drawable.ic_tech_24));

        // we are initializing our adapter class and passing our arraylist to it.
        ChannelsAdapter channelsAdapter = new ChannelsAdapter(getContext(), channelsModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(channelsAdapter);

        return  v;
    }
}
