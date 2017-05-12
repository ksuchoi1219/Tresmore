package tm.tresmore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class Receipt extends AppCompatActivity {

    private ArrayList<String> receipts = new ArrayList<String>();
    private Button addReceiptButton;
    private ConnectionClass connectionClass;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt);

        connectionClass = new ConnectionClass();
        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        username = prefs.getString("UN", "UNKNOWN");
        Connection con = connectionClass.CONN();
        String query = "select stName from dbo.home_receipts where loginid='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                receipts.add(rs.getString(1));
            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
        ListView lv = (ListView) findViewById(R.id.lV);
        lv.setAdapter(new Receipt.MyListAdapter(this, R.layout.receipt_list, receipts));

        addListenerOnButton();

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
            Receipt.ViewHolder mainViewholder = null;

            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                Receipt.ViewHolder viewHolder = new Receipt.ViewHolder();

                viewHolder.receiptStore = (TextView) convertView.findViewById(R.id.receiptStore);
                viewHolder.receiptDate = (TextView) convertView.findViewById(R.id.receiptDate);
                viewHolder.receiptAmount = (TextView) convertView.findViewById(R.id.receiptAmount);

                convertView.setTag(viewHolder);
            }
            mainViewholder = (Receipt.ViewHolder) convertView.getTag();
            mainViewholder.receiptStore.setText(getItem(position));

            return convertView;
        }
    }

    public class ViewHolder {
        TextView receiptStore;
        TextView receiptDate;
        TextView receiptAmount;
    }

}

