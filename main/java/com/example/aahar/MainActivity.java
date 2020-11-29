package com.example.aahar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ToolbarActivity {
    private EditText location,cuisine;
    private Button Explore;
    DatabaseHelper databaseHelper;
    static public String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar(R.id.main_page_toolbar);
        databaseHelper =new DatabaseHelper(MainActivity.this);
        location=(EditText)findViewById(R.id.Location);
        cuisine=(EditText)findViewById(R.id.Cuisine);
        Explore=(Button)findViewById(R.id.Explore);
        ArrayList<Data> arr =new ArrayList<>();
        arr=databaseHelper.checkUser();
        if(arr.isEmpty()){
            loginitent();
        }

        Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("location",location.getText().toString().trim());
                intent.putExtra("cuisine",cuisine.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private void loginitent() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}