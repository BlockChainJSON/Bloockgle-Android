package anonymous.line.bloockgle.bloockgle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class PublishContentRequest extends BasicApiRequest {

    private HashMap<String, String> hashMap;

    public PublishContentRequest(HashMap<String, String> content){
        hashMap = content;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> Params = super.getParams();
        Params.put("data", buildJSON(hashMap).toString());
        Params.put("action", "getAddress");
        return Params;

    }

}
