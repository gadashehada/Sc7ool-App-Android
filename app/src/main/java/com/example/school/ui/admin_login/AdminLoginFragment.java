package com.example.school.ui.admin_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.school.MainActivity;
import com.example.school.R;
import com.example.school.read_api.ApiUser;
import com.example.school.read_api.ReadApi;
import com.example.school.read_api.ServerCallback;
import com.example.school.ui.signup.SignUpActivity;

public class AdminLoginFragment extends Fragment {

    public static ApiUser user = null ;

    Button btn;
    EditText ed_email , ed_password ;
    String email , password ;
    TextView tv_create_account ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_login, container, false);
        btn = v.findViewById(R.id.login);
        ed_email = v.findViewById(R.id.username);
        ed_password = v.findViewById(R.id.password);
        tv_create_account = v.findViewById(R.id.tv_go_create_account);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , SignUpActivity.class);
                startActivity(i);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = ed_email.getText().toString();
                password = ed_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    ed_email.setError("Please enter email");
                    ed_email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ed_password.setError("Please enter password");
                    ed_password.requestFocus();
                    return;
                }
                ReadApi.readApi.context = getContext();
                ReadApi.readApi.login(email, password, new ServerCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        user = (ApiUser) object;
                        Intent i = new Intent(getContext() , MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        });
    }
}
