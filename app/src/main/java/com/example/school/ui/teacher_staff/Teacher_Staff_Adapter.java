package com.example.school.ui.teacher_staff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.R;
import com.example.school.read_api.ApiTeacher;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Teacher_Staff_Adapter extends RecyclerView.Adapter<ViewHolder> {

    List<ApiTeacher> teachersStaff;
    int itemLayoutId;

    public Teacher_Staff_Adapter(List<ApiTeacher> teachersStaff ,int itemLayoutId){
        this.itemLayoutId = itemLayoutId;
        this.teachersStaff = teachersStaff;
        this.itemLayoutId = itemLayoutId;
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
        ApiTeacher teacherStaff = teachersStaff.get(position);
        holder.teacher_subject.setText(teacherStaff.getEmail());
        holder.name.setText(teacherStaff.getName());
        Picasso.get().load(teacherStaff.getImage_profile()).into(holder.im_teacher);

    }

    @Override
    public int getItemCount() {
        return teachersStaff.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView teacher_subject, name;
    ImageView im_teacher;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_teacher_name);
        teacher_subject = itemView.findViewById(R.id.tv_teacher_subject);
        im_teacher = itemView.findViewById(R.id.im_teacher);
    }
}
