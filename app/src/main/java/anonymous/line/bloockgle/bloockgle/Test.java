package anonymous.line.bloockgle.bloockgle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.chip_chap.services.asynchttp.net.ApiRequester;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mr.Marshall on 24/09/2015.
 */
public class Test extends Activity{

    public  void CallGetFile(String ref){
        new ApiRequester(new GetFileRequest(ref), new SilentApiHandler() {
            @Override
            public void onOkResponse(JSONObject jsonObject) throws JSONException {

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Test.this);
                dialog.setMessage(errorResponse);
                dialog.setCancelable(false);
                dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }).execute();
    }
}
