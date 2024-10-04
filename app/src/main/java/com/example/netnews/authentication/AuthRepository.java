package com.example.netnews.authentication;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.netnews.model.user.Bookmark;
import com.example.netnews.model.user.User;
import com.example.netnews.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AuthRepository {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;



    private Application application;


    public AuthRepository(Application application) {
        this.application = application;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void registerUser(String name, String email, String password, Activity activity){
        auth.createUserWithEmailAndPassword(email,password )
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userID = auth.getUid();
                            User user = new User(name,email);
                            database.getReference().child(userID).setValue(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(activity, "Account not created", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            Intent i = new Intent(activity, MainActivity.class);
                            activity.startActivity(i);
                        }else{
                            Toast.makeText(activity, "Account not created!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void loginUser(String email, String password, Activity activity){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(activity, MainActivity.class);
                            activity.startActivity(i);
                        }else{
                            Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logoutUser(Activity activity){
        auth.signOut();
        Intent i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
    }



}
