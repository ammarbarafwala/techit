package io.cordova.techit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONArray;

public class DisplayTicket extends AppCompatActivity {

    LinearLayout layoutVert;

    LinearLayout layoutHori;

    Button takeBtn;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ticket);

        layoutVert = (LinearLayout) findViewById(R.id.ticketInfo);
        layoutHori = (LinearLayout) findViewById(R.id.buttonMap);

        TextView txt = new TextView(this);
        txt.setText("Ticket ID#: " + getIntent().getIntExtra("id", 0));
        txt.setBackgroundColor(0xffAAAAAA);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Firstname: " + getIntent().getStringExtra("userFirstName"));
        txt.setBackgroundColor(0xffffffff);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Lastname: " + getIntent().getStringExtra("userLastName"));
        txt.setBackgroundColor(0xffAAAAAA);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Progress: " + getIntent().getStringExtra("currentProgress"));
        txt.setBackgroundColor(0xffffffff);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Phone: " + getIntent().getStringExtra("phoneNumber"));
        txt.setBackgroundColor(0xffAAAAAA);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Email: " + getIntent().getStringExtra("email"));
        txt.setBackgroundColor(0xffffffff);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Date Submitted: " + getIntent().getStringExtra("startDate"));
        txt.setBackgroundColor(0xffAAAAAA);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Department: " + getIntent().getStringExtra("department"));
        txt.setBackgroundColor(0xffffffff);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Location of Problem: " + getIntent().getStringExtra("ticketLocation"));
        txt.setBackgroundColor(0xffAAAAAA);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Priority: " + getIntent().getStringExtra("priority"));
        txt.setBackgroundColor(0xffffffff);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        if(getIntent().getStringExtra("technicians") != null){
            txt.setText("Technicians: " + getIntent().getStringExtra("technicians"));
        }
        else{
            txt.setText("Technicians: " + " currently none...");
        }
        txt.setBackgroundColor(0xffAAAAAA);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        txt = new TextView(this);
        txt.setText("Details: " + getIntent().getStringExtra("details"));
        txt.setBackgroundColor(0xffffffff);
        txt.setTextColor(0xff000000);
        txt.setTextSize(18f);
        layoutVert.addView(txt);

        takeBtn = (Button) findViewById(R.id.takeTicket);
        updateBtn = (Button) findViewById(R.id.updateTicket);

        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updatePage = new Intent(DisplayTicket.this, UpdateTicket.class);
                startActivity(updatePage);
            }
        });

        String updates = getIntent().getStringExtra("updates");

        JSONArray updateJSON = new JSONArray();
        try{
            if(updates != null ){
                updateJSON = new JSONArray(updates);
            }

            for(int i = 0; i < updateJSON.length(); i++){
                
            }

        }catch(Exception e){

        }
    }
}
