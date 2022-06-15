package com.example.school.ui.Lesson_Details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.R;
import com.example.school.read_api.ApiLessons;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Lesson_Details_Adapter extends RecyclerView.Adapter<ViewHolder> {

    ArrayList<String> lessons_image;
    int itemLayoutId;

    public Lesson_Details_Adapter(ArrayList<String> lessons_image ,int itemLayoutId){
        this.itemLayoutId = itemLayoutId;
        this.lessons_image = lessons_image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String image = lessons_image.get(position);

        if (image.equals("http://test.hexacit.com/uploads/images/lessons") || image.isEmpty()){
            holder.tv_no_image.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.INVISIBLE);
        }else{
            holder.tv_no_image.setVisibility(View.INVISIBLE);
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(image).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return lessons_image.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView tv_no_image ;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView13);
        tv_no_image = itemView.findViewById(R.id.tv_no_image);
    }
}
