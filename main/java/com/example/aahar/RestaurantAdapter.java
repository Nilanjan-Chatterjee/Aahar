package com.example.aahar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.util.List;
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.holder> {
    private List<Food> foodList;
    private Context ctx;
    public RestaurantAdapter(Context ctx, List<Food> foodList){
        this.ctx=ctx;
        this.foodList=foodList;
    }
    @NonNull
    @Override
    public RestaurantAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow2, parent, false);
        return new holder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.holder holder, int position) {
        final Food food =foodList.get(position);
        holder.food.setText(food.getTitle());
        holder.cuisine.setText(food.getCuisine());
        holder.price.setText(food.getPrice());
        final String foodimage=food.getImage();
        String url="http://192.168.43.72.:8080/aahar/images/"+foodimage;
        LoadImage loadimage = new LoadImage(holder.food_image);
        loadimage.execute(url);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantActivity.food_id=food.getFood_id();
                RestaurantActivity.price=food.getPrice();
                RestaurantActivity.discount=food.getDiscount();
                RestaurantActivity.image=food.getImage();
                RestaurantActivity.cuisine=food.getCuisine();
                RestaurantActivity.quantity.setText("1");
                RestaurantActivity.food.setText(food.getTitle());
                RestaurantActivity.food_cuisine.setText(food.getCuisine());
                RestaurantActivity.food.setText(food.getTitle());
                RestaurantActivity.food_price.setText(food.getPrice());

                String url="http://192.168.43.72.:8080/aahar/images/"+food.getImage();
                LoadImage loadimage = new LoadImage(RestaurantActivity.food_image);
                loadimage.execute(url);
              }
        });
    }
    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public class holder extends RecyclerView.ViewHolder {
        ImageView food_image;
        TextView food, cuisine, price;
        public holder(@NonNull View itemView) {
            super(itemView);
            food_image = (ImageView) itemView.findViewById(R.id.image);
            food = (TextView) itemView.findViewById(R.id.name);
            cuisine = (TextView) itemView.findViewById(R.id.cuisine);
            price = (TextView) itemView.findViewById(R.id.price);
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
