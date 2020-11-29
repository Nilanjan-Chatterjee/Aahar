package com.example.aahar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "Message from MainActivity";
    private TextView otp;
    int state=0;
    private Button SignUpButton;
    private EditText number,password,name,enterotp;
    String num = "",pass = "",nam = "";
    String val="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        enterotp=(EditText)findViewById(R.id.enterotp);
        number=(EditText)findViewById(R.id.editTextNumber);
        password=(EditText)findViewById(R.id.editTextPassword);
        name=(EditText)findViewById(R.id.editTextName);
        otp=(TextView)findViewById(R.id.otp);
        SignUpButton=(Button)findViewById(R.id.buttonSignUp);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state==0) {
                    num = number.getText().toString().trim();
                    pass = password.getText().toString().trim();
                    nam = name.getText().toString().trim();
                    SignUpButton.setText("SingUp");
                    otp.setVisibility(View.VISIBLE);
                    number.setVisibility(View.INVISIBLE);
                    name.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    enterotp.setVisibility(View.VISIBLE);
                    Random rand =new Random();
                    int int1 = rand.nextInt(1000);
                    int int2 = rand.nextInt(1000);
                    val=val+int1+int2;
                    for(int i=0;i<6-val.length();i++){
                        val=val+'0';
                    }
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                        if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(num,null,val,null,null);
                                Toast.makeText(Signup.this,"OTP sent",Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                Toast.makeText(Signup.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                        }
                    }
                    state=1;

                }
                else {
                    String verify=enterotp.getText().toString().trim();
                    if(val.equals(verify)) {
                        final Data data = new Data(num, pass, nam);
                        Call<SignupResponse> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .createUser(1, num, pass, nam);

                        call.enqueue(new Callback<SignupResponse>() {
                            @Override
                            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                                if (response.body().getMsg().equals("Successful")) {


                                    DatabaseHelper databaseHelper = new DatabaseHelper(Signup.this);
                                    boolean success = databaseHelper.Add(data);
                                    state = 0;
                                    Intent mainintent = new Intent(Signup.this, MainActivity.class);
                                    mainintent.putExtra(EXTRA_MESSAGE, "1");
                                    startActivity(mainintent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<SignupResponse> call, Throwable t) {
                                Toast.makeText(Signup.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        });
                    }
                    else {
                        Toast.makeText(Signup.this, "OTP do not match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}