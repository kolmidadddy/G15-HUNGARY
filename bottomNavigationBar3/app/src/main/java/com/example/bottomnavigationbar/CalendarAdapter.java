package com.example.bottomnavigationbar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {


    Context context;

    ArrayList<Meal> list;
    ArrayList<ShoppingList> food_list;

    DatabaseReference grocery_food;


    public CalendarAdapter(Context context, ArrayList<Meal> list) {
        this.context = context;
        this.list = list;


    }

    public void setFilteredList(ArrayList<Meal> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.agenda_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meal meal=list.get(position);
        holder.date.setText(meal.getDescription());
        holder.description.setText(meal.getDescription());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView description,date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.dateTextView);
            description=itemView.findViewById(R.id.mealDescriptionTextView);




        }
    }
}
