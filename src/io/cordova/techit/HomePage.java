package io.cordova.techit;

import android.content.Intent;
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
    private boolean isTouched = false;
    private boolean afterTouched = false;

    int id;
    String technicians;
    String username;
    String userFirstName;
    String userLastName;
    String phone;
    String email;
    String currentProgress;
    String priority;
    int unitId;
    String details;
    Date startDate;
    Date endDate;
    Date lastUpdated;
    String lastUpdatedTime;
    String ticketLocation;
    String completionDetails;
    String updates;

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
            if(ticket != null ){
                ticketJson = new JSONArray(ticket);
            }

            for(int i = 0; i < ticketJson.length(); i++){
                JSONObject obj = (JSONObject) ticketJson.get(i);

                /*Iterator<String> iter = obj.keys();
                while(iter.hasNext()){
                    String key = iter.next();
                    System.out.println("item: " + key);
                }*/

                id = obj.getInt("id");
                JSONArray techList = new JSONArray(obj.getString("technicians"));
                try{
                    for(int j = 0; j < techList.length(); j++){
                        JSONObject techies = (JSONObject) techList.get(i);

                        //System.out.println(techies.toString());
                    }
                }catch(Exception e2){

                }

                username = obj.getString("username");
                userFirstName = obj.getString("userFirstName");
                userLastName = obj.getString("userLastName");
                phone = obj.getString("phone");
                email = obj.getString("email");
                currentProgress = obj.getString("currentProgress");
                priority = obj.getString("currentPriority");
                unitId = obj.getInt("unitId");
                details = obj.getString("details");

                String string = obj.getString("startDate");
                DateFormat format = new SimpleDateFormat("MMMM d, yyyy");
                startDate = format.parse(string);

                string = obj.getString("startDate");
                endDate = format.parse(string);

                string = obj.getString("startDate");
                lastUpdated = format.parse(string);

                lastUpdatedTime = obj.getString("lastUpdated");
                ticketLocation = obj.getString("ticketLocation");
                completionDetails = obj.getString("completionDetails");
                updates = obj.getString("updates");

                TextView nBut = new TextView(this);
                nBut.setText("Ticket ID: " + id + "\n" + details);
                nBut.setTextSize(16f);
                nBut.setBackgroundColor(0xffffdf00);
                nBut.setTextColor(0xff000000);
                nBut.setPadding(30, 20, 30, 80);

                nBut.setOnClickListener(new View.OnClickListener(){
                    private int idInner = id;
                    private String techInner = technicians;
                    private String usernameInner = username;
                    private String userFirstNameInner = userFirstName;
                    private String userLastNameInner = userLastName;
                    private String phoneNumber = phone;
                    private String emailInner = email;
                    private String currentProgessInner = currentProgress;
                    private String priorityInner = priority;
                    private int unitIdInner = unitId;
                    private String detailsInner = details;
                    private Date sdInner = startDate;
                    private Date edInner = endDate;
                    private Date ldInner = lastUpdated;
                    private String ludInner = lastUpdatedTime;
                    private String ticketLocationInner = ticketLocation;
                    private String comDetails = completionDetails;
                    private String upd = updates;

                    @Override
                    public void onClick(View v){
                        //System.out.println(userFirstName + " " + userLastName + " \nDate:" + startDate + " " + lastUpdatedTime + " \n" + "Prority: " + priority);
                        Intent intent = new Intent(HomePage.this, DisplayTicket.class);

                        intent.putExtra("id", idInner);
                        if(!technicians.isEmpty()){
                            intent.putExtra("technicians", technicians);
                        }
                        intent.putExtra("username", usernameInner);
                        intent.putExtra("userFirstName", userFirstNameInner);
                        intent.putExtra("userLastName", userLastNameInner);
                        intent.putExtra("phoneNumber", phoneNumber);
                        intent.putExtra("email", emailInner);
                        intent.putExtra("currentProgress", currentProgessInner);
                        intent.putExtra("priorty", priorityInner);
                        intent.putExtra("unit_id", unitIdInner);
                        intent.putExtra("details", detailsInner);
                        intent.putExtra("startDate", sdInner.toString());
                        if(getIntent().hasExtra("department")) {
                            intent.putExtra("department", getIntent().getStringExtra("department"));
                        }
                        else{
                            intent.putExtra("department", "");
                        }
                        intent.putExtra("endDate", edInner.toString());
                        intent.putExtra("lastUpdated", ldInner.toString());
                        intent.putExtra("lastUpdatedTime", ludInner.toString());
                        intent.putExtra("ticketLocation", ticketLocationInner);
                        intent.putExtra("completionDetails", comDetails);
                        intent.putExtra("updates", upd);

                        //Used to determine what the user can do in the next page.
                        intent.putExtra("position", getIntent().getStringExtra("position"));

                        startActivity(intent);
                    }
                });

                layout.addView(nBut, llp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
