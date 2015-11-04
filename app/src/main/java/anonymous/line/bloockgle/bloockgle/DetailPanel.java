package anonymous.line.bloockgle.bloockgle;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ander on 2/10/15.
 */
public class DetailPanel extends LinearLayout {

    private static final String TAG = "DetailPanel";

    public interface Pair<Key extends CharSequence, Value extends CharSequence> {
        Key getKey();
        Value getValue();
    }

    public DetailPanel(Context context) {
        super(context);
        initialize();
    }

    public DetailPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
    }

    private int getPixels(float dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public TextView addDetail(CharSequence detail, CharSequence value, boolean singleLine) {
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(HORIZONTAL);
        if (!singleLine) {
            container.setOrientation(VERTICAL);
        }
        container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 0.1f));
        container.setGravity(Gravity.CENTER_VERTICAL);
        container.setPadding(getPixels(20), getPixels(5), getPixels(20), getPixels(5));

        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        DetailTextView detailView = new DetailTextView(getContext());
        detailView.setTypeface(detailView.getTypeface(), 0);
        detailView.setLayoutParams(textParams);
        detailView.setText(detail);
        ValueTextView valueView = new ValueTextView(getContext());
        valueView.setLayoutParams(textParams);
        valueView.setText(value);

        if (singleLine) {
            valueView.setPadding(getPixels(4), 0, 0, 0);
        }

        container.addView(detailView);
        container.addView(valueView);

        addView(container);

        return valueView;
    }

    public void setOpennableData(final Activity activity, TextView textView, final String data) {
        textView.setSingleLine(true);
        textView.setMaxLines(1);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                launchIntent.setData(Uri.parse(data));
                try {
                    activity.startActivity(launchIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity, "App not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public TextView addDetail(CharSequence detail, CharSequence value) {
        return addDetail(detail, value, true);
    }

    public TextView addDetail(Pair<? extends CharSequence, ? extends CharSequence> pair) {
        return addDetail(pair.getKey(), pair.getValue());
    }

    @SafeVarargs
    public final void addDetails(Pair<CharSequence, CharSequence>... pairs) {
        for (Pair<CharSequence, CharSequence> p : pairs) {
            addDetail(p);
        }
    }

    public void addDetails(List<Pair<CharSequence, CharSequence>> pairList) {
        for (Pair<CharSequence, CharSequence> p : pairList) {
            addDetail(p);
        }
    }

    private class ValueTextView extends TextView {

        public ValueTextView(Context context) {
            super(context);
        }

        public ValueTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

    }

    private class DetailTextView extends TextView {

        public DetailTextView(Context context) {
            super(context);
        }

        public DetailTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setTypeface(Typeface tf, int style) {
            super.setTypeface(tf, Typeface.BOLD);
        }
    }
}
