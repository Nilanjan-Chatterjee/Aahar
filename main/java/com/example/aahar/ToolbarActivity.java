package com.example.aahar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ToolbarActivity extends AppCompatActivity {
    public Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }
    public void initToolbar(int toolbarId)
    {

        myToolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.item_cart) {
            Intent intent = new Intent(ToolbarActivity.this, CartActivity.class);
            startActivity(intent);

        }
        if(item.getItemId()==R.id.logout){
            DatabaseHelper databaseHelper = new DatabaseHelper(ToolbarActivity.this);
            boolean del = databaseHelper.delete();
            Intent intent = new Intent(ToolbarActivity.this,Signup.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


        return true;
    }


}