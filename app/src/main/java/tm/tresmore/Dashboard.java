package tm.tresmore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dashboard extends AppCompatActivity {

    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar mToolbar;
    private Button submitReceiptButton;
    private Button trespassButton;
    private TextView userCommissionTV;
    private TextView userMemberCommissionTV;

    private ConnectionClass connectionClass;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        userCommissionTV = (TextView) findViewById(R.id.userCommission);
        userMemberCommissionTV = (TextView) findViewById(R.id.userMemberCommission);

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

        connectionClass = new ConnectionClass();
        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        username = prefs.getString("UN", "UNKNOWN");
        Connection con = connectionClass.CONN();
        String query = "select commission from dbo.users where loginid='" + username + "';";
        ResultSet rs;

        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                double money = Double.parseDouble(rs.getString(1));
                String moneyForm = String.format("%.2f", new BigDecimal(money));
                userCommissionTV.setText("$ " + moneyForm);
                userMemberCommissionTV.setText("$ " + moneyForm);
            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    public void navigation() {
        // MENU -> MY PAGE
        LinearLayout mypageLinearLayout = (LinearLayout) findViewById(R.id.menuMyPage);
        mypageLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            public void onClick (View v) {
                Intent intent = new Intent(Dashboard.this, Dashboard.class);
                startActivityForResult(intent, 0);
            }
        });
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
//        // MENU -> RECEIPTS
//        LinearLayout receiptsLinearLayout = (LinearLayout) findViewById(R.id.menuReceipts);
//        receiptsLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){
//
//            public void onClick (View v) {
//                Intent intent = new Intent(Dashboard.this, Receipt.class);
//                startActivityForResult(intent, 0);
//            }
//        });
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
    }
}
