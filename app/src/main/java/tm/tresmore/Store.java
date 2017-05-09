package tm.tresmore;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Store extends AppCompatActivity {

    private ArrayList<String> store = new ArrayList<String>();
    private TextView storeName;
    private TextView storeAddress;
    private ConnectionClass connectionClass;
    private String username = "";
    private Button addStoreButton;
    private Button mapButton;
    private Button nonMapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);

        ListView lv = (ListView) findViewById(R.id.lV);
        lv.setLongClickable(true);
        final Button mapButton = (Button) findViewById(R.id.mapButton);
        final Button nonMapButton = (Button) findViewById(R.id.nonMapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mapButton.setBackgroundColor(Color.WHITE);
                nonMapButton.setBackgroundColor(Color.LTGRAY);

            }
        });
        nonMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mapButton.setBackgroundColor(Color.LTGRAY);
                nonMapButton.setBackgroundColor(Color.WHITE);

            }
        });
        generateListContent();
        lv.setAdapter(new MyListAdapter(this, R.layout.list_item, store));
    }
    private void generateListContent() {
        connectionClass = new ConnectionClass();

        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        username = prefs.getString("UN", "UNKNOWN");

        Connection con = connectionClass.CONN();
        String query = "select name, addr1, city, state, zipcode from dbo.home_stores where loginid='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                storeName.setText(rs.getString(1));
                storeAddress.setText(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));

            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.storeName = (TextView) convertView.findViewById(R.id.storeName);
                viewHolder.address = (TextView) convertView.findViewById(R.id.address);
                viewHolder.button = (Button) convertView.findViewById(R.id.removeButton);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
//            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
//                }
//            });
            mainViewholder.storeName.setText(getItem(position));
            return convertView;
        }
    }
    public class ViewHolder {
        TextView storeName;
        TextView address;
        Button button;
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
