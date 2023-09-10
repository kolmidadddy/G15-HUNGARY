// done by Samantha Lok
package com.example.bottomnavigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    NotificationAdapter myAdapter;
    ArrayList<ShoppingList> list;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView=findViewById(R.id.notification);
        db= FirebaseDatabase.getInstance().getReference("Shopping Item");

        searchView=findViewById(R.id.searchView_notification);
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

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        list =new ArrayList<>();
        myAdapter= new NotificationAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                // Replace "your_node" with the specific node where your data is stored
                String nodePath = "Shopping Item";

                // Get a reference to the node containing your data
                DatabaseReference nodeRef = database.getReference(nodePath);

                // Get a reference to the specific record using its ID
                if(calculateRemainingDays(list.get(position).getExpirydate())<0){
                    DatabaseReference recordRef = nodeRef.child(list.get(position).getId());
                    // Delete the record
                    recordRef.removeValue();
                }

                list.remove(position);
                myAdapter.notifyItemRemoved(position);
                if(list.isEmpty()){
                    Toast.makeText(NotificationActivity.this,"No notification!",Toast.LENGTH_LONG).show();
                }


            }
        });

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String expirydate = dataSnapshot.child("expirydate").getValue(String.class);
                        if(calculateRemainingDays(expirydate)<=3){
                            ShoppingList shopping_item=dataSnapshot.getValue(ShoppingList.class);
                            list.add(shopping_item);
                        }

                }
                if(list.isEmpty()){
                    Toast.makeText(NotificationActivity.this,"No notification!",Toast.LENGTH_LONG).show();
                }
                else{
                    TextView category_name=findViewById(R.id.notification_title);
                    category_name.setText("Notification");
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

    public  void deleteRecordByFoodName(String id) {
        // Get a reference to your Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Replace "your_node" with the specific node where your data is stored
        String nodePath = "Shopping Item";

        // Get a reference to the node containing your data
        DatabaseReference nodeRef = database.getReference(nodePath);

        // Get a reference to the specific record using its ID
        DatabaseReference recordRef = nodeRef.child(id);

        // Delete the record
        recordRef.removeValue();
    }
}