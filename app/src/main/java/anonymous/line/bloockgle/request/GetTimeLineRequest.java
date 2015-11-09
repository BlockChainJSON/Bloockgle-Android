package anonymous.line.bloockgle.request;

import java.util.Map;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class GetTimeLineRequest extends BasicApiRequest {

    private String action = "findword";
    private String word;
    private int page = 1;

    public GetTimeLineRequest (String action, String word){
        this.word = word;
        this.action = action;
    }

    public GetTimeLineRequest (int page){
        this.page = page;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = super.getParams();
        if (word != null) {
            params.put("find", word);
            params.put("action", "findword");
        } else {
            params.put("page", String.valueOf(page));
            params.put("action", action);
        }

        return params;
    }

}
