package com.example.coosin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    TextView txtLogin;
    EditText edtFullname;
    EditText edtPhone;
    EditText edtPass;
    EditText edtReppass;
    Button btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtFullname = findViewById(R.id.input_fullname);
        edtPhone = findViewById(R.id.input_phone_num);
        edtPass = findViewById(R.id.input_pass_sign);
        edtReppass = findViewById(R.id.input_replay_pass);
        txtLogin = findViewById(R.id.txt_login);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }
    private void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
