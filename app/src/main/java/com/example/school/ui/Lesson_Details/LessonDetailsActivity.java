package com.example.school.ui.Lesson_Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.read_api.ApiComments;
import com.example.school.read_api.ApiLessons;
import com.example.school.read_api.ReadApi;
import com.example.school.read_api.ServerCallback;
import com.example.school.ui.admin_login.AdminLoginFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LessonDetailsActivity extends AppCompatActivity {

    TextView tv_title , tv_details ;
    EditText ed_comment ;
    TextView im_send ;
    RecyclerView rc_lessons_details , rc_comments ;
    Lesson_Details_Adapter adapter;
    CommentsAdapter adapter_comments ;
    ArrayList<String> lessons_images;
    ApiLessons apiLesson ;
    List<ApiComments> apiComments ;
    String comment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);

        getSupportActionBar().setTitle("الدروس");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rc_lessons_details = findViewById(R.id.rc_lesson_image);
        rc_comments = findViewById(R.id.rc_user_comments);
        tv_title = findViewById(R.id.tv_lesson_details_title);
        tv_details = findViewById(R.id.tv_invite_friend_par);
        ed_comment = findViewById(R.id.ed_comment);
        im_send = findViewById(R.id.im_send_comments);
        lessons_images = new ArrayList<>();
        apiComments = new ArrayList<>();

        apiLesson = (ApiLessons) getIntent().getSerializableExtra("lesson");
        lessons_images.add(apiLesson.getImages());
        tv_title.setText(apiLesson.getTitle());
        tv_details.setText(apiLesson.getDetails());

        adapter = new Lesson_Details_Adapter(lessons_images , R.layout.item_lessons_details);
        rc_lessons_details.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rc_lessons_details.setLayoutManager(llm);

        LinearLayoutManager llm2 = new LinearLayoutManager(getApplicationContext());
        rc_comments.setLayoutManager(llm2);

        ReadApi.readApi.getLessonsDetails(apiLesson.getId(), new ServerCallback() {
            @Override
            public void onSuccess(Object object) {
                apiComments = (List<ApiComments>) object;
                adapter_comments = new CommentsAdapter(apiComments , R.layout.item_comments);
                rc_comments.setAdapter(adapter_comments);

            }
        });

        im_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment = ed_comment.getText().toString();

                if (TextUtils.isEmpty(comment)) {
                    ed_comment.setError("Please enter comment");
                    ed_comment.requestFocus();
                    return;
                }
                ReadApi.readApi.context = getApplicationContext();
                ReadApi.readApi.add_comment(apiLesson.getId(), comment , new ServerCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        ApiComments c = new ApiComments();
                        c.setUser_image(AdminLoginFragment.user.getImage_profile());
                        c.setComment_body(comment);
                        c.setUser_name(AdminLoginFragment.user.getName());

                        ed_comment.setText("");
                        apiComments.add(c);
                        adapter_comments.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}

