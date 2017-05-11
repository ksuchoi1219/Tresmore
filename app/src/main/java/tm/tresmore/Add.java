package tm.tresmore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Add extends AppCompatActivity {
    private ArrayList<String> address = new ArrayList<String>();
    private EditText uStoreName;
    private EditText uStoreAddr;
    private EditText uStoreCity;
    private EditText uStoreState;
    private EditText uStoreZip;
    private ProgressBar pbbar;

    private ConnectionClass connectionClass;
    private String username = "";
    private Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        TextView numStores = (TextView) findViewById(R.id.userNumStores);

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
        uStoreName = (EditText) findViewById(R.id.userStoreName);
        uStoreAddr = (EditText) findViewById(R.id.userStoreAddress);
        uStoreCity = (EditText) findViewById(R.id.userStoreCity);
        uStoreState = (EditText) findViewById(R.id.userStoreState);
        uStoreZip = (EditText) findViewById(R.id.userStoreZipcode);

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
                        String query = "insert into dbo.home_stores (name, addr1, city, state, zipcode, loginid)" +
                                " values ('" + uSName + "', '" + uSAddr + "', '" + uSCity + "', '" + uSState + "', '" + uSZip + "', '" + username + "');";

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
