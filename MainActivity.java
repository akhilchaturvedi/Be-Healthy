package com.example.behealthy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient gsc;
    int REQ_CODE=100;
    OurViewModel ourViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv1=findViewById(R.id.iv_logo);
        iv1.setImageResource(R.drawable.logo2);
        ImageView ivSignIn=findViewById(R.id.iv1);
        ivSignIn.setImageResource(R.drawable.search);
        Button button1=findViewById(R.id.button_signup);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,KnowBetter.class));
            }
        });
        Button button=findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        ivSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
        {
            startActivity(new Intent(MainActivity.this,KnowBetter.class));

        }
    }

    private void signIn()
    {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent,REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleResult(task);
        }
    }
    private void handleResult(Task<GoogleSignInAccount> task)
    {
        try{
            GoogleSignInAccount account=task.getResult(ApiException.class);
//            ourViewModel=ViewModelProviders.of(this).get(OurViewModel.class);
//            Patient patient=new Patient();
//            patient.setName(account.getDisplayName());
//            patient.setEmail(account.getEmail());
//            ourViewModel.insert(patient);
            Intent intent=new Intent(MainActivity.this,KnowBetter.class);
//            intent.putExtra("name",account.getDisplayName());
//            intent.putExtra("email",account.getEmail());
            startActivity(intent);
           }
        catch (ApiException e)
        {
        }
    }
//        patient.setName(editText1.getText().toString());
//        patient.setAge(Integer.parseInt(editText2.getText().toString()));
//        patient.setGender(editText3.getText().toString());
//        patient.setPhone(editText3.getText().toString());
//        patient.setEmail(editText4.getText().toString());
//        patient.setHeight(Double.parseDouble(editText5.getText().toString()));
//        patient.setWeight(Double.parseDouble(editText6.getText().toString()));
   //     Button button=findViewById(R.id.button_create_acc);


}
