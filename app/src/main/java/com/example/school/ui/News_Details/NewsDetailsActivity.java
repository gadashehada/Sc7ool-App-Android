package com.example.school.ui.News_Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.read_api.ApiNews;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    ApiNews apiNews ;
    TextView title , description , no_image ;
    ImageView im_news ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        getSupportActionBar().setTitle("الأخبار");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.tv_news_details_title);
        description = findViewById(R.id.tv_description_nes_details);
        im_news = findViewById(R.id.im_news_details);
        no_image = findViewById(R.id.tv_no_image);

        apiNews = (ApiNews) getIntent().getSerializableExtra("news");

        title.setText(apiNews.getTitle());
        description.setText(apiNews.getDescription());

        if (apiNews.getImage().equals("")){
            no_image.setVisibility(View.VISIBLE);
            im_news.setVisibility(View.INVISIBLE);
        }else {
            no_image.setVisibility(View.INVISIBLE);
            im_news.setVisibility(View.VISIBLE);
            Picasso.get().load(apiNews.getImage()).into(im_news);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
