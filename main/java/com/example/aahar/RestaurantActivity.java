package com.example.aahar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends ToolbarActivity{
    static public TextView hotel_name,hotel_address,food,food_cuisine,quantity,food_price;
    static public ImageView food_image;
    static public int food_id,restaurant_id;
    static  String title,cuisine,image,name,price,location,city,discount;
    private Button add,subtract,add_item_to_cart;
    private RecyclerView rec;
    private List<Food> foodList;
    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        initToolbar(R.id.main_page_toolbar);
        String foodid=getIntent().getExtras().get("food_id").toString().trim();
        food_id=Integer.parseInt(foodid);
        restaurant_id=Integer.parseInt(getIntent().getExtras().get("restaurant_id").toString());
        location=getIntent().getExtras().get("location").toString();
        city=getIntent().getExtras().get("city").toString();
        price=getIntent().getExtras().get("price").toString();
        discount=getIntent().getExtras().get("discount").toString();
        image=getIntent().getExtras().get("image").toString();
        cuisine=getIntent().getExtras().get("cuisine").toString();
        name=getIntent().getExtras().get("name").toString();
        title=getIntent().getExtras().get("title").toString();
        hotel_name=(TextView)findViewById(R.id.hotel_name);
        add_item_to_cart=(Button) findViewById(R.id.add_item_to_cart);
        hotel_address=(TextView)findViewById(R.id.hotel_address);
        food=(TextView)findViewById(R.id.food);
        food_cuisine=(TextView)findViewById(R.id.cuisine);
        food_image=(ImageView)findViewById(R.id.food_image);
        food_price=(TextView)findViewById(R.id.price);
        add=(Button)findViewById(R.id.add);
        subtract=(Button)findViewById(R.id.subtract);
        quantity=(TextView)findViewById(R.id.quantity);
        rec=(RecyclerView)findViewById(R.id.recyclerview);
        rec.setLayoutManager(new LinearLayoutManager(RestaurantActivity.this));
        hotel_name.setText(name);
        hotel_address.setText(location+','+city);
        food.setText(title);
        food_cuisine.setText(cuisine);
        food_price.setText(price);
        String url="http://192.168.43.72.:8080/aahar/images/"+image;
        LoadImage loadimage = new LoadImage(food_image);
        loadimage.execute(url);
        add_item_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean present=false;
                int quant=Integer.parseInt(quantity.getText().toString().trim());
                CartActivity.amount=CartActivity.amount+quant*Integer.parseInt(price);
                for(int i=0;i<CartActivity.foodList.size();i++){
                    if(CartActivity.foodList.get(i).getFood_id()==food_id) {
                        CartActivity.foodList.get(i).setQuantity( CartActivity.foodList.get(i).getQuantity() + quant);
                        present=true;
                        break;
                    }
                    }
                if(present==false) {
                    Food addfood = new Food(food_id, title, restaurant_id, cuisine, price, discount, image, name, location, city, quant);
                    CartActivity.foodList.add(addfood);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= quantity.getText().toString().trim();
                Integer i=Integer.parseInt(s);
                if(i<1000){
                    i++;
                    quantity.setText(i.toString());
                }
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=quantity.getText().toString().trim();
                Integer i=Integer.parseInt(s);
                if(i>1){
                    i--;
                    quantity.setText(i.toString());

                }
            }
        });
        Call<foodresponse> call = RetrofitClient.getInstance().getApi().RestaurantDetails(5,1);
        call.enqueue(new Callback<foodresponse>() {
            @Override
            public void onResponse(Call<foodresponse> call, Response<foodresponse> response) {
                foodList=response.body().getFood();
                String s=String.valueOf(foodList.size());
                adapter = new RestaurantAdapter(getBaseContext(),foodList);
                rec.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<foodresponse> call, Throwable t) {
                Toast.makeText(RestaurantActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });




    }
    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView imageView){
            this.imageView=imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink=strings[0];
            Bitmap bitmap=null;
            try{
                InputStream inputStream =new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}