package com.example.collectme;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText getEmail, getPassword;
    private Button signIn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();

        getEmail = rootView.findViewById(R.id.et_email);
        getPassword= rootView.findViewById(R.id.et_password);

        signIn = rootView.findViewById(R.id.btn_login);
        signIn.setOnClickListener(this);

        progressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(R.id.btn_login== v.getId()){
            userLogin();
        }
    }

    private void userLogin() {
        String email = getEmail.getText().toString().trim();
        String password = getPassword.getText().toString().trim();

        if(email.isEmpty()){
            getEmail.setError("Email is required");
            getEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            getEmail.setError("Please enter a provide valid email");
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

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        //redirect to user profile
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(getActivity(),"Check your email to verify your account", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}