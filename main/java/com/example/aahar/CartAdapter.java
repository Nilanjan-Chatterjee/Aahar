package com.example.aahar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.holder>{
    private List<Food> foodList;
    public CartAdapter(List<Food> foodList) {
        this.foodList=foodList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow3, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final Food food= foodList.get(position);
        holder.food.setText(food.getTitle());
        holder.cuisine.setText(food.getCuisine());
        holder.price.setText(food.getPrice());
        Integer  quant=food.getQuantity();
        holder.quantity.setText(Integer.toString(quant));
        holder.restaurant_name.setText(food.getName());
        String address=food.getLocation()+','+food.getCity();
        holder.restaurant_adress.setText(address);
        String foodimage=food.getImage();
        String url="http://192.168.43.72.:8080/aahar/images/"+foodimage;
        LoadImage loadimage = new LoadImage(holder.food_image);
        loadimage.execute(url);


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

    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public class holder extends RecyclerView.ViewHolder {
        ImageView food_image;
        TextView food,cuisine,price,quantity,restaurant_name,restaurant_adress;
        public holder(@NonNull View itemView) {
            super(itemView);
            food_image=(ImageView)itemView.findViewById(R.id.food_image);
            food=(TextView) itemView.findViewById(R.id.food);
            cuisine=(TextView) itemView.findViewById(R.id.cuisine);
            price=(TextView) itemView.findViewById(R.id.price);
            quantity=(TextView) itemView.findViewById(R.id.quantity);
            restaurant_name=(TextView) itemView.findViewById(R.id.Restaurant_name);
            restaurant_adress=(TextView) itemView.findViewById(R.id.Restaurant_place);


        }
    }
}
