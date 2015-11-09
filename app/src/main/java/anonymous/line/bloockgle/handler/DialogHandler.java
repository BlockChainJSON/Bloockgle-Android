package anonymous.line.bloockgle.handler;

import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ander on 4/11/15.
 */
public class DialogHandler extends SilentApiHandler {

    private ProgressDialog dialog;

    public DialogHandler(Context context, String title, String message) {
        dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
    }

    public DialogHandler(Context context, int titleResId, int msgResId) {
        this(context, context.getString(titleResId), context.getString(msgResId));
    }

    @Override
    public void onStart() {
        dialog.show();
    }

    @Override
    public void onFinish() {
        dialog.dismiss();
    }

    @Override
    public void onOkResponse(JSONObject jsonObject) throws JSONException {

    }

    @Override
    public void onErrorResponse(String errorResponse) {

    }
}
