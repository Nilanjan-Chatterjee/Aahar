package com.example.aahar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.holder> {
    private List<Food> foodList;
    private Context ctx;
    public FoodAdapter(Context ctx, List<Food> foodList){
        this.ctx=ctx;
        this.foodList=foodList;
    }
    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.holder holder, int position) {
        final Food food= foodList.get(position);
        holder.hotel_name.setText(food.getName());
        holder.hotel_address.setText(food.getLocation());
        holder.food.setText(food.getTitle());
        holder.cuisine.setText(food.getCuisine());
        holder.price.setText(food.getPrice());
        String foodimage=food.getImage();
        String url="http://192.168.43.72.:8080/aahar/images/"+foodimage;
        LoadImage loadimage = new LoadImage(holder.food_image);
        loadimage.execute(url);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, RestaurantActivity.class);
                intent.putExtra("food_id",food.getFood_id());
                intent.putExtra("restaurant_id",food.getRestaurant_id());
                intent.putExtra("price",food.getPrice());
                intent.putExtra("discount",food.getDiscount());
                intent.putExtra("image",food.getImage());
                intent.putExtra("location",food.getLocation());
                intent.putExtra("city",food.getCity());
                intent.putExtra("cuisine",food.getCuisine());
                intent.putExtra("name",food.getName());
                intent.putExtra("title",food.getTitle());
                ctx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow, parent, false);
        return new holder(view);

    }

    public class holder extends RecyclerView.ViewHolder {
            ImageView food_image;
            String image;
            TextView hotel_name,hotel_address,food,cuisine,price;

            public holder(@NonNull View itemView) {
                super(itemView);
                food_image=(ImageView) itemView.findViewById(R.id.food_image);
                hotel_name=(TextView) itemView.findViewById(R.id.hotel_name);
                hotel_address=(TextView) itemView.findViewById(R.id.hotel_address);
                food=(TextView) itemView.findViewById(R.id.food);
                cuisine=(TextView) itemView.findViewById(R.id.cusine);
                price=(TextView) itemView.findViewById(R.id.price);


            }
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
