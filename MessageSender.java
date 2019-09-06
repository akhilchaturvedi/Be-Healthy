package com.example.behealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MessageSender extends AppCompatActivity {
    ListView listView;
    String phoneno,message;
    private static final String MESSAGE_KEY="solutionurl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_sender);
        Intent i1=getIntent();
        message=i1.getStringExtra(MESSAGE_KEY);
        listView=findViewById(R.id.lv_contacts);
        Cursor c1=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        startManagingCursor(c1);
        String[] from={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone._ID};
        int[] to={android.R.id.text1,android.R.id.text2};
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,c1,from,to);
        listView.setAdapter(simpleCursorAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                phoneno=((TextView)(view.findViewById(android.R.id.text2))).getText().toString();
                sendMessage(phoneno);
            }
        });
    }
    void sendMessage(String phoneno)
    {
        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permission== PackageManager.PERMISSION_GRANTED)
        {
            deliverMessage(phoneno,message);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},1);
        }
    }

    private void deliverMessage(String phoneno, String message) {
        if(!phoneno.equalsIgnoreCase(""))
        {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(phoneno,null,message,null,null);
            Toast.makeText(MessageSender.this,"Message Sent",Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(MessageSender.this,"Enter Number First",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        {
            switch (requestCode){
                case 0:
                    if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    {
                        deliverMessage(phoneno,message);
                    }
            }
        }
    }
}
