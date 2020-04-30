package AnandaRizkyDutoPamungkas.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = findViewById(R.id.lvExp);
        refreshList();
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
