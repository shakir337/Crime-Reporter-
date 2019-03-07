package com.example.crimereporter1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PoliceLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText edittextEmail,edittextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.link_signup).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        edittextEmail=findViewById(R.id.input_email);
        edittextPassword=findViewById(R.id.input_password);

    }
    private void login(){
        final ProgressDialog progressDialog=new ProgressDialog(PoliceLoginActivity.this);

        final String email=edittextEmail.getText().toString().trim();
        final String password=edittextPassword.getText().toString().trim();
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
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Content Loading");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Expreriment....start
                            mDatabase= FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(mAuth.getCurrentUser().getUid()).child("type");
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String type=dataSnapshot.getValue().toString();
                                    Toast.makeText(getApplicationContext(),type,Toast.LENGTH_LONG).show();
                                    if (type.equals("Police")){
                                        SharedPreferences sp=getSharedPreferences(
                                                "login",MODE_PRIVATE);
                                        SharedPreferences.Editor edit=sp.edit();
                                        edit.putString("email",email);
                                        edit.putString("password",password);
                                        edit.putString("type","police");
                                        edit.commit();
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"User Login  Succufully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(PoliceLoginActivity.this,PolicePanelActivity.class));
                                        finish();
                                        progressDialog.dismiss();
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Wrong Email and Password", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_LONG).show();
                                }
                            });







                            //end
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        // ...
                    }
                });
    }
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.link_signup:
                startActivity(new Intent(this,PoliceSingupActivity.class));
                finish();
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }
}
