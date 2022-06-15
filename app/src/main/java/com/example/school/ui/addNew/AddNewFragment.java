package com.example.school.ui.addNew;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.school.R;
import com.example.school.read_api.ReadApi;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

import static android.app.Activity.RESULT_OK;

public class AddNewFragment extends Fragment {

    EditText ed_title , ed_description ;
    ImageView im_choose_image ;
    Button add_news ;
    String title , description ;
    Uri image_uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_new, container, false);
        ed_title = view.findViewById(R.id.ed_news_title);
        ed_description = view.findViewById(R.id.ed_news_description);
        im_choose_image = view.findViewById(R.id.im_choose_image_news);
        add_news = view.findViewById(R.id.btn_add_news);
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        im_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_cover_image();
            }
        });

        add_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = ed_title.getText().toString();
                description = ed_description.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    ed_title.setError("Please enter title");
                    ed_title.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    ed_description.setError("Please enter description");
                    ed_description.requestFocus();
                    return;
                }

                ReadApi.readApi.context = getContext();
                ReadApi.readApi.add_News(title , image_uri.getPath() , description);

                ed_title.setText("");
                ed_description.setText("");
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
