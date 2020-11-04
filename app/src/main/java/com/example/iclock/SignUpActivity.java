package com.example.iclock;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;
    private EditText contact;
    private EditText college_name;
    private EditText branch_name;
    private Button register;
    private FirebaseAuth mAuth;
    private static final String TAG = "CHECKING_STRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //taking the references
        user = findViewById(R.id.user_username);
        pass = findViewById(R.id.user_password);
        contact = findViewById(R.id.user_contact_number);
        college_name = findViewById(R.id.user_college_name);
        branch_name = findViewById(R.id.user_branch);
        register = findViewById(R.id.user_register_button);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null ){
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String email = user.getText().toString();
                    String password = pass.getText().toString();
                    Log.d(TAG, "onCreate: email = "+email+" password = "+password);
                    if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                    openDashboardActivity();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void openDashboardActivity() {
        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(intent);
    }
    public void Login(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}