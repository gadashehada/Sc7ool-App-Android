package com.example.school.ui.subject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.read_api.ApiSubjects;
import com.example.school.read_api.ReadApi;
import com.example.school.read_api.ServerCallback;
import com.example.school.ui.home.Home_Adapter;

import java.util.ArrayList;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity {

    RecyclerView rc_subject;
    Subject_Adapter adapter;
    List<ApiSubjects> subject_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        getSupportActionBar().setTitle("المواد");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rc_subject = findViewById(R.id.rc_subjects);
        subject_name = new ArrayList<>();

        String class_id = getIntent().getStringExtra("class_id");

        ReadApi.readApi.getSubject(class_id , new ServerCallback() {
            @Override
            public void onSuccess(Object object) {
                subject_name = (List<ApiSubjects>) object;
                if(subject_name.size() <= 0){
                    Toast.makeText(SubjectsActivity.this, "لا يوجد مواد لهذا الصف .", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                adapter = new Subject_Adapter(subject_name , R.layout.item_subject , getApplicationContext());
                rc_subject.setAdapter(adapter);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext() , 1);
        rc_subject.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
