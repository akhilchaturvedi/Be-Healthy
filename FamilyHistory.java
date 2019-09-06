package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class FamilyHistory extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_history);
        recyclerView=findViewById(R.id.rv_fh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        button=findViewById(R.id.button_rv);
        editText=findViewById(R.id.et_fh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String familyname=editText.getText().toString();
                OurViewModel ourViewModel= ViewModelProviders.of(FamilyHistory.this).get(OurViewModel.class);
                ourViewModel.getFamilyMembers(familyname).observe(FamilyHistory.this, new Observer<List<Patient>>() {
                    @Override
                    public void onChanged(List<Patient> patients) {
                        adapter=new RvAdapter(patients);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

    }
}
