package anonymous.line.bloockgle.request;

import java.util.Map;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class GetTimeLineRequest extends BasicApiRequest {

    private String action = "findword";
    private String word;
    private int page = 1;

    public GetTimeLineRequest (String action, String word, int page){
        this.word = word;
        this.action = action;
        this.page = page;
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
            params.put("action", action);
        }

        params.put("page", String.valueOf(page));

        return params;
    }

}
