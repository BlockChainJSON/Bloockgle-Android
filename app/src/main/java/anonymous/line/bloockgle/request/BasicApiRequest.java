package anonymous.line.bloockgle.request;

import android.util.Log;

import com.chip_chap.services.asynchttp.net.util.ApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class BasicApiRequest implements ApiRequest {

    private static final String TAG = "BasicApiRequest";
    @Override
    public String getMethod() {
        return POST;
    }

    public JSONObject buildJSON(HashMap<String, String> hashMap){
        String title = hashMap.remove("title");
        JSONObject jsonObject = new JSONObject(hashMap);
        try {
            jsonObject.put("title", title);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        hashMap.put("title", title);
        return jsonObject;
    }

    @Override
    public String getURL() {
        return "http://experiments.entropy-factory.com/api.php";
    }

    @Override
    public Map<String, String> getURLParams() {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() {
        return new HashMap<>();
    }
}
