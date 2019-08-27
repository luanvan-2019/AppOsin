package com.example.coosin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    TextView txtSignup;
    EditText edtUser,edtPass;
    Button btnlogin;
    ProgressBar pbbar;

    Connection connect;
    String UserNameStr,PasswordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.input_user);
        edtPass = findViewById(R.id.input_password);
        btnlogin = findViewById(R.id.btn_login_1);
        txtSignup = findViewById(R.id.txt_signup);
        pbbar = findViewById(R.id.progressBar);

        //progress khong chay khi chua nhan login
        pbbar.setVisibility(View.GONE);

        //su kien click button login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserNameStr = edtUser.getText().toString();
                PasswordStr = edtPass.getText().toString();

                checklogin check_Login = new checklogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                check_Login.execute(UserNameStr,PasswordStr);
            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignupActivity();
            }
        });
    }
    private void openSignupActivity(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
    public class checklogin extends AsyncTask<String,String,String>
    {
        String ConnectionResult = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {

            pbbar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String result)
        {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
        }
        @Override
        protected String doInBackground(String... params) {
            String usernam = UserNameStr;
            String passwordd =PasswordStr;
            if(usernam.trim().equals("")|| passwordd.trim().equals(""))
                ConnectionResult = "Vui lòng nhập đầy đủ thông tin!";
            else
            {
                try
                {
                    com.example.coosin.Connection conStr=new com.example.coosin.Connection();
                    connect =conStr.CONN();        // Connect to database
                    if (connect == null)
                    {
                        ConnectionResult = "Không có kết nối mạng!";
                    }
                    else
                    {
                        // Change below query according to your own database.
                        String query = "select * from CUSTOMER where Phone_number= '" + usernam.toString() + "' and Pass = '"+ passwordd.toString() +"'  ";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            ConnectionResult = "Đăng nhập thành công!";
                            isSuccess=true;
                            connect.close();
                        }
                        else
                        {
                            ConnectionResult = "Sai số điện thoại hoặc mật khẩu!";
                            isSuccess = false;
                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    ConnectionResult = ex.getMessage();
                }
            }
            return ConnectionResult;
        }
    }


}
