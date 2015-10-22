package anonymous.line.bloockgle.bloockgle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.chip_chap.services.asynchttp.net.ApiRequester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mr.Marshall on 23/09/2015.
 */
public class MainActivity extends Activity {

    private TimeLineAdapter timeLineAdapter;

    private EditText editTextBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBuscar = (EditText) findViewById(R.id.edittext);

        final View buscar = findViewById(R.id.buscar);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recoger el texto del edittext
                String searchTXT = editTextBuscar.getText().toString();
                if (!searchTXT.isEmpty() && searchTXT.length() > 3){
                    new ApiRequester(new GetTimeLineRequest("findword", searchTXT), new SilentApiHandler() {
                        @Override
                        public void onOkResponse(JSONObject jsonObject) throws JSONException {
                            clearData();
                            getReferenceData(jsonObject);
                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {

                        }
                    }).execute();
                }
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.btnpublish);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.info);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
        new ApiRequester(new GetTimeLineRequest(1), new SilentApiHandler(){

            @Override
            public void onOkResponse(JSONObject jsonObject) throws JSONException {
                getReferenceData(jsonObject);
            }

            @Override
            public void onErrorResponse(String errorResponse) {

            }
        }).execute();

    }

    public void getReferenceData (JSONObject jsonObject) throws JSONException {
        JSONArray content = jsonObject.getJSONArray("content");
        Log.e("Item", content.length()+"");
        for (int x = 0; x < content.length(); x++) {

            JSONObject item = content.getJSONObject(x);
            final String reference = item.getString("ref");
            new ApiRequester(new GetDataRequest(reference), new SilentApiHandler() {
                @Override
                public void onOkResponse(JSONObject jsonObject) throws JSONException {
                    Log.e("error", "ERROR");
                    TimeLineItem timeLineItem = new TimeLineItem(jsonObject, reference);
                    Log.e("error", "ERROR");
                    initializedListView(timeLineItem);
                    Log.e("error", "ERROR");
                }

                @Override
                public void onErrorResponse(String errorResponse) {

                }
            }).execute();
        }
    }

    public void clearData() {
        if (timeLineAdapter != null){
            timeLineAdapter.clear();
            timeLineAdapter.notifyDataSetChanged();
        }
    }


    public void initializedListView(TimeLineItem timeLineItem){
        ListView listView = (ListView) findViewById(R.id.list_item);
        if (timeLineAdapter == null){
            timeLineAdapter = (new TimeLineAdapter(this, R.layout.time_line_row, new ArrayList<TimeLineItem>()));
            listView.setAdapter(timeLineAdapter);
        }
        timeLineAdapter.add(timeLineItem);
        timeLineAdapter.notifyDataSetChanged();

    }
}
