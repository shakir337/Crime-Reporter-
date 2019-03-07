package com.example.crimereporter1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PoliceSingupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edittextEmail,edittextPassword,edittextpassword2,editTextName,editTextPhone;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_singup);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.link_login).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        edittextPassword=findViewById(R.id.input_password);
        edittextEmail=findViewById(R.id.input_email);
        edittextpassword2=findViewById(R.id.input_password2);
        editTextName=findViewById(R.id.input_name);
        editTextPhone=findViewById(R.id.input_phone);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }
    private void registeruser(){
        final ProgressDialog progressDialog=new ProgressDialog(PoliceSingupActivity.this);
        final String email=edittextEmail.getText().toString().trim();
        String password=edittextPassword.getText().toString().trim();
        String password2=edittextpassword2.getText().toString().trim();
        final String name=editTextName.getText().toString().trim();
        final String phone=editTextPhone.getText().toString().trim();
        final String type="Police";
        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editTextPhone.setError("Email is required");
            editTextPhone.requestFocus();
            return;
        }
        if(email.isEmpty()){
            edittextEmail.setError("Email is required");
            edittextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edittextEmail.setError("Please Enter the vaild email");
            edittextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edittextPassword.setError("Password is required");
            edittextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            edittextPassword.setError("Password must have at least 6 character");
            edittextPassword.requestFocus();
            return;
        }
        if(!password.equals(password2)){
            edittextpassword2.setError("Password does not match");
            edittextpassword2.requestFocus();
            return;
        }
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Content Loading");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user=new User(name,email,phone,type);
                    Toast.makeText(PoliceSingupActivity.this, mAuth.getUid(), Toast.LENGTH_SHORT).show();

                    mDatabase.child("users").child(mAuth.getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"In OnComplete",Toast.LENGTH_LONG).show();
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(PoliceSingupActivity.this,PoliceLoginActivity.class));
                                        finish();
                                        progressDialog.dismiss();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }

                                }
                            });

                }else{
                    Toast.makeText(getApplicationContext(),"Email Are already registered", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.link_login:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.btn_signup:
                registeruser();
                break;
        }
    }
}
