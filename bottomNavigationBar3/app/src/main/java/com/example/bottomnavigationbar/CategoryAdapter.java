//All done by Loh Jun Xiang
package com.example.bottomnavigationbar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;



    Context context;

    ArrayList<ShoppingList> list;


    public CategoryAdapter(Context context, ArrayList<ShoppingList> list,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    public void setFilteredList(ArrayList<ShoppingList> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShoppingList shopping_item=list.get(position);
        holder.id.setText(shopping_item.getId());
        holder.food.setText(shopping_item.getTitle());
        holder.locations.setText(shopping_item.getLocations());
        int expired=calculateRemainingDays(shopping_item.getExpirydate());
        holder.expireddate.setText(String.valueOf(expired)+" day(s)");

        if(expired<=0){
            holder.expireddate.setTextColor(Color.RED);
        }
        else if(expired<=2){
            holder.expireddate.setTextColor(Color.parseColor("#FFA500"));

        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView food,locations,expireddate,id;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            food=itemView.findViewById(R.id.food);
            locations=itemView.findViewById(R.id.location);
            expireddate=itemView.findViewById(R.id.expired);
            id=itemView.findViewById(R.id.id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos=getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }
                }
            });



        }
    }

    public static int calculateRemainingDays(String expiredDateString) {
        try {
            // Define the date format for parsing
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Get the current date
            Date currentDate = new Date();

            // Parse the expired date string into a Date object
            Date expiredDate = dateFormat.parse(expiredDateString);

            // Calculate the difference in milliseconds
            long timeDiff = expiredDate.getTime() - currentDate.getTime();

            // Calculate the difference in days
            int daysRemaining = (int) (timeDiff / (1000 * 60 * 60 * 24));

            return daysRemaining;

        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error in parsing the date
        }
    }
}
