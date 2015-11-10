package anonymous.line.bloockgle.timeline;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;

import java.util.List;
import java.util.Set;

import anonymous.line.bloockgle.view.DetailPanel;
import anonymous.line.bloockgle.R;

/**
 * Created by Mr.Marshall on 30/09/2015.
 */
public class TimeLineAdapter extends ArrayAdapter<TimeLineItem> {

    private static final String TAG = "TimeLineAdapter";
    private int resource;
    private Activity activity;
    private LayoutInflater inflater;
    private TimeLineItem[] objects;
    private TimeLine timeLine;

    public TimeLineAdapter(Activity activity, int resource, List<TimeLineItem> listTime, TimeLine timeLine) {
        super(activity, resource, listTime);
        this.activity = activity;
        this.resource = resource;
        this.objects = new TimeLineItem[listTime.size()];
        this.objects = listTime.toArray(objects);
        this.timeLine = timeLine;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TimeLineItem getItem(int position) {
        return objects[position];
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){

        if (position < getCount() -1) {
            final TimeLineItem timeLineItem = getItem(position);

            ViewHolder holder;

/*        if (view == null) {*/
            view = inflater.inflate(resource, null);
            holder = new ViewHolder();

            holder.title = (TextView) view.findViewById(R.id.title);
            holder.detailPanel = (DetailPanel) view.findViewById(R.id.detailPanel);

            view.setTag(holder);

/*        } else {
            holder = (ViewHolder) view.getTag();
        }*/

            holder.title.setText(timeLineItem.getTitle());
            Set<String> keys = timeLineItem.getContent().keySet();

            for (String key: keys){
                String value = timeLineItem.get(key);
                TextView t = holder.detailPanel.addDetail(key + ": ", value);
                if (key.equals("torrent_link") || key.equals("link")) {
                    holder.detailPanel.setOpennableData(activity, t, value);

                    if (timeLineItem.hasTorrentLink()) {
                        t.setTextColor(getContext().getResources().getColor(R.color.text_effect_torrent));
                    }

                    if (timeLineItem.hasWebLink()) {
                        t.setTextColor(getContext().getResources().getColor(R.color.text_effect_link));
                    }
                } else if (key.equals("address")) {
                    t.setTextColor(getContext().getResources().getColor(R.color.text_effect_address));
                    holder.detailPanel.setOpennableData(activity, t, timeLineItem.getBitcoinUri().toString());
                }
            }
        } else {
            view = inflater.inflate(R.layout.time_line_more, null);
            final View moreLayout = view.findViewById(R.id.more_layout);
            final View loaderLayout = view.findViewById(R.id.loader_layout);

            ProgressBar loaderView = (ProgressBar) view.findViewById(R.id.loader);
            Drawable circleDrawable = new FoldingCirclesDrawable.Builder(getContext()).colors(getCircleColors()).build();
            loaderView.setIndeterminateDrawable(circleDrawable);

            if (timeLine.isLoading()) {
                moreLayout.setVisibility(View.GONE);
                loaderLayout.setVisibility(View.VISIBLE);
            }
            moreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moreLayout.setVisibility(View.GONE);
                    loaderLayout.setVisibility(View.VISIBLE);
                    timeLine.addPage(timeLine.currentPage()+1);
                }
            });
        }


        return view;
    }

    private int[] getCircleColors() {
        return new int[] {getContext().getResources().getColor(R.color.violet_light3),
                getContext().getResources().getColor(R.color.violet_light2),
                getContext().getResources().getColor(R.color.violet_light),
                getContext().getResources().getColor(R.color.violet)};
    }

    //La clase ya es privada, así que las variables que tenemos on public solo se pueden utilizar dentro de este metodo private
    private class ViewHolder {
        public TextView title;
        public DetailPanel detailPanel;
    }


}
