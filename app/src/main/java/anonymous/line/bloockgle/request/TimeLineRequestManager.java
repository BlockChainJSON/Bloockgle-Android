package anonymous.line.bloockgle.request;

import android.util.Log;

import com.chip_chap.services.asynchttp.net.ApiRequester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import anonymous.line.bloockgle.handler.ErrorHandler;
import anonymous.line.bloockgle.timeline.TimeLine;
import anonymous.line.bloockgle.timeline.TimeLineItem;

/**
 * Created by ander on 11/11/15.
 */
public class TimeLineRequestManager {

    private static final String TAG = "TLRManager";

    private ApiRequester tlRequester;
    private ApiRequester tlSearch;
    private TimeLine timeLine;
    private int currentRequest = -1;
    private int currentPage = 1;
    private int currentSearchPage = 1;
    private String toSearch = "";
    private List<TimeLineItem> timeLineItemList;

    public TimeLineRequestManager(TimeLine timeLine) {
        this.timeLine = timeLine;
    }

    public void page(final int page) {
        if (timeLineItemList == null) {
            timeLineItemList = new ArrayList<>();
        }

        if (currentPage < page) {
            currentPage = page;
        }

        tlRequester = new ApiRequester(new GetTimeLineRequest(page), new ErrorHandler() {
            @Override
            public void onOkResponse(JSONObject jsonObject) throws JSONException {
                getData(jsonObject, false);
            }

            @Override
            public void onErrorResponse(int code, String errorResponse) {
                if (code == EMPTY_RESPONSE_CODE) {
                    timeLine.onError(errorResponse);

                }
                page(page);

            }
        });
        cancelCurrentRequests(TimeLine.TL_REQUESTER);
        tlRequester.execute();

    }

    public void page() {
        page(++currentPage);
    }

    public List<TimeLineItem> getTimeLineItemList() {
        return timeLineItemList;
    }

    public int currentPage() {
        return currentPage;
    }

    public void search(final String word) {
        if (!word.equals(toSearch)) {
            toSearch = word;
            currentSearchPage = 1;
        }
        tlSearch = new ApiRequester(new GetTimeLineRequest("findword", word, currentSearchPage), new ErrorHandler() {
            @Override
            public void onOkResponse(JSONObject jsonObject) throws JSONException {
                getData(jsonObject, true);
                currentSearchPage++;
            }

            @Override
            public void onErrorResponse(int code, String errorResponse) {
                if (code == EMPTY_RESPONSE_CODE) {
                    timeLine.onError(errorResponse);
                } else {
                    search(word);
                }

            }
        });
        cancelCurrentRequests(TimeLine.TL_SEARCH);
        tlSearch.execute();
    }

    public void search() {
        search(toSearch);
    }

    public void getData(final JSONObject jsonObject, boolean isSearch) throws JSONException {

        final JSONArray content = jsonObject.getJSONArray("content");
        Log.e("Item", content.length() + "");
        TimeLineItem timeLineItem;
        for (int x = 0; x < content.length(); x++) {
            try {
                boolean first = (x == 0);
                boolean last = (x == (content.length() -1));
                Log.e(TAG, "first: " + first + ", last: " + last + ", x = " + x);

                JSONObject item = content.getJSONObject(x);
                final String reference = item.getString("ref");

                JSONObject data;

                data = new JSONObject(item.getString("content"));
                timeLineItem = new TimeLineItem(data, reference);
                timeLineItem.setFromSearch(isSearch);
                timeLineItem.setFirst(first);
                timeLineItem.setLast(false);
                timeLine.onTimeLine(timeLineItem);

                if (currentRequest == TimeLine.TL_REQUESTER) {
                    timeLineItemList.add(timeLineItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        timeLineItem = TimeLineItem.fake();
        timeLineItem.setFromSearch(isSearch);
        timeLineItem.setLast(true);

        timeLine.onTimeLine(timeLineItem);
        currentRequest = TimeLine.NO_REQUEST;
    }

    private void cancelCurrentRequests(int newRequest) {
        if (newRequest != TimeLine.TL_REFERENCES) {
            this.currentRequest = newRequest;
        }

        switch (newRequest) {
            case TimeLine.TL_REFERENCES:
                cancel(tlRequester, tlSearch);
                break;
            case TimeLine.TL_REQUESTER:
                cancel(tlSearch);
                break;
            case TimeLine.TL_SEARCH:
                cancel(tlRequester);
                break;
        }
    }

    private void cancel(ApiRequester... requesters) {
        for (ApiRequester r : requesters) {
            if (r != null) {
                r.cancel(true);
            }
        }
    }

    public boolean isRunning() {
        return currentRequest != TimeLine.NO_REQUEST;
    }

}
