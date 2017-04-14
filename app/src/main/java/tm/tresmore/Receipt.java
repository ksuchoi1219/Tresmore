package tm.tresmore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class Receipt extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<String>();
    private Button addReceiptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt);

        ListView lv = (ListView) findViewById(R.id.lV);
        generateListContent();
        lv.setAdapter(new MyListAdaper(this, R.layout.receipt_list, data));

        addListenerOnButton();

    }
    private void generateListContent() {
        for(int i = 0; i < 1; i++) {
            data.add("WalMart");

        }
    }
    private void addListenerOnButton() {
        final Context context = this;
        addReceiptButton = (Button) findViewById(R.id.addReceiptButton);
        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Upload1.class);
                startActivity(intent);
            }
        });

    }
    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.storeName = (TextView) convertView.findViewById(R.id.storeName);
                viewHolder.userDate = (TextView) convertView.findViewById(R.id.userDate);
                viewHolder.receiptAmount = (TextView) convertView.findViewById(R.id.userReceiptAmount);
                convertView.setTag(viewHolder);
            }
            return convertView;
        }
    }
    public class ViewHolder {
        TextView storeName;
        TextView userDate;
        TextView receiptAmount;
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
