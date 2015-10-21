package anonymous.line.bloockgle.bloockgle;

import com.chip_chap.services.asynchttp.net.util.ApiRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class BasicApiRequest implements ApiRequest {

    @Override
    public String getMethod() {
        return POST;
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
