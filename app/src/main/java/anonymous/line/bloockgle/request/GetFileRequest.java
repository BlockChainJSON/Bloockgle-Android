package anonymous.line.bloockgle.request;

import java.util.Map;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class GetFileRequest extends BasicApiRequest {

    private String Reference;

    public GetFileRequest(String Reference){
        this.Reference = Reference;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> Params = super.getParams();
        Params.put("ref", Reference);
        Params.put("action", "getFile");
        return Params;

    }

}
