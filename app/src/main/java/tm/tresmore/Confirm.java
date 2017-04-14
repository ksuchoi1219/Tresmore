package tm.tresmore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Confirm extends AppCompatActivity {

    private Button submitButton;
    private EditText userStoreName;
    private EditText userCreditCard;
    private EditText userAmount;
    private ProgressBar pbbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);
        Intent intent = getIntent();

        userStoreName = (EditText) findViewById(R.id.userStoreName);
        userCreditCard = (EditText) findViewById(R.id.userCreditCard);
        userAmount = (EditText) findViewById(R.id.userAmount);
        Bundle info = intent.getExtras();

        String uStoreName = (String) info.get("userStoreName");
        Toast.makeText(this, uStoreName, Toast.LENGTH_SHORT).show();

        String uCardNum = (String) info.get("userCreditCard");
        String uAmount = (String) info.get("userAmount");
        userStoreName.setText(uStoreName);
        userCreditCard.setText(uCardNum);
        userAmount.setText(uAmount);

        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DoImport doLogin = new DoImport();
//                doLogin.execute("");
//            }
//        });
        addListenerOnButton();

    }
    private void addListenerOnButton() {
        final Context context = this;
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(Confirm.this).create(); //Read Update
                alertDialog.setTitle("Verification");
                alertDialog.setMessage("Your receipt has been successfully submitted!");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, Dashboard.class);
                        startActivity(intent);

                    }
                });

                alertDialog.show();
            }

        });

    }

    }
