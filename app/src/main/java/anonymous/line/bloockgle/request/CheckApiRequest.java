package anonymous.line.bloockgle.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Marshall on 22/10/2015.
 */
public class CheckApiRequest extends BasicApiRequest {

    private HashMap<String, String> hashMap;
    private boolean sendData;
    private String address;

    public CheckApiRequest(HashMap<String, String> content, String address){
        this.address = address;
        hashMap = content;
    }

    public void setSendData(boolean sendData){
        this.sendData = sendData;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> Params = super.getParams();
        if (sendData){
            Params.put("data", buildJSON(hashMap).toString());
        }
        Params.put("action", "check");
        Params.put("address", address);
        return Params;

    }
}
