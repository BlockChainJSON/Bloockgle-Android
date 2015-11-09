package anonymous.line.bloockgle.timeline;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Mr.Marshall on 30/09/2015.
 */
public class TimeLineItem  {

    private static final String TAG = "TimeLineItem";
    private HashMap<String, String> content;

    public TimeLineItem (JSONObject jsonObject, String reference){
        content = new HashMap<>();
        Log.e(TAG, "JSONItem: " + jsonObject);
        try {
            content.put("title", reference);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = keys.next();
                String value = jsonObject.getString(key);
                Log.e(TAG, key + ": " + value);
                content.put(key, value);
            }
        } catch (JSONException e){
            Log.e(TAG, "ERROR", e);
        }
    }

    public HashMap<String, String> getContent(){
        return  content;
    }

    public String getTitle() {
        return get("title");
    }

    public String getType() {
        return content.get("type");
    }

    public boolean has(String key) {
        return (content.get(key) != null);
    }

    public boolean hasWebLink() {
        return has("link");
    }

    public boolean hasTorrentLink() {
        return has("torrent_link");
    }

    public String get(String key) {
        return content.get(key);
    }

    public String getWebLink() {
        return get("link");
    }

    public String getTorrentLinl() {
        return get("torrent_link");
    }

    public Uri getBitcoinUri() {
        return Uri.parse("bitcoin:" + get("address"));
    }

}
