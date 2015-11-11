package anonymous.line.bloockgle.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

import java.util.ArrayList;
import java.util.List;

import anonymous.line.bloockgle.R;
import anonymous.line.bloockgle.request.TimeLineRequestManager;
import anonymous.line.bloockgle.timeline.TimeLine;
import anonymous.line.bloockgle.timeline.TimeLineAdapter;
import anonymous.line.bloockgle.timeline.TimeLineItem;

/**
 * Created by Mr.Marshall on 23/09/2015.
 */
public class MainActivity extends Activity implements TimeLine{

    private static final String TAG = "MainActivity";

    private static final int LIST = 0;
    private static final int LOADER = 1;
    private static final int NO_DATA = 2;

    private TimeLineAdapter timeLineAdapter;
    private List<TimeLineItem> timeLineItems;
    private EditText searchEditText;
    private View loaderLayout;
    private View listLayout;
    private View noDataLayout;
    private boolean isLoading = true;
    private TimeLineRequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = (EditText) findViewById(R.id.edittext);
        loaderLayout = findViewById(R.id.loader_layout);
        listLayout = findViewById(R.id.list_layout);
        noDataLayout = findViewById(R.id.no_data_layout);
        final View searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recoger el texto del edittext
                final String searchTXT = searchEditText.getText().toString();
                if (!searchTXT.isEmpty()) {
                    clearData();
                    requestManager.search(searchTXT);
                }
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    clearData();
                    timeLineItems = requestManager.getTimeLineItemList();
                    if (timeLineItems.size() == 0) {
                        requestManager.page(requestManager.currentPage());
                        initializedListView(false, false);
                    } else {
                        initializedListView(false, true);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        View publishButton = findViewById(R.id.btnpublish);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
        View aboutUs = findViewById(R.id.info);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        requestManager = new TimeLineRequestManager(this);
        requestManager.page(1);

        ProgressBar loaderView = (ProgressBar) findViewById(R.id.loader);
        Drawable circleDrawable = new FoldingCirclesDrawable.Builder(this).colors(getCircleColors()).build();
        loaderView.setIndeterminateDrawable(circleDrawable);

    }

    private void showResultLayout(int layout) {
        switch (layout) {
            case LIST:
                loaderLayout.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.GONE);
                listLayout.setVisibility(View.VISIBLE);
                break;
            case LOADER:
                loaderLayout.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);
                listLayout.setVisibility(View.GONE);
                break;
            case NO_DATA:
                loaderLayout.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
                listLayout.setVisibility(View.GONE);
                break;
        }
    }

    private int[] getCircleColors() {
        return new int[] {getResources().getColor(R.color.violet_light3),
                getResources().getColor(R.color.violet_light2),
                getResources().getColor(R.color.violet_light),
                getResources().getColor(R.color.violet)};
    }

    @Override
    public void onTimeLine(TimeLineItem item, boolean first, boolean last) {
        if (requestManager.isNormalRequest()) {
            timeLineItems = requestManager.getTimeLineItemList();
        } else {
            if (timeLineItems == null) {
                timeLineItems = new ArrayList<>();
            }

            timeLineItems.add(item);
        }

        initializedListView(first, last);
    }

    @Override
    public void onError(String errorMessage) {
        showResultLayout(NO_DATA);
    }

    @Override
    public void more() {
        requestManager.newPage();
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    public void clearData() {
        if (timeLineAdapter != null){
            timeLineAdapter.clear();
            timeLineAdapter.notifyDataSetChanged();
        }

        showResultLayout(LOADER);
    }

    public void initializedListView(boolean isFirst, boolean isLast){
        Log.e(TAG, "first: " + isFirst + ", last: " + isLast);
        if (!isFirst && timeLineItems.size() >= 2 && timeLineItems.get(timeLineItems.size() - 2).isFake()) {
            timeLineItems.remove(timeLineItems.size() - 2);
        }
        isLoading = !isLast;
        timeLineItems.add(TimeLineItem.fake());

        ListView listView = (ListView) findViewById(R.id.list_item);

        int scrollPosition = listView.getVerticalScrollbarPosition();
        Log.e(TAG, "Position: " + scrollPosition);
        timeLineAdapter = (new TimeLineAdapter(this, R.layout.time_line_row, timeLineItems, this));
        listView.setAdapter(timeLineAdapter);
        timeLineAdapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(scrollPosition);
        listView.setVerticalScrollbarPosition(scrollPosition);

        if (timeLineItems.size() <= 1) {
            showResultLayout(NO_DATA);
        } else {
            showResultLayout(LIST);
        }
    }

}
