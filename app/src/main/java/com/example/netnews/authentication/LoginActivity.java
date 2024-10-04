package com.example.netnews.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.netnews.R;
import com.example.netnews.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.inputEmail.getText() != null && binding.inputPassword.getText() != null){
                    String input_email = binding.inputEmail.getText().toString().trim();
                    String input_pass = binding.inputPassword.getText().toString().trim();
                    authViewModel.loginUser(input_email,input_pass, LoginActivity.this);
                }else{
                    if(binding.inputEmail.getText() == null){
                        binding.inputEmail.setError("This field cannot be empty");
                    }
                    if(binding.inputPassword.getText() == null){
                        binding.inputPassword.setError("This field cannot be empty");
                    }
                }
            }
        });

        binding.goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }
}