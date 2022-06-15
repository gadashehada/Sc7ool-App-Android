package com.example.school.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.school.ui.News_Details.NewsDetailsActivity;
import com.example.school.R;
import com.example.school.read_api.ApiNews;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<ApiNews> mSliderItems;

    public SliderAdapter(Context context , List<ApiNews> sliderItems) {
        this.context = context;
        this.mSliderItems = sliderItems;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        ApiNews sliderItem = mSliderItems.get(position);
        viewHolder.textViewDescription.setText(sliderItem.getTitle());

        if (sliderItem.getImage().equals("http://test.hexacit.com/uploads/images/news") || sliderItem.getImage().isEmpty()){
            viewHolder.tv_no_image.setVisibility(View.VISIBLE);
            viewHolder.imageViewBackground.setVisibility(View.INVISIBLE);
            sliderItem.setImage("");
        }else{
            viewHolder.tv_no_image.setVisibility(View.INVISIBLE);
            viewHolder.imageViewBackground.setVisibility(View.VISIBLE);
            Picasso.get().load(sliderItem.getImage()).into(viewHolder.imageViewBackground);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , NewsDetailsActivity.class);
                i.putExtra("news"  , sliderItem);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        ImageView imageViewBackground;
        TextView textViewDescription , tv_no_image;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.im_home_news);
            textViewDescription = itemView.findViewById(R.id.tv_news_title);
            tv_no_image = itemView.findViewById(R.id.tv_no_image);
        }
    }

}
