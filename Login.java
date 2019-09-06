package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView imageView=findViewById(R.id.iv_welcomeback);
        imageView.setImageResource(R.drawable.welcome);
        Button login=findViewById(R.id.loginbutton);
        final EditText username=findViewById(R.id.et_username);
        final EditText email=findViewById(R.id.et_password);
        final OurViewModel ourViewModel= ViewModelProviders.of(this).get(OurViewModel.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent=new Intent(Login.this,KnowBetter.class);
                intent.putExtra("name",username.getText().toString());
                intent.putExtra("email",email.getText().toString());
//                ourViewModel.getOurPatient(username.getText().toString()).observe(Login.this, new Observer<Patient>() {
//                    @Override
//                    public void onChanged(Patient patient) {
//                        if(patient!=null)
                           startActivity(intent);
//                        else Toast.makeText(Login.this,"Account Doest Not Exist",Toast.LENGTH_LONG).show();
//                    }
//                });

            }
        });
    }
}
