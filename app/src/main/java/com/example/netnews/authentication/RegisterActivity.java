package com.example.netnews.authentication;

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
import com.example.netnews.databinding.ActivityRegisterBinding;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private AuthViewModel authViewModel;

    private TextInputEditText inputName , inputEmail, inputPassword, inputConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        inputName = binding.inputName;
        inputEmail = binding.etEmail;
        inputPassword = binding.etPassword;
        inputConfirmPass = binding.etConfirmPassword;

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputName.getText() != null && inputEmail.getText() != null && inputPassword.getText() != null
                        & inputConfirmPass.getText() != null){
                    String name = inputName.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    String confirmPass = inputConfirmPass.getText().toString().trim();
                    authViewModel.registerUser(name,email,password,RegisterActivity.this);
                }else{
                    if(inputName.getText() == null){
                        inputName.setError("This field cannot be empty");
                    }
                    if(inputPassword.getText() == null){
                        inputPassword.setError("This field cannot be empty");
                    }
                    if(inputEmail.getText() == null){
                        inputEmail.setError("This field cannot be empty");
                    }
                    if(inputConfirmPass.getText() == null){
                        inputConfirmPass.setError("This field cannot be empty");
                    }
                }
            }
        });

    }
}