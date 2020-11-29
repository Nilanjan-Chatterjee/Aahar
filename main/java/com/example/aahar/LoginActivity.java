package com.example.aahar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView haveaccount;
    EditText number,password;
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        haveaccount=(TextView)findViewById(R.id.textViewRegister);
        Login=(Button)findViewById(R.id.buttonLogin);
        number=(EditText) findViewById(R.id.editTextNumber);
        password=(EditText) findViewById(R.id.editTextPassword);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String num=number.getText().toString().trim();
        String pass=password.getText().toString().trim();
        Call<SignupResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(2,num,pass);

        //final Data data = new Data(num, pass, "atokini");
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                Toast.makeText(LoginActivity.this,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                if(response.body().getMsg().equals("Successful")){
                    String num=number.toString().trim();
                    String pass=password.toString().trim();
                    Data data = new Data(num, pass, response.body().getname());

                    Toast.makeText(LoginActivity.this,response.body().getname(),Toast.LENGTH_SHORT).show();
                    DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);
                    boolean success = databaseHelper.Add(data);
                    Toast.makeText(LoginActivity.this,"Success"+success,Toast.LENGTH_SHORT).show();

                    Intent mainintent= new Intent(LoginActivity.this,MainActivity.class);
                    mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainintent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}