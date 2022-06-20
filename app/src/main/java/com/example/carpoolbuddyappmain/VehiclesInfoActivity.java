package com.example.carpoolbuddyappmain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.carpoolbuddyappmain.Models.Car;
import com.example.carpoolbuddyappmain.Models.ElectricCar;
import com.example.carpoolbuddyappmain.Models.MiniBus;
import com.example.carpoolbuddyappmain.Models.Motorbike;
import com.example.carpoolbuddyappmain.Models.Vehicle;
import com.example.carpoolbuddyappmain.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VehiclesInfoActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth mUser;
    private FirebaseFirestore firestore;
    private RecyclerView recView;
    private Context context;

    //added for testing
    private ArrayList<Vehicle> vehiclesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_info);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        recView = findViewById(R.id.recView);

        //added for testing
        vehiclesList = new ArrayList<Vehicle>();

        testDB();
    }


    public void testDB() {
        vehiclesList.clear();
        TaskCompletionSource<String> getAllRidesTask = new TaskCompletionSource<>();
        firestore.collection(Constants.VEHICLE_COLLECTION).whereEqualTo("open", true)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                /*if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        vehiclesList.add(document.toObject(Vehicle.class));
                    }
                    getAllRidesTask.setResult(null);
                }
                else {
                    Log.d("VehiclesInfoActivity", "Error getting documents from db: ", task.getException());
                }

                 */
                if(task.isSuccessful() && task.getResult() != null){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        if(document.get("type").equals(Constants.ELECTRIC_CAR)) {
                            vehiclesList.add(document.toObject(ElectricCar.class));
                        }
                        if(document.get("type").equals(Constants.CAR)) {
                            vehiclesList.add(document.toObject(Car.class));
                        }
                        if(document.get("type").equals(Constants.MOTOR_BIKE)) {
                            vehiclesList.add(document.toObject(Motorbike.class));
                        }
                        if(document.get("type").equals(Constants.MINI_BUS)) {
                            vehiclesList.add(document.toObject(MiniBus.class));
                        }

                    }

                    getAllRidesTask.setResult(null);
                }
                else{
                    Log.d("VehiclesInfoActivity", "Error getting comments from db: ", task.getException());
                }
            }
        });
        // when all rides have been retrieved, update RecyclerView
        getAllRidesTask.getTask().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
               // System.out.println("VEHICLE INFO: " + vehiclesList.toString());
                getAllRidesTask.getTask().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {


                        RecAdapter myAdapter = new RecAdapter(vehiclesList ,new RecHolder.ItemClickListener() {

                            @Override
                            public void onItemClick(ArrayList<Vehicle> details, int position) {
                                //  showToast(details.getLocation()+"CLICKED");

                                Intent i = new Intent(context, RecyclerViewC.class);
                                i.putExtra("vehicleList", vehiclesList);
                                i.putExtra("vehiclePosition", position);
                                startActivity(i);
                            }
                        });
                        System.out.println(vehiclesList.toString());
                        System.out.print("Vehicles list gotten from server" + vehiclesList);
                        recView.setAdapter(myAdapter);
                        recView.setLayoutManager(new LinearLayoutManager(VehiclesInfoActivity.this));
                    }
                });
            }
        });
    }

    public void gotoUserProfile(View v) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

}