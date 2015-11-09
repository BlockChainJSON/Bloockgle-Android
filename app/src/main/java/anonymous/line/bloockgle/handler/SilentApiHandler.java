package anonymous.line.bloockgle.handler;

import com.chip_chap.services.asynchttp.net.util.ApiHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Class SilentApiHandler.
 */
public abstract class SilentApiHandler implements ApiHandler {

    private static final String TAG = "SilentApiHandler";

    /**
     * On ok response.
     *
     * @param jsonObject the json object
     * @throws JSONException the JSON exception
     */
    public abstract void onOkResponse(JSONObject jsonObject) throws JSONException;

    public abstract void onErrorResponse(String errorResponse);
    
    public void onResponse(int statusCode, JSONObject jsonObject) {
        try {
        	if (jsonObject.has("error")) {
        		if (!jsonObject.has("message")) {
        			jsonObject.put("message", "No error message");
        		}
        		this.onErrorResponse(jsonObject.getString("message"));
        		
        	} else {
            	this.onOkResponse(jsonObject);
        	}
        } catch (JSONException e) {
            this.onError(e);
        }
    }
    
    public void onError(Exception e) {
        e.printStackTrace();
    }

    public void onStart() {

    }

    public void onFinish() {

    }
}
