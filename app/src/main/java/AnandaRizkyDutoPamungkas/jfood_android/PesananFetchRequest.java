package AnandaRizkyDutoPamungkas.jfood_android;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Kelas ini digunakan untuk melakukan HTTP request pada JFood IntelliJ
 *
 * @author Ananda Rizky Duto Pamungkas
 * @version 6 Juni 2020
 */

public class PesananFetchRequest extends StringRequest {

    private static final String URL = "http://192.168.0.108:8080/invoice/customerOngoing/";

    public PesananFetchRequest(int id_customer, Response.Listener<String> listener) {
        super(Method.GET, URL+id_customer, listener, null);
        Log.d("", "PesananFetchRequest: "+id_customer);
    }
}
