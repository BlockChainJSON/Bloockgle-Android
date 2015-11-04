package anonymous.line.bloockgle.bloockgle.handler;

import com.chip_chap.services.asynchttp.net.util.ApiHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ander on 4/11/15.
 */
public abstract class ErrorHandler implements ApiHandler {

    private static final String TAG = "ErrorHandler";

    public abstract void onOkResponse(JSONObject jsonObject) throws JSONException;

    @Override
    public void onResponse(int responseCode, JSONObject jsonObject) {
        if (responseCode >= 200 && responseCode < 299) {
            try {
                onOkResponse(jsonObject);
            } catch (JSONException e) {
                onError(e);
            }
        } else if (responseCode == 0) {
            onErrorResponse(0, "Failed connection");
        } else {
            onErrorResponse(responseCode, "");
        }
    }

    public abstract void onErrorResponse(int responseCode, String message);

    @Override
    public void onError(Exception e) {
        if (e instanceof JSONException) {
            e.printStackTrace();
        } else {
            onErrorResponse(0, e.getMessage());
        }

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }
}
