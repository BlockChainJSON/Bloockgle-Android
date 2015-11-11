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

    private static final int TL_REQUESTER = 0;
    private static final int TL_SEARCH = 1;
    private static final int TL_REFERENCES = 2;

    private ApiRequester tlRequester;
    private ApiRequester tlSearch;
    private ApiRequester tlReferences;
    private TimeLine timeLine;
    private int currentRequest = -1;
    private int currentPage;
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
                getData(jsonObject);
            }

            @Override
            public void onErrorResponse(int code, String errorResponse) {
                if (code == EMPTY_RESPONSE_CODE) {
                    timeLine.onError(errorResponse);

                }
                page(page);

            }
        });
        cancelCurrentRequests(TL_REQUESTER);
        tlRequester.execute();

    }

    public void newPage() {
        page(currentPage++);
    }

    public List<TimeLineItem> getTimeLineItemList() {
        return timeLineItemList;
    }

    public int currentPage() {
        return currentPage;
    }

    public boolean isNormalRequest() {
        return currentRequest == TL_REQUESTER;
    }

    public void search(final String word) {
        tlSearch = new ApiRequester(new GetTimeLineRequest("findword", word), new ErrorHandler() {
            @Override
            public void onOkResponse(JSONObject jsonObject) throws JSONException {
                getData(jsonObject);
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
        cancelCurrentRequests(TL_SEARCH);
        tlSearch.execute();
    }

    public void getData(final JSONObject jsonObject) throws JSONException {

        final JSONArray content = jsonObject.getJSONArray("content");
        Log.e("Item", content.length() + "");
        for (int x = 0; x < content.length(); x++) {

            JSONObject item = content.getJSONObject(x);
            final String reference = item.getString("ref");

            JSONObject data;
            try {
                data = new JSONObject(item.getString("content"));
            } catch (Exception e) {
                continue;
            }
            TimeLineItem timeLineItem = new TimeLineItem(data, reference);

            timeLine.onTimeLine(timeLineItem, (x == 0), (x == content.length() - 1));

            if (currentRequest == TL_REQUESTER) {
                timeLineItemList.add(timeLineItem);
            }
        }
    }

    private void cancelCurrentRequests(int newRequest) {
        if (newRequest != TL_REFERENCES) {
            this.currentRequest = newRequest;
        }

        switch (newRequest) {
            case TL_REFERENCES:
                cancel(tlRequester, tlSearch);
                break;
            case TL_REQUESTER:
                cancel(tlSearch, tlReferences);
                break;
            case TL_SEARCH:
                cancel(tlRequester, tlReferences);
                break;
        }
    }

    private void cancel(ApiRequester... requesters) {
        for (ApiRequester r : requesters) {
            if (r != null) {
                r.cancel(false);
            }
        }
    }

}
