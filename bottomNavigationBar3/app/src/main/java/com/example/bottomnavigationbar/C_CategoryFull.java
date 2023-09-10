//All done by Loh Jun Xiang
package com.example.bottomnavigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class C_CategoryFull extends AppCompatActivity {

    TextView food,category,locations,expireddate,reminder,quantity,weight,purchasedate,shop,price,note;
    String food_text,category_text,locations_text,expireddate_text,reminder_text,quantity_text,weight_text
            ,purchasedate_text,shop_text,price_text,note_text;

    FirebaseDatabase db;

    DatabaseReference references;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.item1);

        Intent intent = getIntent();
        String textValue = intent.getStringExtra("food");

        food=findViewById(R.id.food_name);
        category=findViewById(R.id.category);
        locations=findViewById(R.id.location);
        expireddate=findViewById(R.id.expired);
        reminder=findViewById(R.id.reminder_full);
        quantity=findViewById(R.id.quantity_full);
        weight=findViewById(R.id.weight_full);
        purchasedate=findViewById(R.id.purchasedate_full);
        shop=findViewById(R.id.shop_full);
        price=findViewById(R.id.price_full);
        note=findViewById(R.id.note_full);



        db= FirebaseDatabase.getInstance();

        references = db.getReference("Shopping Item");
        references.orderByChild("title").equalTo(textValue).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop through the matching data and retrieve the information you need
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     food_text = snapshot.child("title").getValue(String.class);
                     category_text = snapshot.child("cateogry").getValue(String.class);
                     locations_text = snapshot.child("locations").getValue(String.class);
                     expireddate_text = snapshot.child("expirydate").getValue(String.class);
                     reminder_text = snapshot.child("reminder").getValue(String.class);
                     quantity_text = snapshot.child("quantity").getValue(String.class);
                     weight_text = snapshot.child("weight").getValue(String.class);
                     purchasedate_text = snapshot.child("purchasedate").getValue(String.class);
                     shop_text = snapshot.child("shop").getValue(String.class);
                     price_text = snapshot.child("price").getValue(String.class);
                     note_text = snapshot.child("note").getValue(String.class);// Use the retrieved data as needed
                }
                food.setText(food_text);
                category.setText(category_text);
                locations.setText(locations_text);
                food.setText(food_text);
                expireddate.setText(expireddate_text);
                reminder.setText(reminder_text);
                quantity.setText(quantity_text);
                weight.setText(weight_text+"g");
                purchasedate.setText(purchasedate_text);
                shop.setText(shop_text);
                price.setText("RM"+price_text);
                note.setText(note_text);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });







    }

}