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

public class MenuRequest extends StringRequest {
    private static final String URL = "http://192.168.0.108:8080/food";
    private Map<String, String> params;

    public MenuRequest(Response.Listener<String> listener)
    {
        super(Method.GET, URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}
