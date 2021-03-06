package tm.tresmore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Affliate extends AppCompatActivity {

    private Button webButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affliate);

        webButton = (Button) findViewById(R.id.webButton);
        Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        webButton.startAnimation(animationFadeIn);
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

