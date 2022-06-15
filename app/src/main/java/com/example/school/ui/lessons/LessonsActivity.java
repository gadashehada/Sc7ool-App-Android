package com.example.school.ui.lessons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.read_api.ApiLessons;
import com.example.school.read_api.ApiSubjects;
import com.example.school.read_api.ReadApi;
import com.example.school.read_api.ServerCallback;
import com.example.school.ui.subject.Subject_Adapter;
import com.example.school.ui.subject.SubjectsActivity;

import java.util.ArrayList;
import java.util.List;

public class LessonsActivity extends AppCompatActivity {

    RecyclerView rc_lesson;
    Lesson_Adapter adapter;
    List<ApiLessons> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        getSupportActionBar().setTitle("الدروس");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rc_lesson = findViewById(R.id.rc_lessons);
        lessons = new ArrayList<>();

        String classes_id = getIntent().getStringExtra("classes_id");
        String subject_id = getIntent().getStringExtra("subject_id");

        ReadApi.readApi.getLesssons(classes_id , subject_id , new ServerCallback() {
            @Override
            public void onSuccess(Object object) {
                lessons = (List<ApiLessons>) object;

                if(lessons.size() <= 0){
                    Toast.makeText(getApplicationContext(), "لا يوجد لهذا المادة .", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

                adapter = new Lesson_Adapter(lessons , R.layout.item_lessons , getApplicationContext());
                rc_lesson.setAdapter(adapter);

                for (int i = 0 ; i < lessons.size() ; i++){
                    int finalI = i;
                    ReadApi.readApi.getKindClass(lessons.get(i).getKind_id(), new ServerCallback() {
                        @Override
                        public void onSuccess(Object object) {
                            lessons.get(finalI).setKind((String) object);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext() , 1);
        rc_lesson.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
