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

import anonymous.line.bloockgle.bloockgle.handler.ErrorHandler;

/**
 * Created by Mr.Marshall on 23/09/2015.
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private TimeLineAdapter timeLineAdapter;
    private ArrayList<TimeLineItem> timeLineItems;
    private EditText editTextBuscar;
    private int currentPage = 1;
    private int totalPages = 0;

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

        View nextPage = findViewById(R.id.next);
        View lastPage = findViewById(R.id.left);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage++;
                if (currentPage > totalPages) {
                    currentPage = totalPages;
                } else {
                    getTimeLine();
                }

            }
        });

        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage--;
                if (currentPage < 1) {
                    currentPage = 0;
                } else {
                    getTimeLine();
                }
            }
        });
        getTimeLine();

    }

    private int getTotalPages(int count) {
        if (count % 10 == 0) {
            return (count / 10);
        } else {
            return (count / 10) + 1;
        }
    }

    private void setPagination(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    private void setPagination(int currentPage) {
        setPagination(currentPage, totalPages);
    }

    private void getTimeLine() {
        timeLineItems = new ArrayList<>();
        new ApiRequester(new GetTimeLineRequest(currentPage), new ErrorHandler(){

            @Override
            public void onOkResponse(JSONObject jsonObject) throws JSONException {
                JSONArray content = jsonObject.getJSONArray("content");
                int count = content.getJSONObject(0).getInt("count");
                setPagination(currentPage, getTotalPages(count));
                clearData();
                getReferenceData(jsonObject);
            }

            @Override
            public void onErrorResponse(int code, String errorResponse) {
                getTimeLine();
            }
        }).execute();
    }

    public void getReferenceData (final JSONObject jsonObject) throws JSONException {
        JSONArray content = jsonObject.getJSONArray("content");
        Log.e("Item", content.length()+"");
        for (int x = 0; x < content.length(); x++) {

            JSONObject item = content.getJSONObject(x);
            final String reference = item.getString("ref");
            new ApiRequester(new GetDataRequest(reference), new ErrorHandler() {
                @Override
                public void onOkResponse(JSONObject jsonObject) throws JSONException {
                    TimeLineItem timeLineItem = new TimeLineItem(jsonObject, reference);
                    timeLineItems.add(timeLineItem);
                    initializedListView();
                }

                @Override
                public void onErrorResponse(int code, String errorResponse) {
                    try {
                        getReferenceData(jsonObject);
                    } catch (JSONException e) {
                        onError(e);
                    }
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


    public void initializedListView(){
        ListView listView = (ListView) findViewById(R.id.list_item);
        timeLineAdapter = (new TimeLineAdapter(this, R.layout.time_line_row, timeLineItems));
        listView.setAdapter(timeLineAdapter);
        timeLineAdapter.notifyDataSetChanged();
    }
}
