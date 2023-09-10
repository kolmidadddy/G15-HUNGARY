//All done by Samantha Lok Tian Wen
package com.example.bottomnavigationbar;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences sharedPreferences;
    private Meal meal;

    String description,ingredients;

    FirebaseDatabase db;
    DatabaseReference references;

    FirebaseDatabase db1;
    DatabaseReference references1;
    private EditText description_edittext;
    private EditText ingredient_edittext;

    private RecyclerView agendaRecyclerView;
    private CalendarAdapter agendaAdapter;

private EditText mealInput;

private EditText ingredientsInput;

    ArrayList<String> ingredientsList;

    ArrayList<AgendaItem> agendaItemsList;

    ArrayList<Meal> list;

    ArrayList<String> item;


    String eventDate;


    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        item=new ArrayList<String>();

        View view=inflater.inflate(R.layout.activity_calendar, container, false);
        sharedPreferences = getActivity().getSharedPreferences("events", MODE_PRIVATE);

        // Initialize the RecyclerView
        RecyclerView agendaRecyclerView = view.findViewById(R.id.agendaRecyclerView);
        agendaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        agendaAdapter = new CalendarAdapter(getContext(),agendaItemsList);
//        agendaRecyclerView.setAdapter(agendaAdapter);

        // Get current time in GMT+8 timezone
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        long currentTime = calendar.getTimeInMillis();

        // Set CalendarView's date to the current time
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setDate(currentTime, true, true);

//        db1=FirebaseDatabase.getInstance();
//        references1= db1.getReference("Food plan");
//
//        references1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    String expirydate = dataSnapshot.child("expirydate").getValue(String.class);
//
//                        Meal meal=dataSnapshot.getValue(Meal.class);
//                        list.add(meal);
//
//
//                }
//                agendaAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("FirebaseError", "Data loading cancelled/error: " + error.getMessage());
//                Toast.makeText(requireContext(), "Failed to load data. Please check your connection.", Toast.LENGTH_LONG).show();
//            }
//        });



        calendarView.setOnDateChangeListener((v, year, month, dayOfMonth) -> {
            eventDate = dayOfMonth + "/" + (month+1) + "/" + year;
            String mealDescription = sharedPreferences.getString(eventDate + "_meal", null);
            String ingredientsDescription = sharedPreferences.getString(eventDate + "_ingredients", null);

            if (mealDescription != null || ingredientsDescription != null) {
                updateOrDeleteEvent(year, month, dayOfMonth, mealDescription, ingredientsDescription);
            } else {
                showEventDialog(year, month, dayOfMonth);
            }
        });
        System.out.println(eventDate);


        return view;
    }
    private void saveEvent(int year, int month, int dayOfMonth, String mealDescription, String ingredientsDescription) {
        if(mealDescription.isEmpty() && ingredientsDescription.isEmpty()) {
            return;
        }

        // Splitting the ingredients based on commas
        String[] ingredientsArray = ingredientsDescription.split("\\s*,\\s*");

        ingredientsList = new ArrayList<>(Arrays.asList(ingredientsArray));

        Meal meal1 = new Meal(mealDescription,eventDate, ingredientsList);

        db = FirebaseDatabase.getInstance();
        references = db.getReference("Food plan");

        references.child(mealDescription).setValue(meal1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();
            }
        });

        //Retrieve food name from firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shopping Item");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String food=dataSnapshot.child("title").getValue(String.class);
                    item.add(food);
                }

                int groceryLength=item.size();
                for(int i=1;i<groceryLength;i++){
                    for(int j=0;j<i;j++){
                        if(item.get(i).equals(item.get(j))){
                            item.remove(i);
                            for(int k=i;k<groceryLength;k++){
                                if(k!=(groceryLength-1)){
                                    item.set(k,item.get(k+1));
                                }



                            }

                        }

                    }
                }

                System.out.println(ingredientsList.get(0));
                System.out.println(item.get(0));
                for(int i=0;i<ingredientsList.size();i++){
                    for(int j=0;j<item.size();j++){
                        if(item.get(j).equals(ingredientsList.get(i))){
                            Toast.makeText(requireContext(),ingredientsList.get(i)+" is in your grocery list!",Toast.LENGTH_SHORT).show();
                            System.out.println(item.get(j)+"="+ingredientsList.get(i));
                            break;
                        }
                        else{
                            if(j==groceryLength-1){
                                Toast.makeText(requireContext(),ingredientsList.get(i)+" is not in your grocery list!",Toast.LENGTH_SHORT).show();
                                System.out.println(item.get(j)+"!="+ingredientsList.get(i));
                            }
                        }
                    }


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    private void deleteEvent(int year, int month, int dayOfMonth) {
        String eventDate = dayOfMonth + "/" + (month+1) + "/" + year;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(eventDate + "_meal");
        editor.remove(eventDate + "_ingredients");
        editor.apply();  // Single apply() after all modifications
    }


    private void showEventDialog(int year, int month, int dayOfMonth) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Meal and Ingredients");

        final EditText mealInput = new EditText(getActivity());
        mealInput.setHint("Meal description");
        final EditText ingredientsInput = new EditText(getActivity());
        ingredientsInput.setHint("Ingredients for next meal");

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(mealInput);
        layout.addView(ingredientsInput);
        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String mealDescription = mealInput.getText().toString();
            String ingredientsDescription = ingredientsInput.getText().toString();
            saveEvent(year, month, dayOfMonth, mealDescription, ingredientsDescription);
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateOrDeleteEvent(int year, int month, int dayOfMonth, String currentMealDescription, String currentIngredientsDescription) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Update/Delete Meal and Ingredients");

        mealInput = new EditText(getActivity());
        mealInput.setText(currentMealDescription);
        ingredientsInput = new EditText(getActivity());
        ingredientsInput.setText(currentIngredientsDescription);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(mealInput);
        layout.addView(ingredientsInput);
        builder.setView(layout);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String mealDescription = mealInput.getText().toString();
            String ingredientsDescription = ingredientsInput.getText().toString();
            saveEvent(year, month, dayOfMonth, mealDescription, ingredientsDescription);
        });

        builder.setNegativeButton("Delete", (dialog, which) -> deleteEvent(year, month, dayOfMonth));

        builder.setNeutralButton("Cancel", null);

        builder.show();
    }


}