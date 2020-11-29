package com.example.aahar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends ToolbarActivity {
    private TextView textview,item_not_found;
    private RecyclerView rec;
    private FoodAdapter adapter;

    private List<Food> foodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        textview=(TextView)findViewById(R.id.edittext);
        item_not_found=(TextView)findViewById(R.id.item_not_found);
        initToolbar(R.id.main_page_toolbar);
        rec=(RecyclerView)findViewById(R.id.recyclerview);
        rec.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        String s=getIntent().getExtras().get("location").toString();
        String t=getIntent().getExtras().get("cuisine").toString();
        textview.setText(s);
        Call<foodresponse> call = RetrofitClient.getInstance().getApi().FetchFoodDetails(3,s,t);
        call.enqueue(new Callback<foodresponse>() {
            @Override
            public void onResponse(Call<foodresponse> call, Response<foodresponse> response) {
                foodList=response.body().getFood();
                adapter = new FoodAdapter(getBaseContext(),foodList);
                rec.setAdapter(adapter);
                if(foodList.size()==0){
                    item_not_found.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<foodresponse> call, Throwable t) {
                Toast.makeText(ListActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}