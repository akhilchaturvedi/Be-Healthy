package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class BMIIndex extends AppCompatActivity {
    String name;
    TextView tvbmi,tviw,tvyouare,tvad;
    ImageView ivface;
    Button buttonmed;
    private String url = "";
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiindex);
        ImageView bmi=findViewById(R.id.image_bmi);
        ImageView iw=findViewById(R.id.image_weight);
        bmi.setImageResource(R.drawable.bmi);
        iw.setImageResource(R.drawable.weight);
        tvbmi=findViewById(R.id.tv_bmi);
        tviw=findViewById(R.id.tv_iw);
        ivface=findViewById(R.id.iv_face);
        tvyouare=findViewById(R.id.tv_youare);
        tvad=findViewById(R.id.tv_advice);
        buttonmed=findViewById(R.id.button_medical);
        tvad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigateurl(url);
            }
        });
//        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        GoogleSignInClient gsc= GoogleSignIn.getClient(this,gso);
//        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(BMIIndex.this);
//        if(account!=null)
//            name=account.getDisplayName();
//        else
//        {
            Intent intent=getIntent();
            name=intent.getStringExtra("p.name");
       // }
        final OurViewModel ourViewModel=ViewModelProviders.of(this).get(OurViewModel.class);
        ourViewModel.getOurPatient(name).observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                    setter(patient);

            }
        });
        buttonmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BMIIndex.this,MedicalIssue.class));
            }
        });
        Button alert=findViewById(R.id.button_alert);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BMIIndex.this,AlarmSetter.class));
            }
        });
    }

    private void Navigateurl(String url) {
        if(!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(BMIIndex.this,webview1.class);
            intent.putExtra("url1",url);
            startActivity(intent);
        }
    }

    void setter(Patient patient)
    {
        int idealWeight=0;
        int bmi=patient.getBmi();
        String gender=patient.getGender();
        tvbmi.setText("Your BMI is: \n"+bmi);
        if("Male".equalsIgnoreCase(gender)) {
            idealWeight= (int) (50.0+2.3*(patient.getHeight()-60));
        }
        else {
            idealWeight= (int) (45.5+2.3*(patient.getHeight()-60));
        }
        tviw.setText("IdealWeight: \n"+idealWeight);
        if(bmi<18.5)
        {tvyouare.setText("SORRY! You are underweight");
        ivface.setImageResource(R.drawable.sad);
        }
        else {
            if (bmi > 24.9) {
                tvyouare.setText("SORRY! You are overweight");
                ivface.setImageResource(R.drawable.worried);
                url = "https://www.healthline.com/nutrition/how-to-lose-weight-as-fast-as-possible";
            } else {
                tvyouare.setText("YAYY! You are fit");
                ivface.setImageResource(R.drawable.fitness);
                url = "https://www.healthline.com/nutrition/maintain-weight-loss";
            }
        }


    }
}
