// done by Loh Jun Xiang
package com.example.bottomnavigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Full_Shopping_List extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    DatabaseReference grocery_food;
    ShoppingListAdapter myAdapter;
    ArrayList<Meal> list;
    ArrayList<ShoppingList> food_list;
    ArrayList<String> ingredients;

    private SearchView searchView;

    ArrayList<String> uniqueList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_shopping_list);

        recyclerView=findViewById(R.id.shoppinglist);
        db=FirebaseDatabase.getInstance().getReference("Food plan");
        grocery_food=FirebaseDatabase.getInstance().getReference("Shopping Item");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        ingredients=new ArrayList<>();
        food_list=new ArrayList<>();
        uniqueList=new ArrayList<>();

        searchView=findViewById(R.id.searchView_shoppinglist);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);

                return true;
            }
        });

        myAdapter= new ShoppingListAdapter(this,uniqueList);
        recyclerView.setAdapter(myAdapter);



        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int index=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Meal meal= dataSnapshot.getValue(Meal.class);
                    list.add(meal);
                    index++;
                }
                for(int i=0;i<list.size();i++){
                    for(int j=0;j<list.get(i).getIngredient().size();j++){
                        ingredients.add(list.get(i).getIngredient().get(j));
                    }

                    for (String item : ingredients) {
                        if (!uniqueList.contains(item)) {
                            uniqueList.add(item);
                        }
                    }









                }
                if(list.isEmpty()){
                    Toast.makeText(Full_Shopping_List.this,"Shopping List is empty!",Toast.LENGTH_LONG).show();
                }
                else{
                    TextView category_name=findViewById(R.id.category_name);
                    category_name.setText("Shopping List");
                    category_name.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void filterList(String newText) {
        ArrayList<String> list_item=new ArrayList<>();
        for(String item:ingredients){
            if(item.contains(newText)){
                list_item.add(item);
            }
        }
        if(list_item.isEmpty()){
            Toast.makeText(this,"Not Found!",Toast.LENGTH_SHORT).show();
        }
        myAdapter.setFilteredList(list_item);
    }

    public static ArrayList<String> removeDuplicates(ArrayList<String> list) {
        // Create a new ArrayList to store unique items
        ArrayList<String> uniqueList = new ArrayList<>();

        for (String item : list) {
            if (!uniqueList.contains(item)) {
                // If the item is not already in the unique list, add it
                uniqueList.add(item);
            }
        }

        return uniqueList;
    }
}


