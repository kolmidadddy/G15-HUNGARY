//All done by Loh Jun Xiang
package com.example.bottomnavigationbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryFullAdapter extends RecyclerView.Adapter<CategoryFullAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;

    ArrayList<ShoppingList> list;

    public CategoryFullAdapter(Context context, ArrayList<ShoppingList> list,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface=recyclerViewInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item1,parent,false);
        return new MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShoppingList shopping_item=list.get(position);
        holder.food.setText(shopping_item.getTitle());
        holder.category.setText(shopping_item.getCateogry());
        holder.locations.setText(shopping_item.getLocations());
        holder.expireddate.setText(shopping_item.getExpirydate());
        holder.quantity.setText(shopping_item.getQuantity());
        holder.weight.setText(shopping_item.getWeight());
        holder.expireddate.setText(shopping_item.getExpirydate());
        holder.purchasedate.setText(shopping_item.getPurchasedate());
        holder.shop.setText(shopping_item.getShop());
        holder.price.setText(shopping_item.getPrice());
        holder.note.setText(shopping_item.getNote());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView food,category,locations,expireddate,reminder,quantity,weight,purchasedate,shop,price,note;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            food=itemView.findViewById(R.id.food_name);
            category=itemView.findViewById(R.id.category);
            locations=itemView.findViewById(R.id.location);
            expireddate=itemView.findViewById(R.id.expired);
            reminder=itemView.findViewById(R.id.reminder_full);
            quantity=itemView.findViewById(R.id.quantity_full);
            weight=itemView.findViewById(R.id.weight_full);
            purchasedate=itemView.findViewById(R.id.purchasedate_full);
            shop=itemView.findViewById(R.id.shop_full);
            price=itemView.findViewById(R.id.price_full);
            note=itemView.findViewById(R.id.note_full);

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
}
