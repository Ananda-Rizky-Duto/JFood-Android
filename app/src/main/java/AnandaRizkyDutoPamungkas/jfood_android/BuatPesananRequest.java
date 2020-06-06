package AnandaRizkyDutoPamungkas.jfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Kelas ini digunakan untuk melakukan HTTP request pada JFood IntelliJ
 *
 * @author Ananda Rizky Duto Pamungkas
 * @version 6 Juni 2020
 */

public class BuatPesananRequest extends StringRequest {
    private static final String URLCash = "http://192.168.0.108:8080/invoice/createCashInvoice";
    private static final String URLCashless = "http://192.168.0.108:8080/invoice/createCashlessInvoice";
    private Map<String, String> params;

    public BuatPesananRequest(String foodList, String customerId ,Response.Listener<String> listener) {
        super(Method.POST, URLCash, listener, null);
        params = new HashMap<>();
        params.put("foodList", foodList);
        params.put("customerId", customerId);
        params.put("deliveryFee", "0");
    }

    public BuatPesananRequest(String foodList, String customerId, String promoCode ,Response.Listener<String> listener) {
        super(Method.POST, URLCashless, listener, null);
        params = new HashMap<>();
        params.put("foodList", foodList);
        params.put("customerId", customerId);
        params.put("promoCode", promoCode);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}
