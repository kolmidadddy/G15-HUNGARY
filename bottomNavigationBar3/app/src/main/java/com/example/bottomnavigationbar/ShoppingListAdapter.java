// done by Loh Jun Xiang and Samantha Lok
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

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {


    Context context;

    ArrayList<String> list;
    ArrayList<ShoppingList> food_list;

    DatabaseReference grocery_food;


    public ShoppingListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;


    }

    public void setFilteredList(ArrayList<String> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.shopping_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String meal=list.get(position);
        holder.ingredient.setText(meal);
        grocery_food= FirebaseDatabase.getInstance().getReference("Shopping Item");
        food_list=new ArrayList<>();
        grocery_food.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ShoppingList item= dataSnapshot.getValue(ShoppingList.class);
                    food_list.add(item);
                }

                int index=0;
                for(int i=0;i<food_list.size();i++){
                    if(meal.equals(food_list.get(i).getTitle())){
                        System.out.println(meal+" == "+food_list.get(i).getTitle());
                        break;
                    }
                    else{
                        index++;
                        if(index==food_list.size()-1){
                            holder.ingredient.setTextColor(Color.RED);
                            index=0;
                        }
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ingredient;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient=itemView.findViewById(R.id.ingredient);




        }
    }
}
