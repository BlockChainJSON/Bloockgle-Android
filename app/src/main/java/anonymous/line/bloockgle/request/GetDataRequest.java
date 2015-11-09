package anonymous.line.bloockgle.request;

import java.util.Map;

/**
 * Created by Mr.Marshall on 15/10/2015.
 */
public class GetDataRequest extends BasicApiRequest {

    private String reference;

    public GetDataRequest(String reference) {
        this.reference = reference;

    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = super.getParams();
        params.put("ref", reference);
        params.put("action", "getData");

        return params;
    }
}
