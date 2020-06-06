package AnandaRizkyDutoPamungkas.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PromoListActivity extends AppCompatActivity {

    ListView PromoListView;

    /**
     * Method to be executed on create
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_list);
        PromoListView = (ListView) findViewById(R.id.PromoListView);
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<String> promoList = new ArrayList<>();
                    JSONArray promos = new JSONArray(response);
                    for (int i=0; i<promos.length(); i++) {
                        JSONObject objPromo = promos.getJSONObject(i);
                        promoList.add("\nPromo Code\t\t\t\t: " + objPromo.getString("code") +
                                "\nDiscount\t\t\t\t\t\t: Rp. " + objPromo.getInt("discount") +
                                "\nMinimum Price\t\t: Rp. " + objPromo.getInt("minPrice") +
                                "\nActive\t\t\t\t\t\t\t\t: " + objPromo.getBoolean("active")+"\n");
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PromoListActivity.this, android.R.layout.simple_list_item_1, promoList);
                    PromoListView.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        PromoFetchRequest request = new PromoFetchRequest(responseListener);
        RequestQueue queue = new Volley().newRequestQueue(PromoListActivity.this);
        queue.add(request);
    }
}
