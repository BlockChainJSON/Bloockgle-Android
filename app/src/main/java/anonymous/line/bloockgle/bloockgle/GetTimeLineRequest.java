package anonymous.line.bloockgle.bloockgle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class GetTimeLineRequest extends BasicApiRequest {

    private String action;
    private String word;

    public GetTimeLineRequest() {
        this.action = "findword";
    }

    public GetTimeLineRequest(String action) {
        this.action = action;
    }

    public GetTimeLineRequest (String action, String word){
        this.word = word;
        this.action = action;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = super.getParams();
        if (word != null) {
            params.put("find", word);
            params.put("action", "findword");
        } else {
            params.put("action", action);
        }

        return params;
    }

}
