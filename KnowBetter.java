package com.example.behealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KnowBetter extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;
    EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7;
    RadioGroup rg;
    Patient ourPatient;
    String name,gender,phoneno,email,url;
    int age,bmi;
    int height,weight;
    OurViewModel ourViewModel;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_better);
        imageView1=findViewById(R.id.iv_name);
        imageView1.setImageResource(R.drawable.identification);
        imageView2=findViewById(R.id.age_iv);
        imageView2.setImageResource(R.drawable.troglodyte);
        imageView3=findViewById(R.id.gender_iv);
        imageView3.setImageResource(R.drawable.malefemale);
        imageView4=findViewById(R.id.phone_iv);
        imageView4.setImageResource(R.drawable.handgesture);
        imageView5=findViewById(R.id.email_iv);
        imageView5.setImageResource(R.drawable.gmail);
        imageView6=findViewById(R.id.height_iv);
        imageView6.setImageResource(R.drawable.height);
        imageView7=findViewById(R.id.weight_iv);
        imageView7.setImageResource(R.drawable.scale);
        editText1=findViewById(R.id.name_et);
        editText2=findViewById(R.id.age_et);
        editText3=findViewById(R.id.phone_et);
        editText4=findViewById(R.id.email_et);
        editText5=findViewById(R.id.height_et);
        editText6=findViewById(R.id.weight_et);
        editText7=findViewById(R.id.relation_et);
        rg=findViewById(R.id.gender_rg);

        final Button update=findViewById(R.id.button_ua);
        ourViewModel= ViewModelProviders.of(this).get(OurViewModel.class);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc=GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(KnowBetter.this);
        if(account!=null) {
            name=account.getDisplayName();
            editText1.setText(name);
            email=account.getEmail();
            editText4.setText(email);
        }
        else {
            Intent intent=getIntent();
            name=intent.getStringExtra("name");
            email=intent.getStringExtra("email");
            editText1.setText(name);
            editText4.setText(email);}
        ourViewModel.getOurPatient(name).observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                if(patient!=null)
                {
                    update.setText("UPDATE");
                    editText2.setText(Integer.toString(patient.getAge()));
                    editText3.setText(patient.getPhone());
                    editText5.setText(Integer.toString(patient.getHeight()));
                    editText6.setText(Integer.toString(patient.getWeight()));
                    editText7.setText(patient.getFamilyname());
                    String pgender=patient.getGender();
                    if("Male".equalsIgnoreCase(pgender))
                        rg.check(R.id.rbm);
                    else rg.check(R.id.rbf);
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Patient patient=createupdate(new Patient());
                            if(patient==null)
                                Toast.makeText(KnowBetter.this,"Enter Valid Entries",Toast.LENGTH_LONG).show();
                            else {
                                ourViewModel.update(patient);
                                Toast.makeText(KnowBetter.this,"Account Updated",Toast.LENGTH_LONG).show();}
                        }
                    });
                }
                else
                {
                    update.setText("CREATE");
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Patient patient=createupdate(new Patient());
                            if(patient==null)
                                Toast.makeText(KnowBetter.this,"Enter Valid Entries",Toast.LENGTH_LONG).show();
                            else {
                                try{
                                    ourViewModel.insert(patient);
                                    Toast.makeText(KnowBetter.this,"Account Created",Toast.LENGTH_LONG).show();}
                                catch (Exception e){}
                            }
                        }
                    });
                }
            }
        });
        Button button1=findViewById(R.id.button_next);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(KnowBetter.this,BMIIndex.class);
                intent.putExtra("p.name",name);
                startActivity(intent);
            }
        });
        FloatingActionButton floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             signout();
            }
        });
    }
    private Patient createupdate(Patient patient)
    {

        patient.setName(name);
        patient.setAge(Integer.parseInt(editText2.getText().toString()));
        phoneno=editText3.getText().toString();
        patient.setPhone(phoneno);
        height=Integer.parseInt(editText5.getText().toString());
        patient.setHeight(height);
        weight=Integer.parseInt(editText6.getText().toString());
        patient.setWeight(weight);
        patient.setGender(((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString());
        patient.setEmail(email);
        patient.setFamilyname(editText7.getText().toString());
        bmi= (int) (weight/((height*0.025)*(height*0.025)));
        patient.setBmi(bmi);
        if(isValidEmail(email)&&isValidPhoneno(phoneno)){
            return patient;
        }
        else {

            return null;
        }
    }
    private boolean isValidEmail(CharSequence email)
    {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    }
    private  boolean isValidPhoneno(CharSequence number)
    {
        if (number.length()!=10) {
        return false;
        }
        else
        return android.util.Patterns.PHONE.matcher(number).matches();
    }
    private void signout()
    {
          gsc.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  startActivity(new Intent(KnowBetter.this,MainActivity.class));
                  finish();
              }
          });
    }
}