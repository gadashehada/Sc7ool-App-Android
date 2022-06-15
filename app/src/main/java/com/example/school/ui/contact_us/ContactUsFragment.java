package com.example.school.ui.contact_us;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.school.R;
import com.example.school.read_api.ReadApi;

public class ContactUsFragment extends Fragment {

    EditText ed_name , ed_email , ed_mobile , ed_message ;
    TextView tv_email , tv_mobile , tv_tel ;
    Button btn_contact ;
    String name , email , mobile , message ;
    ImageView im_facebook , im_instgram , im_twitter ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ed_email = root.findViewById(R.id.ed_contact_email);
        ed_name = root.findViewById(R.id.ed_contact_name);
        ed_mobile = root.findViewById(R.id.ec_contact_mobile);
        ed_message = root.findViewById(R.id.ed_contact_message);
        tv_email = root.findViewById(R.id.textView4);
        tv_mobile = root.findViewById(R.id.tv_tel);
        tv_tel = root.findViewById(R.id.tv_phone);
        im_facebook = root.findViewById(R.id.im_facebook);
        im_twitter = root.findViewById(R.id.im_twitter);
        im_instgram = root.findViewById(R.id.im_instagram);
        btn_contact = root.findViewById(R.id.btn_contact);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(Intent.EXTRA_EMAIL , new String[]{tv_email.getText().toString()});
                startActivity(Intent.createChooser(i , ""));
            }
        });

        tv_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL , Uri.parse("tel:" + tv_mobile.getText().toString()));
                startActivity(i);
            }
        });

        tv_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL , Uri.parse("tel:" + tv_tel.getText().toString()));
                startActivity(i);
            }
        });

        im_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getContext().getPackageManager().getPackageInfo("com.facebook.katana" , 0);
                    startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse("fb://profile/100017513328934")));
                } catch (PackageManager.NameNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse("http://www.facebook.com/profile.php?id=100017513328934")));
                }
            }
        });

        im_instgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getContext().getPackageManager().getPackageInfo("com.instagram.android" , 0);
                    startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse("http://instagram.com/_u/ghada_sameer_sh")));
                } catch (PackageManager.NameNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse("https://instagram.com/ghada_sameer_sh")));
                }
            }
        });

        im_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getContext().getPackageManager().getPackageInfo("com.twitter.android" , 0);
                    startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse("twitter://user?screen_name=id")));
                } catch (PackageManager.NameNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse("https://twitter.com/ghada_profilename")));
                }
            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ed_name.getText().toString();
                email = ed_email.getText().toString();
                mobile = ed_mobile.getText().toString();
                message = ed_message.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    ed_email.setError("Please enter email");
                    ed_email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    ed_name.setError("Please enter name");
                    ed_name.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    ed_mobile.setError("Please enter mobile");
                    ed_mobile.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(message)) {
                    ed_message.setError("Please enter message");
                    ed_message.requestFocus();
                    return;
                }
                ReadApi.readApi.context = getContext();
                ReadApi.readApi.contactUs(email , mobile , name , message);
            }
        });
    }
}