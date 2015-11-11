package anonymous.line.bloockgle.timeline;

/**
 * Created by ander on 10/11/15.
 */
public interface TimeLine {

    void onTimeLine(TimeLineItem timeLineItem, boolean first, boolean last);
    void onError(String errorMessage);
    void more();
    boolean isLoading();
}
