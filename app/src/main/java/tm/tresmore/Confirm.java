package tm.tresmore;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Confirm extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private Button confirmButton;
    private EditText userRStoreName;
    private EditText userRCreditCard;
    private EditText userRAmount;
    private EditText userRUsedDate;
    private ProgressBar pbbar;
    private int _day;
    private int _month;
    private int _birthYear;
    private ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);
        connectionClass = new ConnectionClass();

        userRStoreName = (EditText) findViewById(R.id.userReceiptStoreName);
        userRCreditCard = (EditText) findViewById(R.id.userReceiptCardNum);
        userRAmount = (EditText) findViewById(R.id.userReceiptAmount);
        userRUsedDate = (EditText) findViewById(R.id.userReceiptUsedDate);
        userRUsedDate.setOnClickListener(this);
        userRStoreName.setTextColor(Color.BLACK);
        userRCreditCard.setTextColor(Color.BLACK);
        userRAmount.setTextColor(Color.BLACK);
        userRUsedDate.setTextColor(Color.BLACK);

        confirmButton = (Button) findViewById(R.id.confirmButton);



        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm.DoImport doLogin = new Confirm.DoImport();
                doLogin.execute("");
            }
        });


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

    // updates the date in the birth date EditText
    private void updateDisplay() {

        userRUsedDate.setText(new StringBuilder()
                // Month is 0 based so add 1
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
        String uRSName = userRStoreName.getText().toString();
        String uRCnum = userRCreditCard.getText().toString();
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

            if(uRSName.trim().equals("") || uRCnum.trim().equals("") || uRAmount.trim().equals(""))
                z = "Please enter all the criteria.";
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "insert into dbo.home_receipts (code, stName, amount, dtUsed, loginid)" +
                                " values ('" + receiptCode + "', '" + uRSName + "', '" + uRAmount + "', '" + uRDateUsed + "', '" +  username + "');";

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
