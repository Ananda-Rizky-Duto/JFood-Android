package AnandaRizkyDutoPamungkas.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Kelas ini digunakan untuk mengatur komponen yang ada pada layout buat pesanan
 *
 * @author Ananda Rizky Duto Pamungkas
 * @version 6 Juni 2020
 */

public class BuatPesananActivity extends AppCompatActivity {


    //Inisiasi variable
    private int currentUserId;
    private String currentUserName;
    private int id_food;
    private String foodName;
    private String foodCategory;
    private double foodPrice;
    private String promoCode;
    private String selectedPayment;

    //Inisiasi componen yang ada pada layout
    TextView food_name;
    TextView food_category;
    TextView food_price;
    TextView total_price;
    TextView textCode;
    EditText promo_code;
    RadioGroup radioGroup;
    RadioButton cash;
    RadioButton cashless;
    Button pesan;
    Button hitung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        //Fetch data from main activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
            id_food = extras.getInt("item_id");
            foodName = extras.getString("item_name");
            foodCategory = extras.getString("item_category");
            foodPrice = extras.getInt("item_price");
        }

        //Inisiasi componen yang ada pada layout
        food_name = findViewById(R.id.food_name);
        food_category = findViewById(R.id.food_category);
        food_price = findViewById(R.id.food_price);
        total_price = findViewById(R.id.total_price);
        textCode = findViewById(R.id.textCode);
        promo_code = findViewById(R.id.promo_code);
        radioGroup = findViewById(R.id.radioGroup);
        cash = findViewById(R.id.cash);
        cashless = findViewById(R.id.cashless);
        pesan = findViewById(R.id.pesan);
        hitung = findViewById(R.id.hitung);

        //Assign code
        promo_code.setVisibility(View.GONE);
        textCode.setVisibility(View.GONE);

        pesan.setEnabled(false);
        hitung.setEnabled(false);

        food_name.setText(foodName);
        food_category.setText(foodCategory);
        food_price.setText("Rp. " + (int) foodPrice);
        total_price.setText("Rp. " + "0");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selection = radioButton.getText().toString().trim();
                if(selection.equals("Via Cashless"))
                {
                    textCode.setVisibility(View.VISIBLE);
                    promo_code.setVisibility(View.VISIBLE);
                    hitung.setEnabled(true);
                }
                else
                {
                    promo_code.setVisibility(View.GONE);
                    textCode.setVisibility(View.GONE);
                    hitung.setEnabled(true);
                }
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                selectedPayment = radioButton.getText().toString().trim();
                if(selectedPayment.equals("Via Cashless"))
                {
                    //Get Promo Code String
                    promoCode = promo_code.getText().toString();
                    //Listener Promo
                    final Response.Listener<String> promoResponse = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //No promo code applied
                            if (promoCode.isEmpty()) {
                                Toast.makeText(BuatPesananActivity.this, "No Promo Code Applied", Toast.LENGTH_LONG).show();
                                total_price.setText("Rp. " + foodPrice);
                                //Button VIsibility
                                hitung.setEnabled(false);
                                pesan.setVisibility(View.VISIBLE);
                            } else {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    //Get Discount Price
                                    int promoDiscountPrice = jsonResponse.getInt("discount");
                                    int minimalDiscountPrice = jsonResponse.getInt("minPrice");
                                    boolean promoStatus = jsonResponse.getBoolean("active");
                                    //Case if Promo can be Applied
                                    if (promoStatus == false) {
                                        Toast.makeText(BuatPesananActivity.this, "Promo Code can no longer used", Toast.LENGTH_LONG).show();
                                    } else if (promoStatus == true) {
                                        //Promo cannot be applied
                                        if (foodPrice < promoDiscountPrice || foodPrice < minimalDiscountPrice) {
                                            Toast.makeText(BuatPesananActivity.this, "Promo Code cannot be Applied", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(BuatPesananActivity.this, "Promo Code Applied", Toast.LENGTH_LONG).show();
                                            total_price.setText("Rp. " + (foodPrice - promoDiscountPrice));
                                            hitung.setEnabled(false);
                                            pesan.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(BuatPesananActivity.this, "Promo Code not found", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    };
                    PromoFetchRequest promoRequest = new PromoFetchRequest(promoCode, promoResponse);
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(promoRequest);
                }
                if(selectedPayment.equals("Via Cash"))
                {
                    total_price.setText("Rp. " + (int) foodPrice);
                }

                hitung.setEnabled(false);
                pesan.setEnabled(true);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                selectedPayment = radioButton.getText().toString().trim();
                BuatPesananRequest buatPesananRequest = null;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null)
                            {
                                Toast.makeText(BuatPesananActivity.this, "Order Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("currentUserId", currentUserId);
                                intent.putExtra("currentUserName", currentUserName);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(BuatPesananActivity.this, "Order Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException error)
                        {
                            Toast.makeText(BuatPesananActivity.this, "Order Failed", Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }
                };

                if(selectedPayment.equals("Via Cash"))
                {
                    buatPesananRequest = new BuatPesananRequest(id_food+"", currentUserId+"", responseListener);
                }
                if(selectedPayment.equals("Via Cashless"))
                {
                    buatPesananRequest = new BuatPesananRequest(id_food+"", currentUserId+"", promoCode+"", responseListener);
                }
                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(buatPesananRequest);
            }
        });
    }
}
