package com.example.carpoolbuddyappmain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carpoolbuddyappmain.Models.Car;
import com.example.carpoolbuddyappmain.Models.ElectricCar;
import com.example.carpoolbuddyappmain.Models.MiniBus;
import com.example.carpoolbuddyappmain.Models.Motorbike;
import com.example.carpoolbuddyappmain.Models.User;
import com.example.carpoolbuddyappmain.Models.Vehicle;
import com.example.carpoolbuddyappmain.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewC extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;

    private TextView location, price, model, type;
    private String loca, pri, mod, ty, batt;
    private int position;
    private Vehicle selectedVehicle;

    private ArrayList<Vehicle> vehicleList;
    private LinearLayout layout;

    private TextView battSize;
    private TextView speed;
    private TextView nRoom;
    private TextView pSize;


    private TextView carMaxCapacityTextView;
    private TextView carRemainingCapacity;
    private TextView vehicleID;

    //for the owner
    private Button buttonReservedRide;
    private Button buttonCloseRide;

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_click);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        imageView = findViewById(R.id.imageView);


        if(getIntent().hasExtra("vehicleList") && getIntent().hasExtra("vehiclePos")){

            vehicleList = (ArrayList<Vehicle>) getIntent().getSerializableExtra("vehicleList");
            position = (int) getIntent().getSerializableExtra("vehiclePos");


            //vehicle.getModel();
            selectedVehicle = vehicleList.get(position);
            layout = findViewById(R.id.mainLayout);
            price = findViewById(R.id.priceN);
            model = findViewById(R.id.modelN);
            location = findViewById(R.id.locationN);
            carMaxCapacityTextView = findViewById(R.id.maxCa);
            carRemainingCapacity = findViewById(R.id.reCa);
            vehicleID = findViewById(R.id.bookedUID);

            imageView = findViewById(R.id.imageView);

            loca = selectedVehicle.getLocation();
            pri = String.valueOf(selectedVehicle.getPrice());
            mod = selectedVehicle.getModel();
            String book = selectedVehicle.getVehicleID();
            carMaxCapacityTextView.setText(String.valueOf("Maximum Capacity:"+ selectedVehicle.getCapacity()));
            carRemainingCapacity.setText(String.valueOf("Seats left: "+ selectedVehicle.getRemainingCapacity()));
            vehicleID.setText(selectedVehicle.getReservedUIDs().toString());
            location.setText("Location: "+ loca);
            price.setText("Price: "+ pri);
            model.setText("Model: "+ mod);
            vehicleID.setText("Vehicle ID: "+ book);

            String il = selectedVehicle.getImage();
            Picasso.get().load(il).into(imageView);


            if (vehicleList.get(position).getType().equals(Constants.ELECTRIC_CAR)) {
                ElectricCar electricCar = (ElectricCar) vehicleList.get(position);
                //   System.out.println(electricCar.getBatterySize());
                battSize = new TextView(this);
                battSize.setHint("Battery Size: "+electricCar.getBatterySize());
                layout.addView(battSize);

            } else if(vehicleList.get(position).getType().equals(Constants.MOTOR_BIKE)) {
                Motorbike motorBike = (Motorbike) vehicleList.get(position);
                speed = new TextView(this);
                speed.setHint("Max Speed: "+motorBike.getMaxSpeed());
                layout.addView(speed);

            }
            else if(vehicleList.get(position).getType().equals(Constants.MINI_BUS)){
                MiniBus miniBus = (MiniBus) vehicleList.get(position);
                nRoom = new TextView(this);
                nRoom.setHint("Number of Rooms Avaliable: "+miniBus.getNumOfRooms());
                layout.addView(nRoom);

            }
            else if(vehicleList.get(position).getType().equals(Constants.CAR)){
                Car car = (Car) vehicleList.get(position);
                pSize = new TextView(this);
                pSize.setHint("Plane Size: "+car.getRange());
                layout.addView(pSize);
            }
        }

        buttonReservedRide = findViewById(R.id.buttonReserveVehicle);
        buttonReservedRide.setOnClickListener(this);

        buttonCloseRide = findViewById(R.id.buttonCloseR);
        buttonCloseRide.setOnClickListener(this);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore.collection(Constants.PEOPLE_COLLECTION).document(mUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User tempUser = documentSnapshot.toObject(User.class);

                if(tempUser != null && tempUser.getOwnedVehicle().contains(selectedVehicle.getVehicleID())){
                    buttonCloseRide.setVisibility(View.VISIBLE);
                }
                else{
                    buttonReservedRide.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void book(){

        //make vehicle unavailable if no capacity
        if(selectedVehicle.getRemainingCapacity() == 1){
            firestore.collection("vehicle").document(selectedVehicle.getVehicleID())
                    .update("open", false);
        }
        //update capacity
        System.out.println("HERE:");
        System.out.println(selectedVehicle.getRemainingCapacity());
        firestore.collection("vehicle").document(selectedVehicle.getVehicleID())
                .update("remainingCapacity", selectedVehicle.getRemainingCapacity() -1);

        //add user's uid to the list of reserved user uids
        selectedVehicle.addReservedUIDs(mAuth.getUid());
        firestore.collection("vehicle").document(selectedVehicle.getVehicleID())
                .update("reservedUids", selectedVehicle.getReservedUIDs())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //go back to VehiclesInfoActivity
                        Intent intent = new Intent(getApplicationContext(), VehiclesInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    private void closeRide(){
        firestore.collection("vehicle").document(selectedVehicle.getVehicleID())
                .update("open", false);

        //go back to VehiclesInfoActivity
        Intent intent = new Intent(getApplicationContext(), VehiclesInfoActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==buttonReservedRide.getId()){
            book();
        }
        else if(i == buttonCloseRide.getId()){
            closeRide();
        }
    }

}
