package anonymous.line.bloockgle.bloockgle;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Mr.Marshall on 30/09/2015.
 */
public class TimeLineItem  {

    private String title, address, file;
    private HashMap<String, String> content;

    public TimeLineItem (String title, String address, String file){
        this.title = title;
        this.address = address;
        this.file = file;
    }

    public TimeLineItem (JSONObject jsonObject, String reference){
        content = new HashMap<>();
        try {
            content.put("title", reference);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = keys.next();
                String value = jsonObject.getString(key);
                Log.e("TmeLineItem", key + ": " + value);
                content.put(key, value);

            }
        } catch (JSONException jsonexception){
            Log.e("item", "EROOR", jsonexception);
        }
    }

    public HashMap<String, String> getContent(){
        return  content;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getFile() {
        return file;
    }

    public FileType getFileExtension(){
        return null;
    }

}
