package AnandaRizkyDutoPamungkas.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Kelas ini digunakan untuk mengatur layout selesai pesanan
 *
 * @author Ananda Rizky Duto Pamungkas
 * @version 6 Juni 2020
 */
public class SelesaiPesananActivity extends AppCompatActivity {

    private int currentUserId;
    private String currentUserName;
    private int invoiceId;
    private String invoiceDate;
    private Integer invoiceTotalPrice;
    private String invoicePaymentType;

    Button btnInvoiceCancel;
    Button btnInvoiceDone;
    ListView listView;
    TextView tvInvoiceId;
    TextView tvInvoiceCustomerName;
    TextView tvInvoiceDate;
    TextView tvInvoicePaymentType;
    TextView tvInvoiceTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        tvInvoiceId = findViewById(R.id.tvInvoiceId);
        tvInvoiceCustomerName = findViewById(R.id.tvInvoiceCustomerName);
        tvInvoiceDate = findViewById(R.id.tvInvoiceDate);
        tvInvoicePaymentType = findViewById(R.id.tvInvoicePaymentType);
        tvInvoiceTotalPrice = findViewById(R.id.tvInvoiceTotalPrice);
        btnInvoiceCancel = findViewById(R.id.btnInvoiceCancel);
        btnInvoiceDone = findViewById(R.id.btnInvoiceDone);
        listView = (ListView) findViewById(R.id.foodsListView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
        }

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<Food> temp = new ArrayList<Food>();
                    JSONObject invoice = new JSONObject(response);
                    JSONArray foods = invoice.getJSONArray("foods");
                    for (int i = 0; i < foods.length(); i++) {
                        JSONObject food = foods.getJSONObject(i);
                        JSONObject seller = food.getJSONObject("seller");
                        JSONObject location = seller.getJSONObject("location");
                        temp.add(new Food(food.getInt("id"),
                                food.getString("name"),
                                new Seller(seller.getInt("id"),
                                        seller.getString("name"),
                                        seller.getString("email"),
                                        seller.getString("phoneNumber"),
                                        new Location(location.getString("province"), location.getString("description"),
                                                location.getString("city"))),
                                food.getInt("price"), food.getString("category")));
                    }

                    ArrayList<String> foodList = new ArrayList<String>();

                    for (Food food : temp) {
                        String addTemp = "" + food.getName() + "\tRp. " + food.getPrice();
                        foodList.add(addTemp);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SelesaiPesananActivity.this, android.R.layout.simple_list_item_1, foodList);
                    listView.setAdapter(arrayAdapter);

                    invoiceId = invoice.getInt("id");
                    invoiceDate = invoice.getString("date");
                    invoicePaymentType = invoice.getString("paymentType");
                    invoiceTotalPrice = invoice.getInt("totalPrice");
                    tvInvoiceId.setText("" + invoiceId);
                    tvInvoiceDate.setText("" + invoiceDate.substring(0, 9));
                    tvInvoiceCustomerName.setText("" + currentUserName);
                    tvInvoicePaymentType.setText("" + invoicePaymentType);
                    tvInvoiceTotalPrice.setText("" + invoiceTotalPrice);
                }
                catch (JSONException e) {
                    Intent intent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                    intent.putExtra("currentUserId", currentUserId);
                    intent.putExtra("currentUserName", currentUserName);
                    startActivity(intent);
                    e.printStackTrace();
                }
            }
        };

        PesananFetchRequest request = new PesananFetchRequest(currentUserId, responseListener);
        RequestQueue queue = new Volley().newRequestQueue(SelesaiPesananActivity.this);
        queue.add(request);

        btnInvoiceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                PesananBatalRequest pesananBatalRequest = new PesananBatalRequest(invoiceId+"", "Cancelled", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananBatalRequest);
                Toast.makeText(SelesaiPesananActivity.this, "Order Cancelled", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                returnIntent.putExtra("currentUserId", currentUserId);
                returnIntent.putExtra("currentUserName", currentUserName);
                startActivity(returnIntent);
            }
        });

        btnInvoiceDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PesananSelesaiRequest pesananSelesaiRequest = new PesananSelesaiRequest(invoiceId+"", "Finished", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananSelesaiRequest);
                Toast.makeText(SelesaiPesananActivity.this, "Order Finished", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                returnIntent.putExtra("currentUserId", currentUserId);
                returnIntent.putExtra("currentUserName", currentUserName);
                startActivity(returnIntent);
            }
        });
    }
}
