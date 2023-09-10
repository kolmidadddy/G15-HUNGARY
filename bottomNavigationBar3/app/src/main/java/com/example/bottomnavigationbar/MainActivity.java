// done by Loh Jun Xiang and Samantha Lok
package com.example.bottomnavigationbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigationbar.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DatabaseReference db;

    ArrayList<ShoppingList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db= FirebaseDatabase.getInstance().getReference("Shopping Item");
        list =new ArrayList<>();
        replaceFragment(new CalendarFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.calendar) {
                replaceFragment(new CalendarFragment());

            } else if (itemId == R.id.category) {
                replaceFragment(new CategoryFragment());

            }else if (itemId == R.id.grocery) {
                replaceFragment(new GroceryFragment());

            }else if (itemId == R.id.settings) {
                replaceFragment(new SettingsFragment());

            }

            return true;
        });

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String expirydate = dataSnapshot.child("expirydate").getValue(String.class);
                    if(calculateRemainingDays(expirydate)<=3){
                        ShoppingList shopping_item=dataSnapshot.getValue(ShoppingList.class);
                        list.add(shopping_item);

                        NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this,"My Notification!");
                        builder.setContentTitle("Reminder");
                        builder.setContentText("Your "+shopping_item.getTitle()+" is about to expired!");
                        builder.setSmallIcon(R.drawable.eatsy);
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(MainActivity.this);
                        managerCompat.notify(1,builder.build());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
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