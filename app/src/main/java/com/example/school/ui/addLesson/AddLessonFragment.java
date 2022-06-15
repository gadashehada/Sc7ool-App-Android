package com.example.school.ui.addLesson;


import android.Manifest;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.read_api.ApiClasses;
import com.example.school.read_api.ApiKind;
import com.example.school.read_api.ApiSubjects;
import com.example.school.read_api.ReadApi;
import com.example.school.read_api.ServerCallback;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;


public class AddLessonFragment extends Fragment {

    Button btn_add_lessons ;
    Spinner spinner_classes , spinner_subjects , spinner_kinds ;
    EditText ed_title , ed_description ;
    ImageView im_lessons_choose ;
    List<ApiClasses> classes_list ;
    List<ApiSubjects> subjects_list ;
    List<ApiKind> kinds_list ;
    List<String> kinds , subjects , classes ;
    String title , message , classes_id , subject_id , kind_id ;
    Uri image_uri ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_lesson, container, false);
        btn_add_lessons = view.findViewById(R.id.btn_add_lessons);
        spinner_classes = view.findViewById(R.id.spinner_grade);
        spinner_kinds = view.findViewById(R.id.spinner_kind_lessons);
        spinner_subjects = view.findViewById(R.id.spinner_subjects);
        ed_description = view.findViewById(R.id.ed_message_lessons);
        ed_title = view.findViewById(R.id.ed_lesson_title);
        im_lessons_choose = view.findViewById(R.id.im_choose_lessons);
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        kinds = new ArrayList<>(); kinds.add("النوع ....");
        subjects = new ArrayList<>(); subjects.add("المادة ....");
        classes = new ArrayList<>(); classes.add("الصف ....");

        ArrayAdapter<String> adapter_classes = new ArrayAdapter<String>(getContext() , android.R.layout.simple_spinner_item , classes);
        ArrayAdapter<String> adapter_subject = new ArrayAdapter<String>(getContext() , android.R.layout.simple_spinner_item , subjects);
        ArrayAdapter<String> adapter_kind = new ArrayAdapter<String>(getContext() , android.R.layout.simple_spinner_item , kinds);

        spinner_classes.setAdapter(adapter_classes);
        spinner_subjects.setAdapter(adapter_subject);
        spinner_kinds.setAdapter(adapter_kind);

        spinner_classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    classes_id = classes_list.get(position-1).getId();
                    ReadApi.readApi.getSubject(classes_id , new ServerCallback() {
                        @Override
                        public void onSuccess(Object object) {
                            subjects_list = (List<ApiSubjects>) object;
                            for (ApiSubjects s : subjects_list) { subjects.add(s.getName()); }
                            adapter_subject.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    subject_id = subjects_list.get(position - 1).getId();
                    classes_id = subjects_list.get(position - 1).getClasses_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_kinds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    kind_id = kinds_list.get(position - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        im_lessons_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_cover_image();
            }
        });

        ReadApi.readApi.getClasses(new ServerCallback() {
            @Override
            public void onSuccess(Object object) {
                classes_list = (List<ApiClasses>) object;
                for (ApiClasses c : classes_list) { classes.add(c.getName()); }
                adapter_classes.notifyDataSetChanged();
            }
        });

        ReadApi.readApi.getKinds(new ServerCallback() {
            @Override
            public void onSuccess(Object object) {
                kinds_list = (List<ApiKind>) object;
                for (ApiKind k : kinds_list) { kinds.add(k.getType()); }
                adapter_kind.notifyDataSetChanged();
            }
        });

        btn_add_lessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = ed_title.getText().toString();
                message = ed_description.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    ed_title.setError("Please enter title");
                    ed_title.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(message)) {
                    ed_description.setError("Please enter description");
                    ed_description.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(classes_id)) {
                    spinner_classes.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(subject_id)) {
                    spinner_subjects.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(kind_id)) {
                    spinner_kinds.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(image_uri.getPath())) {
                    Toast.makeText(getContext(), "please choose image !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ReadApi.readApi.context = getContext();
                ReadApi.readApi.add_Lessons(
                        title ,
                        image_uri.getPath() ,
                        message ,
                        classes_id ,
                        subject_id ,
                        kind_id
                );
            }
        });

   }

    public void get_cover_image(){
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        TedBottomPicker.with(getActivity())
                                .setPeekHeight(1600)
                        .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {
                                Log.d("ttt", "onImageSelected: " + uri.getPath());
                                image_uri = uri ;
                            }
                        });
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

    }



}
