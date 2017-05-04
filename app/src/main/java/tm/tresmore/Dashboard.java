package tm.tresmore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Dashboard extends AppCompatActivity {
    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    private Button submitReceiptButton;
    private Button trespassButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        navigation();

        addListenerOnButton();

    }
    public void navigation() {
        // MENU -> STORE
        LinearLayout storeLinearLayout = (LinearLayout) findViewById(R.id.menuStore);
        storeLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v) {
                Intent intent = new Intent(Dashboard.this, Store.class);
                startActivityForResult(intent, 0);
            }
        });
        // MENU -> REDEEM
        LinearLayout redeemLinearLayout = (LinearLayout) findViewById(R.id.menuRedeem);
        redeemLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v) {
                Intent intent = new Intent(Dashboard.this, Redeem.class);
                startActivityForResult(intent, 0);
            }
        });
        // MENU -> RECEIPTS
        LinearLayout receiptsLinearLayout = (LinearLayout) findViewById(R.id.menuReceipts);
        receiptsLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v) {
                Intent intent = new Intent(Dashboard.this, Receipt.class);
                startActivityForResult(intent, 0);
            }
        });
        // MENU -> AFFLIATE
        LinearLayout affliateLinearLayout = (LinearLayout) findViewById(R.id.menuAffliate);
        affliateLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v) {
                Intent intent = new Intent(Dashboard.this, Affliate.class);
                startActivityForResult(intent, 0);
            }
        });
        // MENU -> FAQ
        LinearLayout faqLinearLayout = (LinearLayout) findViewById(R.id.menuFAQ);
        faqLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v) {
                Intent intent = new Intent(Dashboard.this, FAQ.class);
                startActivityForResult(intent, 0);
            }
        });
        // MENU -> SUPPORT
        LinearLayout supportLinearLayout = (LinearLayout) findViewById(R.id.menuSupport);
        supportLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v) {
                Intent intent = new Intent(Dashboard.this, Support.class);
                startActivityForResult(intent, 0);
            }
        });
        // MENU -> SIGN OUT
        LinearLayout signoutLinearLayout = (LinearLayout) findViewById(R.id.menuSignout);
        signoutLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v) {
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivityForResult(intent, 0);
            }
        });
    }
    private void addListenerOnButton() {
        final Context context = this;
        submitReceiptButton = (Button) findViewById(R.id.submitReceipteButton);
        submitReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(Dashboard.this).create();
                alertDialog.setTitle("Feature");
                alertDialog.setMessage("Coming Soon!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
//        submitReceiptButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Receipt.class);
//                startActivity(intent);
//            }
//        });
        trespassButton = (Button) findViewById(R.id.trespassButton);
        trespassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(Dashboard.this).create();
                alertDialog.setTitle("Feature");
                alertDialog.setMessage("Coming Soon!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
//        trespassButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Receipt.class);
//                startActivity(intent);
//            }
//        });
    }
//
//        connectionClass = new ConnectionClass();
//        edtuserid = (EditText) findViewById(R.id.userId);
//        edtpass = (EditText) findViewById(R.id.password);
//        btnlogin = (Button) findViewById(R.id.loginButton);
////        pbbar = (ProgressBar) findViewById(R.id.pbbar);
////        pbbar.setVisibility(View.GONE);
//
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DoLogin doLogin = new DoLogin();
//                doLogin.execute("");
//            }
//        });
//    }
//
//    public class DoLogin extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//        String userid = edtuserid.getText().toString();
//        String password = edtpass.getText().toString();
//
//        @Override
//        protected void onPreExecute() {
//            pbbar.setVisibility(View.VISIBLE);
//        }
//        @Override
//        protected void onPostExecute(String r) {
////            pbbar.setVisibility(View.GONE);
//            Toast.makeText(Dashboard.this,r,Toast.LENGTH_SHORT).show();
//            if(isSuccess) {
//                SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
//                prefs.edit().putString("UN", userid).commit();
//                Intent i = new Intent(Dashboard.this, Dashboard.class);
//                startActivity(i);
//                finish();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            if(userid.trim().equals("")|| password.trim().equals(""))
//                z = "Please enter username and password!";
//            else {
//                try {
//                    Connection con = connectionClass.CONN();
//
//                    if (con == null) {
//                        z = "Error in connection with SQL server!";
//                    } else {
//                        String query = "select loginid, password from users where loginid='" + userid + "' and password='" + password + "'";
//                        Statement stmt = con.createStatement();
//                        ResultSet rs = stmt.executeQuery(query);
//
//                        if(rs.next()) {
//                            z = "Login Successful!";
//                            isSuccess=true;
//                        }
//                        else {
//                            z = "Invalid Credentials";
//                            isSuccess = false;
//                        }
//                    }
//                }
//                catch (Exception ex) {
//                    isSuccess = false;
//                    z = ex.getMessage();
//                }
//            }
//            return z;
//        }
//   }
}
