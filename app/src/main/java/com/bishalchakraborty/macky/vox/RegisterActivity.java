package com.bishalchakraborty.macky.vox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout email, displayname, password;
    Button register;

    Toolbar toolbar;


  ProgressDialog progressDialog;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        toolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        email = findViewById(R.id.email_reg);
        displayname = findViewById(R.id.display_name_reg);
        password = findViewById(R.id.password_reg);
        register = findViewById(R.id.register_reg);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String email_f = email.getEditText().getText().toString();
        String display_f = displayname.getEditText().getText().toString();
        String password_f = password.getEditText().getText().toString();

        if(!TextUtils.isEmpty(display_f) || !TextUtils.isEmpty(email_f) || !TextUtils.isEmpty(password_f)) {

            progressDialog.setTitle("Registering User");
            progressDialog.setMessage("Please Wait While We Create Your Account");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            registerUser(display_f, email_f, password_f);
        }

    }

    private void registerUser( final String display_f, String email_f, String password_f) {

        mAuth.createUserWithEmailAndPassword(email_f , password_f).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", display_f);
                    userMap.put("status", "Hi there I'm using Vox Chat App.");
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");
                    userMap.put("device_token", device_token);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {


                                progressDialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "Please Login With Your Email & Passsword", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    });


                }

                else {
                    progressDialog.hide();
                    Toast.makeText(RegisterActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
