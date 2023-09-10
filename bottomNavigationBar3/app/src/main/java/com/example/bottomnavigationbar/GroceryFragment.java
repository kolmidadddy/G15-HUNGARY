// done by Chin Kah Jun and Loh Jun Xiang and Samantha Lok
package com.example.bottomnavigationbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroceryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroceryFragment extends Fragment {

    private String id,title,category,locations,expirydate,
            quantity,weight,purchasedate,brand,shop,price,note,selectedItem;

    FirebaseDatabase db;
    DatabaseReference references;

    private int currentQuantity = 0;
    private Button shopping_list;
    private Button edit;
    private  Button delete;

    private  Button confirm;

    private EditText title_edittext;

    Spinner spinner;
    Spinner spinner_locations;

    private EditText expiry_date;

    private EditText id_edittext;
    private EditText quantity_edittext;
    private EditText weight_edittext;
    private EditText purchase_date;
    private EditText brand_edittext;
    private EditText shop_edittext;
    private EditText price_edittext;
    private EditText note_edittext;

    private EditText selectedEditText;
    private EditText dateEditText;
    private EditText quantityEditText;

    private EditText PurchaseDateEditText;


    private Calendar calendar;


    final int desiredLength = 10; // Specify the desired length of the random string

    String randomString;

    private boolean alertDialogShown = false;
    private boolean exist=false;

    private ArrayList<ShoppingList> list;








    public GroceryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroceryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroceryFragment newInstance(String param1, String param2) {
        GroceryFragment fragment = new GroceryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_grocery, container, false);

        spinner = view.findViewById(R.id.spinner_category);
        spinner_locations = view.findViewById(R.id.spinner_location);

        // Create an ArrayAdapter using a string array from resources and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.categories, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_locations = ArrayAdapter.createFromResource(
                requireContext(), R.array.locations, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_locations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner_locations.setAdapter(adapter_locations);


        quantityEditText = view.findViewById(R.id.quantity);
        Button incrementButton = view.findViewById(R.id.incrementButton);
        Button decrementButton = view.findViewById(R.id.decrementButton);

        randomString = generateRandomString(desiredLength);

        list=new ArrayList<>();








        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuantity++;
                quantityEditText.setText(String.valueOf(currentQuantity));
            }
        });
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuantity > 0) {
                    currentQuantity--;
                    quantityEditText.setText(String.valueOf(currentQuantity));
                }
            }
        });

        dateEditText = view.findViewById(R.id.dateEditText);
        PurchaseDateEditText=view.findViewById(R.id.PurchaseDate);
        calendar = Calendar.getInstance();

        id_edittext=view.findViewById(R.id.itemID);
        id_edittext.setText(randomString);







        id_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!alertDialogShown) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Edit/Delete");
                    builder.setMessage("Do you want to edit or delete your item?");

                    // Add buttons for "Yes" and "No"
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            id_edittext.setFocusable(true);
                            id_edittext.setFocusableInTouchMode(true);
                            id_edittext.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.NONE, false));
                            alertDialogShown=true;
                            confirm.setEnabled(true);
                            Toast.makeText(requireContext(), "You can now edit or delete your item with the itemID!", Toast.LENGTH_LONG).show();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle the "No" button click
                            // You can perform the desired action here or simply dismiss the dialog
                            dialog.dismiss();
                        }
                    });

                    // Create and show the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();



                }
            }

        });

        // Inside your onClick listener
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Set the selectedEditText to the EditText you want to update
                selectedEditText = dateEditText;

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        dateSetListener, // Use the globally defined listener
                        year,
                        month,
                        day
                );
                datePickerDialog.show();
            }
        });



        PurchaseDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Set the selectedEditText to the EditText you want to update
                selectedEditText = PurchaseDateEditText;

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        dateSetListener, // Use the globally defined listener
                        year,
                        month,
                        day
                );
                datePickerDialog.show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                selectedItem = parentView.getItemAtPosition(position).toString();
                category=selectedItem;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinner_locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                selectedItem = parentView.getItemAtPosition(position).toString();
                locations=selectedItem;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });



        title_edittext=view.findViewById(R.id.title);
        expiry_date=view.findViewById(R.id.dateEditText);

        quantity_edittext=view.findViewById(R.id.quantity);
        weight_edittext=view.findViewById(R.id.weight);
        purchase_date=view.findViewById(R.id.PurchaseDate);
        brand_edittext=view.findViewById(R.id.Brand);
        shop_edittext=view.findViewById(R.id.Shop);
        price_edittext=view.findViewById(R.id.Price);
        note_edittext=view.findViewById(R.id.note);
        shopping_list=view.findViewById(R.id.shopping_list);
        edit=view.findViewById(R.id.done);
        delete=view.findViewById(R.id.delete);
        confirm=view.findViewById(R.id.confirm);











        shopping_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=id_edittext.getText().toString();
                title=title_edittext.getText().toString();
                expirydate=expiry_date.getText().toString();
                quantity=quantity_edittext.getText().toString();
                weight=weight_edittext.getText().toString();
                purchasedate=purchase_date.getText().toString();
                brand=brand_edittext.getText().toString();
                shop=shop_edittext.getText().toString();
                price=price_edittext.getText().toString();
                note=note_edittext.getText().toString();


                if(!title.isEmpty()&&!expirydate.isEmpty()&&!quantity.isEmpty()&&!weight.isEmpty()&&
                        !purchasedate.isEmpty()&&!brand.isEmpty()&&!shop.isEmpty()&&!price.isEmpty()&&!locations.isEmpty()&&
                        !category.isEmpty()){
                    ShoppingList shopping_item=new ShoppingList(id,title,category,locations,expirydate,quantity,weight,purchasedate,brand,shop,price,note);
                    db=FirebaseDatabase.getInstance();
                    references=db.getReference("Shopping Item");
                    references.child(id).setValue(shopping_item).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            title_edittext.setText("");
                            expiry_date.setText("");
                            quantity_edittext.setText("");
                            weight_edittext.setText("");
                            purchase_date.setText("");
                            brand_edittext.setText("");
                            shop_edittext.setText("");
                            price_edittext.setText("");
                            note_edittext.setText("");

                            Toast.makeText(requireContext(),"Successfully Updated!",Toast.LENGTH_SHORT).show();



                        }

                    });


                }











            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=id_edittext.getText().toString();
                deleteRecordById(id);
                Toast.makeText(requireContext(),"Record deleted successfully!",Toast.LENGTH_SHORT).show();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=id_edittext.getText().toString();
                title=title_edittext.getText().toString();
                expirydate=expiry_date.getText().toString();
                quantity=quantity_edittext.getText().toString();
                weight=weight_edittext.getText().toString();
                purchasedate=purchase_date.getText().toString();
                brand=brand_edittext.getText().toString();
                shop=shop_edittext.getText().toString();
                price=price_edittext.getText().toString();
                note=note_edittext.getText().toString();

                updateItemById(id,title,category,locations,expirydate,quantity,weight,purchasedate,brand,shop,price,note);




            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=id_edittext.getText().toString();
                CheckIdExist(id);




            }
        });





        // Inflate the layout for this fragment
        return view;
    }

    private void updateItemById(String id, String title, String category, String locations, String expirydate , String quantity, String weight, String purchasedate, String brand, String shop, String price, String note) {
        HashMap item=new HashMap();
        item.put("id",id);
        item.put("title",title);
        item.put("cateogry",category);
        item.put("locations",locations);
        item.put("expirydate",expirydate);
        item.put("quantity",quantity);
        item.put("purchasedate",purchasedate);
        item.put("brand",brand);
        item.put("shop",shop);
        item.put("price",price);
        item.put("note",note);

        references=FirebaseDatabase.getInstance().getReference("Shopping Item");
        references.child(id).updateChildren(item).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    title_edittext.setText("");
                    expiry_date.setText("");
                    quantity_edittext.setText("");
                    weight_edittext.setText("");
                    purchase_date.setText("");
                    brand_edittext.setText("");
                    shop_edittext.setText("");
                    price_edittext.setText("");
                    note_edittext.setText("");
                    Toast.makeText(requireContext(),"Successfully Updated!",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(requireContext(),"Failed to Update!",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (selectedEditText != null) {
                // Here, you can handle the selected date and update the selectedEditText
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                selectedEditText.setText(selectedDate);
            }
        }
    };



    public  String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public  void deleteRecordById(String id) {
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

    public void CheckIdExist(String id){


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Replace "your_node" with the specific node where your data is stored
        String nodePath = "Shopping Item";

        // Get a reference to the node containing your data
        DatabaseReference nodeRef = database.getReference(nodePath);

        // Get a reference to the specific record using its key
        DatabaseReference recordRef = nodeRef.child(id);

        // Add a ValueEventListener to retrieve the data
        recordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if the record exists
                if (dataSnapshot.exists()) {
                    // Retrieve the 'id' field from the data
                    String id_firebase = dataSnapshot.child("id").getValue(String.class);
                    String category_firebase = dataSnapshot.child("cateogry").getValue(String.class);
                    String locations_firebase = dataSnapshot.child("locations").getValue(String.class);
                    if(id_firebase.equals(id)){
                        ShoppingList shopping_item=dataSnapshot.getValue(ShoppingList.class);
                        list.add(shopping_item);
                        id_edittext.setKeyListener(null);
                        alertDialogShown=false;
                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        // Replace "your_node" with the specific node where your data is stored
                        String nodePath = "Shopping Item";

                        // Get a reference to the node containing your data
                        DatabaseReference nodeRef = database.getReference(nodePath);

                        // Get a reference to the specific record using its key
                        DatabaseReference recordRef = nodeRef.child(id);

                        // Add a ValueEventListener to retrieve the data
                        recordRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Check if the record exists
                                if (dataSnapshot.exists()) {
                                    // Retrieve the 'id' field from the data
                                    String id_firebase = dataSnapshot.child("id").getValue(String.class);
                                    String category_firebase = dataSnapshot.child("cateogry").getValue(String.class);
                                    String locations_firebase = dataSnapshot.child("locations").getValue(String.class);
                                    if (id_firebase.equals(id)) {
                                        ShoppingList shopping_item = dataSnapshot.getValue(ShoppingList.class);
                                        list.add(shopping_item);
                                        title_edittext.setText(list.get(0).getTitle());
                                        expiry_date.setText(list.get(0).getExpirydate());
                                        quantity_edittext.setText(list.get(0).getQuantity());
                                        weight_edittext.setText(list.get(0).getWeight());
                                        purchase_date.setText(list.get(0).getPurchasedate());
                                        brand_edittext.setText(list.get(0).getBrand());
                                        shop_edittext.setText(list.get(0).getShop());
                                        price_edittext.setText(list.get(0).getPrice());
                                        note_edittext.setText(list.get(0).getNote());

                                        String[] category_array = new String[]{"Vegetable", "Meat/Fish", "Dairy Products", "Fruits", "Seasoning", "Other"};
                                        String[] location_array = new String[]{"Fridge", "Kitchen", "Freezer", "Shelf", "Drawer"};


                                        int category_position = 0, locations_position = 0;
                                        for (int i = 0; i < category_array.length; i++) {
                                            if (category_firebase.equals(category_array[i])) {
                                                category_position = i;
                                            }
                                        }
                                        for (int i = 0; i < location_array.length; i++) {
                                            if (locations_firebase.equals(location_array[i])) {
                                                locations_position = i;
                                            }
                                        }
                                        spinner.setSelection(category_position);
                                        spinner_locations.setSelection(locations_position);

                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {


                            }
                        });
                        Toast.makeText(requireContext(),list.get(0).getTitle(),Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(requireContext(),"The Item ID doesn't exist!",Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }






}