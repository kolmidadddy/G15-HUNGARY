//All done by Loh Jun Xiang
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

public class C_DairyProductActivity extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    DatabaseReference db;
    CategoryAdapter myAdapter;
     ArrayList<ShoppingList> list;

     private SearchView searchView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView=findViewById(R.id.cateogryList);
        db= FirebaseDatabase.getInstance().getReference("Shopping Item");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView=findViewById(R.id.searchView);
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



         list =new ArrayList<>();
        myAdapter= new CategoryAdapter(this,list,this);
        recyclerView.setAdapter(myAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String category = dataSnapshot.child("cateogry").getValue(String.class);
                    String expirydate = dataSnapshot.child("expirydate").getValue(String.class);
                    if(category.equals("Dairy Products")){
                        if(calculateRemainingDays(expirydate)>=1){
                            ShoppingList shopping_item=dataSnapshot.getValue(ShoppingList.class);
                            list.add(shopping_item);
                        }
                    }
                }
                if(list.isEmpty()){
                    Toast.makeText(C_DairyProductActivity.this,"Dairy Product Category is empty!",Toast.LENGTH_LONG).show();
                }
                else{
                    TextView category_name=findViewById(R.id.category_name);
                    category_name.setText("Dairy Product");
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
        ArrayList<ShoppingList> list_item=new ArrayList<>();
        for(ShoppingList item:list){
            if(item.getTitle().contains(newText)){
                list_item.add(item);
            }
        }
        if(list_item.isEmpty()){
            Toast.makeText(this,"Not Found!",Toast.LENGTH_SHORT).show();
        }
        myAdapter.setFilteredList(list_item);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(C_DairyProductActivity.this, C_CategoryFull.class);
        intent.putExtra("food",list.get(position).getTitle());
        startActivity(intent);


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