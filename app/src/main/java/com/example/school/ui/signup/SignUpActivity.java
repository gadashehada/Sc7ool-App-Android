package com.example.school.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.read_api.ReadApi;
import com.example.school.read_api.ServerCallback;

public class SignUpActivity extends AppCompatActivity {

    EditText ed_name , ed_email , ed_mobile , ed_password , ed_confirm_password ;
    String name , email , mobile , password , confirm_password ;
    Button btn_signup ;
    TextView tv_login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("إنشاء حساب");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_name = findViewById(R.id.ed_register_firstName);
        ed_email = findViewById(R.id.ed_register_name);
        ed_mobile = findViewById(R.id.ed_register_email);
        ed_password = findViewById(R.id.ed_register_password);
        ed_confirm_password = findViewById(R.id.ed_register_confirm_password);
        btn_signup = findViewById(R.id.btn_register);
        tv_login = findViewById(R.id.tv_register_login);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromInputs();
                checkInputsIsEmpty();

                ReadApi.readApi.context = getApplicationContext();
                ReadApi.readApi.signUp(email, password, mobile, name, new ServerCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        Toast.makeText(SignUpActivity.this, "Done !!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });            }
        });
    }

    private void getDataFromInputs(){
        name = ed_name.getText().toString();
        email = ed_email.getText().toString();
        mobile = ed_mobile.getText().toString();
        password = ed_password.getText().toString();
        confirm_password = ed_confirm_password.getText().toString();
    }

    private void checkInputsIsEmpty() {
        if (TextUtils.isEmpty(name)) {
            ed_name.setError("please enter name");
            ed_name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ed_email.setError("please enter email");
            ed_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            ed_mobile.setError("please enter mobile");
            ed_mobile.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ed_password.setError("please enter password");
            ed_password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirm_password)) {
            ed_confirm_password.setError("please enter confirm password");
            ed_confirm_password.requestFocus();
            return;
        }
        if (!confirm_password.equals(password)){
            ed_confirm_password.setError("كلمة المرور غير متطابقة");
            ed_confirm_password.requestFocus();
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
