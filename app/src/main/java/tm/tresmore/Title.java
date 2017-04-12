package tm.tresmore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Title extends AppCompatActivity {

    private ImageView logo;
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        logo = (ImageView) findViewById(R.id.logo);
        addListenerOnButton();

        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signButton);
        Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        logo.startAnimation(animationFadeIn);
        loginButton.startAnimation(animationFadeIn);
        signupButton.startAnimation(animationFadeIn);

    }
    private void addListenerOnButton() {
        final Context context = this;
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Login.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        logo.clearAnimation();
    }
    public void signupLink (View view) {
        goToUrl ( "http://www.tresmore.com/signup.htm");
    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    }

