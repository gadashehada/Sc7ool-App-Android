package com.example.school.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.R;
import com.example.school.read_api.ApiClasses;
import com.example.school.ui.subject.SubjectsActivity;

import java.util.ArrayList;
import java.util.List;

public class Home_Adapter extends RecyclerView.Adapter<ViewHolder> {

    List<ApiClasses> classesName;
    int itemLayoutId;
    Context context;

    public Home_Adapter(List<ApiClasses> classesName , int itemLayoutId , Context context){
        this.itemLayoutId = itemLayoutId;
        this.classesName = classesName;
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
        ApiClasses apiClasses = classesName.get(position);
        holder.name.setText(apiClasses.getName());
        holder.number.setText(position + 1 + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , SubjectsActivity.class);
                i.putExtra("class_id" , apiClasses.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classesName.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView number, name;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        number = itemView.findViewById(R.id.tv_class_number);
        name = itemView.findViewById(R.id.tv_class_name);
    }
}
