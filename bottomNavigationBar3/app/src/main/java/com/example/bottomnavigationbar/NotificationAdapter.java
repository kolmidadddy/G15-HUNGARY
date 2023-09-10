// done by Samantha Lok
package com.example.bottomnavigationbar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {





    Context context;

    ArrayList<ShoppingList> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener{

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener ){
        listener=clickListener;
    }




    public NotificationAdapter(Context context, ArrayList<ShoppingList> list) {
        this.context = context;
        this.list = list;

    }

    public void setFilteredList(ArrayList<ShoppingList> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);

        return new MyViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShoppingList shopping_item=list.get(position);
        int expired=calculateRemainingDays(shopping_item.getExpirydate());
        System.out.println(expired);
        System.out.println(shopping_item.getTitle());
        if(expired>0){
            holder.notification.setText("Your "+shopping_item.getTitle()+" is about to expire in "+String.valueOf(expired)+" day(s)");
        }
        else{
            holder.notification.setText("Your "+shopping_item.getTitle()+" has been expired!");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView notification;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);

            notification=itemView.findViewById(R.id.notification);
            imageView=itemView.findViewById(R.id.delete);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());

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
