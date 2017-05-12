package tm.tresmore;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import java.util.ArrayList;
import java.util.List;


public class Upload1 extends AppCompatActivity {

    Button btnpic;
    ImageView imgTakenPic;
    private static final int CAM_REQUEST=1313;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload1);

        btnpic = (Button) findViewById(R.id.scanButton);
        imgTakenPic = (ImageView)findViewById(R.id.imageView);
        btnpic.setOnClickListener(new btnTakePhotoClicker());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgTakenPic.setImageBitmap(bitmap);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(Upload1.this, Confirm.class);
                startActivity(i);
            }
        }, 1000);
    }

    class btnTakePhotoClicker implements  Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAM_REQUEST);

        }
    }
//    private ArrayList<String> data = new ArrayList<String>();
//    private Button scanButton;
//    private String userStoreName;
//
//    private static final int RC_OCR_CAPTURE = 9003;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.upload1);
//        findViewById(R.id.scanButton).setOnClickListener(this);
//        scanButton = (Button) findViewById(R.id.scanButton);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.scanButton) {
//            Intent intent = new Intent(this, OcrCaptureActivity.class);
//            startActivityForResult(intent, RC_OCR_CAPTURE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == RC_OCR_CAPTURE) {
//            if (resultCode == CommonStatusCodes.SUCCESS) {
//                if (data != null) {
//                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
//                    userStoreName = text;
//                    Intent intent = new Intent(getApplicationContext(), Confirm.class);
//                    intent.putExtra("userTextStoreName", userStoreName);
//                    //Toast.makeText(this, userStoreName, Toast.LENGTH_SHORT).show();
//                    //Intent intent2 = new Intent(getApplicationContext(), Upload2.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getBaseContext(), "Text is not recognized.",  Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(getBaseContext(), "OCR is not working properly.",  Toast.LENGTH_SHORT).show();
//            }
//        }
//        else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}
