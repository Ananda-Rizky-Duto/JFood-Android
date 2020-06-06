package AnandaRizkyDutoPamungkas.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Kelas ini digunakan untuk mengatur aktivitas pada layout main
 *
 * @author Ananda Rizky Duto Pamungkas
 * @version 6 Juni 2020
 */

public class MainActivity extends AppCompatActivity {

    private int currentUserId;
    private String currentUserName;
    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Button pesanan;
    Button btnPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pesanan  = findViewById(R.id.pesanan);
        btnPromo = findViewById(R.id.promo);

        //Fetch data from login
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
        }

        expandableListView = findViewById(R.id.lvExp);
        refreshList();
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(MainActivity.this, BuatPesananActivity.class);

                int foodId = childMapping.get(listSeller.get(groupPosition)).get(childPosition).getId();
                String foodName = childMapping.get(listSeller.get(groupPosition)).get(childPosition).getName();
                String foodCategory = childMapping.get(listSeller.get(groupPosition)).get(childPosition).getCategory();
                int foodPrice = childMapping.get(listSeller.get(groupPosition)).get(childPosition).getPrice();

                intent.putExtra("item_id",foodId);
                intent.putExtra("item_name",foodName);
                intent.putExtra("item_category",foodCategory);
                intent.putExtra("item_price",foodPrice);
                intent.putExtra("promoo", 2000);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("currentUserName", currentUserName);

                startActivity(intent);
                return true;
            }
        });

        pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                        Intent intent = new Intent(MainActivity.this, SelesaiPesananActivity.class);
                        intent.putExtra("currentUserId", currentUserId);
                        intent.putExtra("currentUserName", currentUserName);
                        startActivity(intent);
            }
        });

        btnPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromoListActivity.class);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("currentUserName", currentUserName);
                startActivity(intent);
            }
        });
    }

    protected void refreshList()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonResponse = new JSONArray(response);
                    if(jsonResponse != null)
                    {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.putExtra("currentUserId", currentUserId);
                        intent.putExtra("currentUserName", currentUserName);
                        for(int i = 0; i < jsonResponse.length(); i++)
                        {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                            JSONObject food = jsonResponse.getJSONObject(i);
                            JSONObject seller = food.getJSONObject("seller");
                            JSONObject location = seller.getJSONObject("location");

                            Location newLocation = new Location(
                                    location.getString("province"),
                                    location.getString("description"),
                                    location.getString("city")
                            );

                            Seller newSeller = new Seller(
                                    seller.getInt("id"),
                                    seller.getString("name"),
                                    seller.getString("email"),
                                    seller.getString("phoneNumber"),
                                    newLocation
                            );

                            Food newFood = new Food(
                                    food.getInt("id"),
                                    food.getString("name"),
                                    newSeller,
                                    food.getInt("price"),
                                    food.getString("category")
                            );

                            boolean tempStatus = true;
                            for(Seller sellerPtr : listSeller) {
                                if(sellerPtr.getId() == newSeller.getId()){
                                    tempStatus = false;
                                }
                            }
                            if(tempStatus==true){
                                listSeller.add(newSeller);
                            }

                            foodIdList.add(newFood);
                            for(Seller sel : listSeller)
                            {
                                ArrayList<Food> temp = new ArrayList<>();
                                for(Food foods : foodIdList)
                                {
                                    if(foods.getSeller().getName().equals(sel.getName()) || foods.getSeller().getEmail().equals(sel.getEmail())
                                            || foods.getSeller().getPhoneNumber().equals(sel.getPhoneNumber()))
                                    {
                                        temp.add(foods);
                                    }
                                }
                                childMapping.put(sel,temp);
                            }

                            expandableListAdapter = new MainListAdapter(MainActivity.this, listSeller, childMapping);
                            expandableListView.setAdapter(expandableListAdapter);
                        }
                    }
                }
                catch(JSONException e)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Load Data Failed.").create().show();
                }
            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}
