package com.example.school.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.R;
import com.example.school.read_api.ApiClasses;
import com.example.school.read_api.ApiNews;
import com.example.school.read_api.ReadApi;
import com.example.school.read_api.ServerCallback;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView rc_classes ;
    List<ApiClasses> classes_name;
    List<ApiNews> news ;
    Home_Adapter adapter;
    SliderView sliderView ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rc_classes = getView().findViewById(R.id.rc_home);
        sliderView = getView().findViewById(R.id.imageSlider);
        classes_name = new ArrayList<>();
        news = new ArrayList<>();

        ReadApi.readApi.requestqueue(getContext());
        ReadApi.readApi.getClasses(new ServerCallback() {
            @Override
            public void onSuccess(Object object) {
                classes_name = (List<ApiClasses>) object;
                adapter = new Home_Adapter(classes_name , R.layout.item_home , getContext());
                rc_classes.setAdapter(adapter);
            }
        });

        ReadApi.readApi.getNews(new ServerCallback() {
            @Override
            public void onSuccess(Object object) {
                news  = (List<ApiNews>) object;
                sliderView.setSliderAdapter(new SliderAdapter(getContext() , news));
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext() , 2);
        rc_classes.setLayoutManager(gridLayoutManager);


        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

    }
}