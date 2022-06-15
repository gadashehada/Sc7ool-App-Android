package com.example.school.ui.lessons;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.school.R;
import com.example.school.read_api.ApiLessons;
import com.example.school.ui.Lesson_Details.LessonDetailsActivity;
import com.example.school.ui.Lesson_Details.Lesson_Details_Adapter;

import java.util.ArrayList;
import java.util.List;

public class Lesson_Adapter extends RecyclerView.Adapter<ViewHolder> {

    List<ApiLessons> lessons;
    int itemLayoutId;
    Context context;

    public Lesson_Adapter(List<ApiLessons> lessons ,int itemLayoutId , Context context){
        this.itemLayoutId = itemLayoutId;
        this.lessons = lessons;
        this.context = context;
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
       ApiLessons lesson = lessons.get(position);
        holder.name.setText(lesson.getKind());
        holder.title.setText(lesson.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , LessonDetailsActivity.class);
                i.putExtra("lesson"  , lesson);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView title, name;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_lesson_title);
        name = itemView.findViewById(R.id.tv_lesson_name);
    }
}
