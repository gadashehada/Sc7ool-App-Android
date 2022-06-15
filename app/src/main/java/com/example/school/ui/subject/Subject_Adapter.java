package com.example.school.ui.subject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.R;
import com.example.school.read_api.ApiSubjects;
import com.example.school.ui.lessons.LessonsActivity;

import java.util.ArrayList;
import java.util.List;

public class Subject_Adapter extends RecyclerView.Adapter<ViewHolder> {

    List<ApiSubjects> subjectName;
    int itemLayoutId;
    Context context;

    public Subject_Adapter(List<ApiSubjects> subjectName ,int itemLayoutId , Context context){
        this.itemLayoutId = itemLayoutId;
        this.subjectName = subjectName;
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
        ApiSubjects apiSubjects = subjectName.get(position);
        holder.name.setText(apiSubjects.getName());
        holder.number.setText((position+1)+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , LessonsActivity.class);
                i.putExtra("classes_id" , apiSubjects.getClasses_id());
                i.putExtra("subject_id" , apiSubjects.getId());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectName.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView number, name;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        number = itemView.findViewById(R.id.tv_subject_number);
        name = itemView.findViewById(R.id.tv_subject_name1);
    }
}