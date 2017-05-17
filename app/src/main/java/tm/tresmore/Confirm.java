package tm.tresmore;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Confirm extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private Button confirmButton;;
    private EditText userRAmount;
    private EditText userRUsedDate;
    private Spinner userStoreSpinner;
    private ProgressBar pbbar;

    private int _day;
    private int _month;
    private int _birthYear;
    private String userSTcode;
    private String userStoreSpinnerValue;
    private ArrayList<String> data = new ArrayList<String>();

    private ConnectionClass connectionClass;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        userStoreSpinner = (Spinner) findViewById(R.id.userReceiptStoreName);
        userRAmount = (EditText) findViewById(R.id.userReceiptAmount);
        userRUsedDate = (EditText) findViewById(R.id.userReceiptUsedDate);
        userRUsedDate.setOnClickListener(this);
        userRAmount.setTextColor(Color.BLACK);
        userRUsedDate.setTextColor(Color.BLACK);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        connectionClass = new ConnectionClass();
        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        username = prefs.getString("UN", "UNKNOWN");
        Connection con = connectionClass.CONN();
        String query = "select name from dbo.home_stores where loginid='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                data.add(rs.getString(1));
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_list,data);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_list);
            userStoreSpinner.setAdapter(spinnerArrayAdapter);

            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }

        getSpinnerValue();
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
    public void getSpinnerValue() {
        userStoreSpinnerValue = userStoreSpinner.getSelectedItem().toString();
        Connection con = connectionClass.CONN();
        String query = "select code from dbo.home_stores where loginid='" + username + "' and name = '" + userStoreSpinnerValue + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                userSTcode = rs.getString(1);
            }

            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }

    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _birthYear = year;
        _month = monthOfYear;
        _day = dayOfMonth;
        updateDisplay();
    }
    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(Confirm.this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

    private void updateDisplay() {

        userRUsedDate.setText(new StringBuilder()
                .append(_month + 1).append("/").append(_day).append("/").append(_birthYear).append(" "));
    }
    public class DoImport extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        String username = prefs.getString("UN", "UNKNOWN");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String receiptCode = sdf.format(new Date());
        String code = username + "_" + receiptCode;
        String uRSName = userStoreSpinner.getSelectedItem().toString();
        String uRAmount = userRAmount.getText().toString();
        String uRDateUsed = userRUsedDate.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(Confirm.this,r,Toast.LENGTH_SHORT).show();
            if(isSuccess) {
                Intent i = new Intent(Confirm.this, Dashboard.class);
                startActivity(i);
                finish();
            }
        }
        @Override
        protected String doInBackground(String... params) {

            if(uRSName.trim().equals("") || uRAmount.trim().equals(""))
                z = "Please enter all the criteria.";
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "insert into dbo.home_receipts (code, stCode, stName, amount, dtUsed, loginid)" +
                                " values ('" + code + "', '" + userSTcode + "', '" + uRSName + "', '" + uRAmount + "', '" + uRDateUsed + "', '" +  username + "');";

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
