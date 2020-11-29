package com.example.aahar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    static public List<Food> foodList = new ArrayList<>();
    static public  int amount;
    private TextView bill ,empty;
    private Button order;
    private CartAdapter adapter;
    private RecyclerView rec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        bill=(TextView)findViewById(R.id.amount);
        order=(Button)findViewById(R.id.order);
        empty=(TextView)findViewById(R.id.empty);
        rec=(RecyclerView)findViewById(R.id.recyclerview);
        rec.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        adapter = new CartAdapter(foodList);
        rec.setAdapter(adapter);
        if(foodList.size()>0){
            empty.setVisibility(View.INVISIBLE);
            bill.setVisibility(View.VISIBLE);
            order.setVisibility(View.VISIBLE);
        }
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> fields = new HashMap<>();
                fields.put("number",MainActivity.number);
                for(int i=0;i<foodList.size();i++){
                    String s=Integer.toString(foodList.get(i).getFood_id());
                    String q=Integer.toString(foodList.get(i).getQuantity());
                    fields.put(s,q);
                }
                fields.put("op","6");
                foodList.clear();
                Call<SignupResponse> call = RetrofitClient.getInstance().getApi().placeOrder(fields);
                call.enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        if(response.body()!=null) {

                        }
                        else {
                            Toast.makeText(CartActivity.this, "1112", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        Toast.makeText(CartActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }
}