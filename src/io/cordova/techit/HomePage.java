package io.cordova.techit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomePage extends AppCompatActivity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        layout = (LinearLayout) findViewById(R.id.ticketView);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        llp.setMargins(0, 0, 0, 15); // llp.setMargins(left, top, right, bottom);

        String ticket = getIntent().getStringExtra("tickets");

        JSONArray ticketJson = new JSONArray();
        try{
            ticketJson = new JSONArray(ticket);

            for(int i = 0; i < ticketJson.length(); i++){
                JSONObject obj = (JSONObject) ticketJson.get(i);

                /*Iterator<String> iter = obj.keys();
                while(iter.hasNext()){
                    String key = iter.next();
                    System.out.println("item: " + key);
                }*/

                int id = obj.getInt("id");
                String username = obj.getString("username");
                String userFirstName = obj.getString("userFirstName");
                String userLastName = obj.getString("userLastName");
                String phone = obj.getString("phone");
                String email = obj.getString("email");
                String currentProgress = obj.getString("currentProgress");
                int unitId = obj.getInt("unitId");
                String details = obj.getString("details");

                String string = obj.getString("startDate");
                DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                Date startDate = format.parse(string);

                string = obj.getString("startDate");
                Date endDate = format.parse(string);

                string = obj.getString("startDate");
                Date lastUpdated = format.parse(string);

                String lastUpdatedTime = obj.getString("lastUpdated");
                String ticketLocation = obj.getString("ticketLocation");
                String completionDetails;

                TextView nBut = new TextView(this);
                nBut.setText("Ticket ID: " + id + "\n" + details);
                nBut.setTextSize(16f);
                nBut.setBackgroundColor(0xffffdf00);
                nBut.setTextColor(0xff000000);
                nBut.setPadding(30, 20, 30, 80);

                nBut.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View arg0, MotionEvent arg1){

                        return false;
                    }
                });

                layout.addView(nBut, llp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
