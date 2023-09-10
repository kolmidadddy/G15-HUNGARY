// done by Yap Xiang Ying and Loh Jun Xiang and Samantha Lok
package com.example.bottomnavigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactUsActivity extends AppCompatActivity {

    FirebaseDatabase db;

    DatabaseReference references;

    EditText etEmail, etComment;
    Button btnSubmit;

    ArrayList<Contact> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        etEmail = findViewById(R.id.editTextEmail);
        etComment = findViewById(R.id.editTextComment);
        btnSubmit = findViewById(R.id.buttonSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etEmail.getText().toString();
                String comment=etComment.getText().toString();
                email=encodeUserEmail(email);

                if(!email.isEmpty()&&!comment.isEmpty()) {
                    Contact contact = new Contact(email, comment);
                    db = FirebaseDatabase.getInstance();
                    references = db.getReference("Contact Us");
                    references.child(email).setValue(contact).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            etEmail.setText("");
                            etComment.setText("");
                            Toast.makeText(ContactUsActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();

                        }


                    });

                }
            }
        });
    }
    public static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    public static String decodeUserEmail(String userKey) {
        return userKey.replace(",", ".");
    }
}