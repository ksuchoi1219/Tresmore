package tm.tresmore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Add extends AppCompatActivity {

    private EditText uStoreName;
    private EditText uStoreAddr;
    private EditText uStoreCity;
    private EditText uStoreState;
    private EditText uStoreZip;
    private Button confirmButton;
    private ProgressBar pbbar;

    private ArrayList<String> address = new ArrayList<String>();
    private ArrayList<String> storeCodes = new ArrayList<String>();
    private ConnectionClass connectionClass;
    private String username = "";
    private String userNo = "";
    private String stCode = "";
    private int ascValue = 0;
    private long dateFirstLong;
    private long dateNowLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        TextView numStores = (TextView) findViewById(R.id.userNumStores);
        uStoreName = (EditText) findViewById(R.id.userStoreName);
        uStoreAddr = (EditText) findViewById(R.id.userStoreAddress);
        uStoreCity = (EditText) findViewById(R.id.userStoreCity);
        uStoreState = (EditText) findViewById(R.id.userStoreState);
        uStoreZip = (EditText) findViewById(R.id.userStoreZipcode);

        connectionClass = new ConnectionClass();
        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        username = prefs.getString("UN", "UNKNOWN");
        Connection con = connectionClass.CONN();
        String query = "select count(name) from dbo.home_stores where loginid='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                numStores.setText(rs.getString(1) + " out of 10 maximum stores saved");

            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
        Connection con1 = connectionClass.CONN();
        String query1 = "select code from dbo.home_stores where loginid='" + username + "';";
        ResultSet rs1;
        try {
            Statement stmt1 = con1.createStatement();
            rs1 = stmt1.executeQuery(query1);
            while (rs1.next()) {
                storeCodes.add(rs1.getString(1));

            }
            con1.close();
        } catch (Exception ex) {
            ex.getMessage();
        }

        uStoreName.setTextColor(Color.BLACK);
        uStoreAddr.setTextColor(Color.BLACK);
        uStoreCity.setTextColor(Color.BLACK);
        uStoreState.setTextColor(Color.BLACK);
        uStoreZip.setTextColor(Color.BLACK);

        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoImport doLogin = new DoImport();
                doLogin.execute("");
            }
        });

        for (int i = 0; i < username.length(); i++) {
            ascValue = ascValue + (int)(username.charAt(i));
        }
        userNo = Integer.toHexString(ascValue);

        String dateInString = "2017-01-01";
        try {
            Date dateFirst = new SimpleDateFormat("yyyy-MM-dd").parse(dateInString);
            dateFirstLong = dateFirst.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(now.getTime().getTime());
        now.add(Calendar.HOUR, -4);

        String dateNowString = new SimpleDateFormat("yyyy-MM-dd-mm-ss").format(now.getTime());
        try {
            Date dateNow = new SimpleDateFormat("yyyy-MM-dd-mm-ss").parse(dateNowString);
            dateNowLong = dateNow.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int secondsBetween = (int) ((dateNowLong - dateFirstLong) / 1000);
        String diff = Integer.toHexString(secondsBetween);
        stCode = "ST" + userNo + diff;

    }
    public class DoImport extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        String username = prefs.getString("UN", "UNKNOWN");
        String uSName = uStoreName.getText().toString();
        String uSAddr = uStoreAddr.getText().toString();
        String uSCity = uStoreCity.getText().toString();
        String uSState = uStoreState.getText().toString();
        String uSZip = uStoreZip.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(Add.this,r,Toast.LENGTH_SHORT).show();
            if(isSuccess) {
                Intent i = new Intent(Add.this, Store.class);
                startActivity(i);
                finish();
            }
        }
        @Override
        protected String doInBackground(String... params) {

            if(uSName.trim().equals(""))
                z = "Please enter store name.";
            else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "insert into dbo.home_stores (code, name, addr1, city, state, zipcode, loginid)" +
                                " values ('" + stCode + "', '" + uSName + "', '" + uSAddr + "', '" + uSCity + "', '" + uSState + "', '" + uSZip + "', '" + username + "');";
                        Statement stmt = con.createStatement();
                        z = "Imported successfully!";
                        stmt.executeUpdate(query);
                        isSuccess = true;
                    }
                }
                catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }
    }
