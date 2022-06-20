package com.example.carpoolbuddyappmain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.carpoolbuddyappmain.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText emailField;
    private EditText passwordField;
    private Button signUpButton;
    private Button logInButton;


    private LinearLayout layout;
    private EditText nameField;
    private Spinner userRoleSpinner;
    private String selectedRole;

    //for alumnis
    private EditText gradYearField;

    //for students
    private EditText studentGrade;

    //for teachers
    private EditText teacherSubject;

    //for parents
    private EditText childUIDs;

    private String userId;
    ArrayList<String> ownedVehicles;
    private View GoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        layout = findViewById(R.id.linearLayoutVehicle);
        userRoleSpinner = findViewById(R.id.spinnerAuth);
        spinnerSetUp();
/*
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveToSignUpActivity();
            }
        });

 */
    }
// button goes to sign up page
    // program would quit unexpectedly

    /*
    public void moveToSignUpActivity(){
        Intent nextScreen = new Intent (AuthActivity.this, CreateUserActivity.class);
        startActivity(nextScreen);

    }

     */
    public void signUpAuth(View v)
    {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    // setup spinner where user selects what user type they want to make an account for
    private void spinnerSetUp() {
        String[] userTypes = {Constants.USER_STUDENT, Constants.USER_TEACHER, Constants.USER_ALUMNI, Constants.USER_PARENT};
        // add user types to spinner
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(AuthActivity.this,
                android.R.layout.simple_spinner_item, userTypes);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userRoleSpinner.setAdapter(langArrAdapter);

        //triggered whenever user selects something different
        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedRole = parent.getItemAtPosition(position).toString();
                addUserFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void addUserFields() {
        commonFields();
        if(selectedRole.equals("Alumni")) {
            gradYearField = new EditText(this);
            gradYearField.setHint("Graduation year");
            layout.addView(gradYearField);
        }

        if(selectedRole.equals("Student")) {
            studentGrade = new EditText(this);
            studentGrade.setHint("Student Grade");
            layout.addView(studentGrade);
        }


        if(selectedRole.equals("Teacher")) {
            teacherSubject = new EditText(this);
            teacherSubject.setHint("Subject");
            layout.addView(teacherSubject);
        }

        if(selectedRole.equals("Parent")) {
            childUIDs = new EditText(this);
            childUIDs.setHint("Child's UID");
            layout.addView(childUIDs);
        }
    }

// applies to all user occupations
    public void commonFields() {
        layout.removeAllViewsInLayout();
        nameField = new EditText(this);
        nameField.setHint("Name");
        layout.addView(nameField);
        emailField = new EditText(this);
        emailField.setHint("Email");
        layout.addView(emailField);
        passwordField = new EditText(this);
        passwordField.setHint("Password");
        layout.addView(passwordField);
    }



    public void signIn(View v){

        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();


        mAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success: updates UI with the user's personal information
                            Log.d("SIGN IN", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            Intent nextScreen = new Intent(getBaseContext(), UserProfileActivity.class);
                            startActivity(nextScreen);

                        } else {
                            // Display a fail message to the user, if sign-in fails.
                            Log.w("SIGN IN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
//test below
        //  UUID.randomUUID().toString();

    }

    // updates Firebase with the input info of the user
    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }
    }

}