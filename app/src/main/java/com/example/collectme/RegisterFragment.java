package com.example.collectme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment implements View.OnClickListener{

//    private TextView banner, registerUser;
    private EditText getFirstName, getLastname, getEmail, getPassword, getConfirmPassword;
    private Button registerUser;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();

        registerUser = rootView.findViewById(R.id.btn_register);
        registerUser.setOnClickListener(this);

        getFirstName= rootView.findViewById(R.id.et_firstname);
        getLastname = rootView.findViewById(R.id.et_lastname);
        getEmail = rootView.findViewById(R.id.et_email);
        getPassword= rootView.findViewById(R.id.et_password);
        getConfirmPassword = rootView.findViewById(R.id.et_repassword);

        progressBar = rootView.findViewById(R.id.progressBar);


        return rootView;


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_register){
            registerUser();
        }
    }

    private void registerUser() {
        String firstname = getFirstName.getText().toString().trim();
        String lastname = getLastname.getText().toString().trim();
        String email = getEmail.getText().toString().trim();
        String password = getPassword.getText().toString().trim();
        String confirmPassword = getConfirmPassword.getText().toString().trim();

        if(firstname.isEmpty()){
            getFirstName.setError("First-name is required");
            getFirstName.requestFocus();
            return;
        }
        if(lastname.isEmpty()){
            getLastname.setError("Last-name is required");
            getLastname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            getEmail.setError("Email is required");
            getEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            getEmail.setError("Please provide valid email");
            getEmail.requestFocus();
        }
        if(password.isEmpty()){
            getPassword.setError("Password is required");
            getPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            getPassword.setError("Min Password length should be 6 characters!");
            getPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()){
            getConfirmPassword.setError("Confirm Password is required");
            getConfirmPassword.requestFocus();
            return;
        }
        if(confirmPassword.length() < 6){
            getConfirmPassword.setError("Min Password length should be 6 characters!");
            getConfirmPassword.requestFocus();
            return;
        }
//        if (password == confirmPassword){
//            getConfirmPassword.setError("Matching password is required");
//            getConfirmPassword.requestFocus();
//            return;
//        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(firstname,lastname,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(),"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        //redirect to login layout
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "Failed to register- to connect to db! Try again!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(getActivity(), "Failed to register- to create user! Try again!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}