package com.example.school.ui.teacher_staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.R;
import com.example.school.read_api.ApiSubjects;
import com.example.school.read_api.ApiTeacher;
import com.example.school.read_api.ReadApi;
import com.example.school.read_api.ServerCallback;
import com.example.school.ui.subject.Subject_Adapter;

import java.util.ArrayList;
import java.util.List;

public class TeacherStaffFragment extends Fragment {

    RecyclerView rc_teacher_staff;
    Teacher_Staff_Adapter adapter;
    List<ApiTeacher> teachersStaff;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_teacher_staff, container, false);
        rc_teacher_staff = v.findViewById(R.id.rc_staff);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        teachersStaff = new ArrayList<>();

        ReadApi.readApi.getTeachers(new ServerCallback() {
            @Override
            public void onSuccess(Object object) {
                teachersStaff = (List<ApiTeacher>) object;
                adapter = new Teacher_Staff_Adapter(teachersStaff , R.layout.item_teacher_staff);
                rc_teacher_staff.setAdapter(adapter);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext() , 2);
        rc_teacher_staff.setLayoutManager(gridLayoutManager);
    }
}