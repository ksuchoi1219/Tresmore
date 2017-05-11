package tm.tresmore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Store extends AppCompatActivity {
    private ArrayList<String> names = new ArrayList<String>();
    private TextView numStores;
    private int intnumStores;
    private ConnectionClass connectionClass;
    private String username = "";
    private Button addStoreButton;
    private Button mapButton;
    private Button nonMapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);
        TextView numStores = (TextView) findViewById(R.id.userNumStores);
        connectionClass = new ConnectionClass();

        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        username = prefs.getString("UN", "UNKNOWN");
        Connection con = connectionClass.CONN();
        String query = "select count(name) from dbo.home_stores where loginid='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                numStores.setText(rs.getString(1) + " out of 10 maximum stores saved");
                intnumStores = Integer.parseInt(rs.getString(1));

            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
        Connection con1 = connectionClass.CONN();
        String query1 = "select name from dbo.home_stores where loginid='" + username + "';";
        ResultSet rs1;
        try {
            Statement stmt = con1.createStatement();
            rs1 = stmt.executeQuery(query1);
            while (rs1.next()) {
                names.add(rs1.getString(1));
            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
        ListView lv = (ListView) findViewById(R.id.lV);
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
        lv.setAdapter(new MyListAdapter(this, R.layout.list_item, names));
        addListenerOnButton();

    }
    private void addListenerOnButton() {
        final Context context = this;
        Button addButton = (Button) findViewById(R.id.addStoreButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add.class);
                startActivity(intent);
            }
        });
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
                viewHolder.button = (Button) convertView.findViewById(R.id.removeButton);

                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            final ViewHolder finalMainViewholder = mainViewholder;
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String deleteStoreName = finalMainViewholder.storeName.getText().toString();

                    AlertDialog alertDialog = new AlertDialog.Builder(Store.this).create();
                    alertDialog.setTitle(deleteStoreName);
                    alertDialog.setMessage("Are you sure to delete this store?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    startActivity(new Intent(Store.this, Store.class));
                                    SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
                                    username = prefs.getString("UN", "UNKNOWN");
                                    Connection con = connectionClass.CONN();
                                    String query = "delete from dbo.home_stores where name = '" + deleteStoreName + "' and loginid = '" + username + "';";
                                    ResultSet rs;
                                    try {
                                        Statement stmt = con.createStatement();
                                        rs = stmt.executeQuery(query);
                                        con.close();
                                    } catch (Exception ex) {
                                        ex.getMessage();
                                    }

                                }
                            });
                    alertDialog.show();

                }
            });
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.storeName.setText(getItem(position));

            return convertView;
        }
    }

    public class ViewHolder {
        TextView storeName;
        Button button;
    }

    }
