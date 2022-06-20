package com.example.carpoolbuddyappmain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.carpoolbuddyappmain.Models.Car;
import com.example.carpoolbuddyappmain.Models.ElectricCar;
import com.example.carpoolbuddyappmain.Models.MiniBus;
import com.example.carpoolbuddyappmain.Models.Motorbike;
import com.example.carpoolbuddyappmain.Models.Vehicle;
import com.example.carpoolbuddyappmain.Utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateVehicleActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText location;
    private EditText model;
    private EditText capacity;
    private EditText price;
    private Switch open;
    private EditText type;
    private LinearLayout layout;
    private Spinner userRoleSpinner;
    private String selectedRole;
    //electric cars
    private EditText battSize;
    //Car
    private EditText range;
    //Mini Bus
    private EditText numOfRoomsAd;
    //Motorbike
    private EditText maxSpeed;
    ArrayList<String> ownedVehicles;
    //image
    private EditText imageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);
        firestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        layout = findViewById(R.id.linearLayoutVehicle);
        userRoleSpinner = findViewById(R.id.spinnerVehicle);
        setupSpinner();
    }

    // setup spinner where user selects what user type they want to make an account for
    private void setupSpinner() {
        String[] vehicleTypes = {Constants.ELECTRIC_CAR, Constants.CAR, Constants.MINI_BUS, Constants.MOTOR_BIKE};
        // add user types to spinner
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(CreateVehicleActivity.this,
                android.R.layout.simple_spinner_item, vehicleTypes);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userRoleSpinner.setAdapter(langArrAdapter);

        //triggered whenever user selects something different
        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = parent.getItemAtPosition(position).toString();
                addFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // adding the different/ specialised fields of each vehicle
    public void addFields() {
        commonFields();
        if (selectedRole.equals(Constants.ELECTRIC_CAR)) {
            battSize = new EditText(this);
            battSize.setHint("Battery Size");
            layout.addView(battSize);
        }
        if (selectedRole.equals(Constants.CAR)) {
            range = new EditText(this);
            range.setHint("Car Range");
            layout.addView(range);
        }
        if (selectedRole.equals(Constants.MINI_BUS)) {
            numOfRoomsAd = new EditText(this);
            numOfRoomsAd.setHint("Number of Rooms");
            layout.addView(numOfRoomsAd);
        }
        if (selectedRole.equals(Constants.MOTOR_BIKE )) {
            maxSpeed = new EditText(this);
            maxSpeed.setHint("Max Speed");
            layout.addView(maxSpeed);
        }
    }


    public void commonFields() {

        layout.removeAllViewsInLayout();
        location = new EditText(this);
        location.setHint("Location");
        layout.addView(location);
        model = new EditText(this);
        model.setHint("Model");
        layout.addView(model);
        capacity = new EditText(this);
        capacity.setHint("Capacity");
        layout.addView(capacity);
        price = new EditText(this);
        price.setHint("Price");
        layout.addView(price);
        open = new Switch(this);
        open.setHint("Check if Open");
        layout.addView(open);
        type = new EditText(this);
        type.setHint("Write the Type:");
        layout.addView(type);

        imageLink = new EditText(this);
        imageLink.setHint("Image Link: ");
        layout.addView(imageLink);

    }


    public void createVehicleB(View v) {

        //generate + get new key
        DocumentReference newSignUpKey = firestore.collection(Constants.VEHICLE_COLLECTION).document();
        String vehicleKey = newSignUpKey.getId();

        //user ID
        String userID = mUser.getUid();

        //make new user according to selected usertype
        Vehicle newVehicle = null;

        String locationPlace = location.getText().toString();
        String modelName = model.getText().toString();
        int spaces = Integer.parseInt(capacity.getText().toString());
        int theCost = Integer.parseInt(price.getText().toString());
        String typeV = type.getText().toString();

        String imagesLink = imageLink.getText().toString();

        Boolean checked = open.isChecked();


        if(selectedRole.equals(Constants.ELECTRIC_CAR)) {
            int batterySizer = Integer.parseInt(battSize.getText().toString());
            newVehicle = new ElectricCar(locationPlace, modelName, spaces, theCost, checked, typeV, vehicleKey, batterySizer, userID, imagesLink);

             // String id = mAuth.getCurrentUser().getUid();
             //  ownedVehicles.add(id);
        }
        else if(selectedRole.equals(Constants.CAR)) {
            int rangeCar= Integer.parseInt(range.getText().toString());
            newVehicle = new Car(locationPlace, modelName, spaces, theCost, checked, typeV, vehicleKey, rangeCar, userID, imagesLink);

        }
        else if(selectedRole.equals(Constants.MINI_BUS)) {
            int nrOfRooms = Integer.parseInt(numOfRoomsAd.getText().toString());
            newVehicle = new MiniBus(locationPlace, modelName, spaces, theCost, checked, typeV, vehicleKey, nrOfRooms, userID, imagesLink);

        }
        else if(selectedRole.equals(Constants.MOTOR_BIKE)) {
            int maxiSpeed = Integer.parseInt(maxSpeed.getText().toString());
            newVehicle = new Motorbike(locationPlace, modelName, spaces, theCost, checked, typeV, vehicleKey, maxiSpeed, userID, imagesLink);
        }


        //add the new vehicle to the database
        newSignUpKey.set(newVehicle);

        firestore.collection(Constants.PEOPLE_COLLECTION).document(mUser.getUid())
                .update("ownedVehicles", FieldValue.arrayUnion(vehicleKey));

    }





}


