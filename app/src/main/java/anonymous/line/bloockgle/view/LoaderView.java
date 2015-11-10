package anonymous.line.bloockgle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import anonymous.line.bloockgle.R;

/**
 * Created by ander on 10/11/15.
 */
public class LoaderView extends ImageView {

    public LoaderView(Context context) {
        super(context);
        initialize();
    }

    public LoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        setImageResource(0);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(R.drawable.loading);
    }
}
