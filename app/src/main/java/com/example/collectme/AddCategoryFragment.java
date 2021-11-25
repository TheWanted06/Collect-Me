package com.example.collectme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;


public class AddCategoryFragment extends Fragment implements View.OnClickListener {

    private EditText CateName, CateDes;
    private Button Add, Cancel;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_add_category, container, false);
        mAuth = FirebaseAuth.getInstance();

        CateName = rootView.findViewById(R.id.category_Name);
        CateDes = rootView.findViewById(R.id.category_Description);

        Add = rootView.findViewById(R.id.btn_Add);
        Cancel = rootView.findViewById(R.id.btn_Cancel);
        Add.setOnClickListener(this);
        Cancel.setOnClickListener(this);

        progressBar = rootView.findViewById(R.id.progressBar2);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(R.id.btn_Add== v.getId()){
            addingCategory();
        }
        if(R.id.btn_Cancel==v.getId()){

        }
    }

    private void addingCategory() {
    }
}