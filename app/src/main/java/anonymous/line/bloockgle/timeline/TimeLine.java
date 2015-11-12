package anonymous.line.bloockgle.timeline;

/**
 * Created by ander on 10/11/15.
 */
public interface TimeLine {

    static final int NO_REQUEST = -1;
    static final int TL_REQUESTER = 0;
    static final int TL_SEARCH = 1;
    static final int TL_REFERENCES = 2;
    void onTimeLine(TimeLineItem timeLineItemt);
    void onError(String errorMessage);
    void more(int request);
    boolean isLoading();
}
