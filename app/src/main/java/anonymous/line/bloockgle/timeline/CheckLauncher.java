package anonymous.line.bloockgle.timeline;

import android.os.Handler;
import com.chip_chap.services.asynchttp.net.ApiRequester;
import org.json.JSONException;
import org.json.JSONObject;

import anonymous.line.bloockgle.handler.SilentApiHandler;
import anonymous.line.bloockgle.request.CheckApiRequest;


/**
 * Created by Mr.Marshall on 22/10/2015.
 */
public class CheckLauncher extends Thread {

    private Handler handler;
    private CheckApiRequest apiRequest;
    private CheckHolder checkHolder;
    private boolean stop = false;

    public interface CheckHolder {
        void check(JSONObject jsonObject) throws JSONException;
    }

    public CheckLauncher(CheckApiRequest apiRequest, CheckHolder checkHolder){
        this.apiRequest = apiRequest;
        this.handler = new Handler();
        this.checkHolder = checkHolder;
    }

    public void cancel(){
        stop = true;
    }

    @Override
    public void start(){
        handler.post(this);
    }

    @Override
    public void run(){
        new ApiRequester(apiRequest, new SilentApiHandler() {
            @Override
            public void onOkResponse(JSONObject jsonObject) throws JSONException {
                checkHolder.check(jsonObject);
                apiRequest.setSendData(false);
            }

            @Override
            public void onErrorResponse(String errorResponse) {

            }
        }).execute();
        if (!stop){
            handler.postDelayed(this, 10000);
        }
    }
}
