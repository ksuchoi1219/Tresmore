package tm.tresmore;

import android.content.Context;
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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Receipt extends AppCompatActivity {

    private ArrayList<Receipts> data = new ArrayList<Receipts>();
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
        String query = "select stName, amount, dtUsed from dbo.home_receipts where loginid='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                double money = Double.parseDouble(rs.getString(2));
                String moneyForm = String.format("%.2f", new BigDecimal(money));
                data.add(new Receipts(rs.getString(1), moneyForm, rs.getString(3)));
            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }

        ListView lv = (ListView) findViewById(R.id.lV);
        lv.setAdapter(new MyListAdapter(Receipt.this, R.layout.receipt_list, data));
        addListenerOnButton();

    }

    private void addListenerOnButton() {
        final Context context = this;
        addReceiptButton = (Button) findViewById(R.id.addReceiptButton);
        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Upload.class);
                startActivity(intent);
            }
        });
    }

    public class MyListAdapter extends ArrayAdapter<Receipts> {
        private final Context context;
        private final ArrayList<Receipts> data;
        private final int layoutResourceId;

        public MyListAdapter(Context context, int layoutResourceId, ArrayList<Receipts> data) {
            super(context, layoutResourceId, data);
            this.context = context;
            this.data = data;
            this.layoutResourceId = layoutResourceId;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;
            if (row == null) {
                LayoutInflater inflater = ((Receipt) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ViewHolder();
                holder.receiptStore = (TextView) row.findViewById(R.id.receiptStore);
                holder.receiptDate = (TextView) row.findViewById(R.id.receiptDate);
                holder.receiptAmount = (TextView) row.findViewById(R.id.receiptAmount);

                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            Receipts r = data.get(position);

            holder.receiptStore.setText(r.getName());
            holder.receiptAmount.setText("$ " + r.getAmount());
            holder.receiptDate.setText(r.getDate());
            return row;
        }
    }

    public class ViewHolder {
        TextView receiptStore;
        TextView receiptDate;
        TextView receiptAmount;
    }
    public class Receipts{
        private String name;
        private String amount;
        private String date;

        public Receipts (String name, String amount, String date) {
            this.name = name;
            this.amount = amount;
            this.date = date;
        }
        public String getName() {
            return name;
        }
        public String getAmount() {
            return amount;
        }
        public String getDate() {
            return date;
        }
    }

}

