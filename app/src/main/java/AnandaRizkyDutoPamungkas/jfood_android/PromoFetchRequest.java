package AnandaRizkyDutoPamungkas.jfood_android;

import android.util.Log;

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

public class PromoFetchRequest extends StringRequest {

    private static final String URL = "http://192.168.0.108:8080/promo/";
    private Map<String, String> params;

    public PromoFetchRequest(String code, Response.Listener<String> listener) {
        super(Method.GET, URL+code, listener, null);
        params = new HashMap<>();
    }

    public PromoFetchRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
        params = new HashMap<>();
    }
}
