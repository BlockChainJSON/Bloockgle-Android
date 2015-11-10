package anonymous.line.bloockgle.request;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Marshall on 22/10/2015.
 */
public class CheckApiRequest extends BasicApiRequest {

    private static final String TAG = "CheckApiRequest";
    private HashMap<String, String> hashMap;
    private boolean sendData = true;
    private String address;

    public CheckApiRequest(HashMap<String, String> content, String address){
        this.address = address;
        hashMap = content;
    }

    public void setSendData(boolean sendData) {
        this.sendData = sendData;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = super.getParams();
        if (sendData){
            params.put("datos", buildJSON(hashMap).toString());
        }
        params.put("action", "check");
        params.put("address", address);
        return params;

    }
}
