package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicalIssue extends AppCompatActivity implements AlertDailogueBox.onMultiChoiceListener {
    Button button1,buttonfh;
    ImageView ivfriend;
    private static final String MESSAGE_KEY="solutionurl";
    String message="Hi! Your friend wants you to go through these links for the best cure of your medical problems\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_issue);
        ivfriend=findViewById(R.id.iv_friend);
        ivfriend.setImageResource(R.drawable.friendship);
        ivfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MedicalIssue.this,MessageSender.class);
                i1.putExtra(MESSAGE_KEY,message);
                startActivity(i1);

            }
        });
        button1=findViewById(R.id.button_issue);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dg=new AlertDailogueBox();
                dg.setCancelable(false);
                dg.show(getSupportFragmentManager(),"multichoice");

            }
        });
        buttonfh=findViewById(R.id.button_fh);
        buttonfh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicalIssue.this,FamilyHistory.class));
            }
        });


    }

    @Override
    public void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemList) {
        //setContentView(R.layout.activity_medical_issue);
        LinearLayout ll = findViewById(R.id.ll_tv);
        for(final String str:selectedItemList)
        {
            TextView textView=new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20f);
            textView.setTextColor(Color.parseColor("#F2F2F2"));
            textView.setText(str);
            ll.addView(textView);
            final String links=getData(str);
            message=message+links+"\n";
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String oururl= links;
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(oururl));
                    startActivity(intent);

                }
            });
        }
    }
    String getData(String problem)
    {
        String url="";
        if("Diabetes".equalsIgnoreCase(problem))
            url="https://wa.kaiserpermanente.org/healthAndWellness?item=%2Fcommon%2FhealthAndWellness%2Fconditions%2Fdiabetes%2FtopTips.html";
        if("Thyroid".equalsIgnoreCase(problem))
            url="https://www.everydayhealth.com/hs/healthy-living-with-hypothyroidism/expert-tips/";
        if("PCOS".equalsIgnoreCase(problem))
            url="https://www.healthline.com/nutrition/how-to-lose-weight-with-pcos";
        if("Cholestrol".equalsIgnoreCase(problem))
            url="https://www.webmd.com/cholesterol-management/features/11-tips-to-cut-your-cholesterol-fast#1";
        if("Fever".equalsIgnoreCase(problem))
            url="https://www.google.com/search?rlz=1C1CHBF_enIN857IN857&ei=oLVfXbGOMMO7rQHNhrewCQ&q=expert+tips+to+control+Gever&oq=expert+tips+to+control+Gever&gs_l=psy-ab.3..33i160.3245.4187..4618...0.2..0.260.985.0j3j2......0....1..gws-wiz.......0i71j33i22i29i30.EAdMihpM8Ok&ved=0ahUKEwix3rLJ2pjkAhXDXSsKHU3DDZYQ4dUDCAo&uact=5";
        if("Headache".equalsIgnoreCase(problem))
            url="https://www.webmd.com/migraines-headaches/5-ways-to-get-rid-of-headache#1";
        if("LooseMotion".equalsIgnoreCase(problem))
            url="https://www.stylecraze.com/articles/effective-home-remedies-to-treat-loose-motion/#gref";
        if("PhysicalInjury".equalsIgnoreCase(problem))
            url="https://www.themuse.com/advice/it-hurts-everywhere-how-to-cope-with-an-injury";
        if("Migrane".equalsIgnoreCase(problem))
            url="https://www.health.harvard.edu/pain/new-ways-to-manage-migraines";
        return url;
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}
