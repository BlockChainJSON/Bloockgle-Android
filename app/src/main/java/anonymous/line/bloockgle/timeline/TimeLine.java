package anonymous.line.bloockgle.timeline;

/**
 * Created by ander on 10/11/15.
 */
public interface TimeLine {

    void addPage(int page);
    int currentPage();
    boolean isLoading();
}
