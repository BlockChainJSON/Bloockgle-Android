package anonymous.line.bloockgle.bloockgle;

import java.util.Map;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class PublishFileRequest extends BasicApiRequest {

    private String title, description, file, address;

    public PublishFileRequest(String title, String description, String file, String address){
        this.title = title;
        this.description = description;
        this.file = file;
        this.address = address;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> Params = super.getParams();
        Params.put("title", title);
        Params.put("address", description);
        Params.put("data", file);
        Params.put("address", address);
        Params.put("action", "publishFile");
        return Params;

    }

}
