package AnandaRizkyDutoPamungkas.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListAdapter = new MainListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
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
                        for(int i = 0; i < jsonResponse.length(); i++)
                        {
                            JSONObject food = jsonResponse.getJSONObject(i);
                            JSONObject seller = food.getJSONObject("seller");
                            JSONObject location = seller.getJSONObject("location");
                        }

                        Seller seller1 = new Seller(2, "Gerald", "gerald@gmail.com", "092812891237", new Location("DKI Jakarta", "Jakarta Timur", "Rumah Gerald"));
                        Food food1 = new Food( 3, "Sashimi", seller1, 7000, "Japanese");

                        listSeller.add(new Seller(seller1.getId(), seller1.getName(), seller1.getEmail(), seller1.getPhoneNumber(), seller1.getLocation()));
                        foodIdList.add(new Food(food1.getId(), food1.getName(), food1.getSeller(), food1.getPrice(), food1.getCategory()));

                        for(Seller sel : listSeller)
                        {
                            ArrayList<Food> temp = new ArrayList<>();
                            for(Food food : foodIdList)
                            {
                                if(food.getSeller().getName().equals(sel.getName()) || food.getSeller().getEmail().equals(sel.getEmail())
                                        || food.getSeller().getPhoneNumber().equals(sel.getPhoneNumber()))
                                {
                                    temp.add(food);
                                }
                            }
                            childMapping.put(sel,temp);
                        }
                    }
                }
                catch(JSONException e)
                {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

                ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lvExp);
                MainListAdapter listAdapter = new MainListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);
                expListView.setAdapter(listAdapter);
            }
        };
    }
}
