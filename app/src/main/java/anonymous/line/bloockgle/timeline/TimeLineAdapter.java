package anonymous.line.bloockgle.timeline;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import anonymous.line.bloockgle.view.DetailPanel;
import anonymous.line.bloockgle.bloockgle.R;

/**
 * Created by Mr.Marshall on 30/09/2015.
 */
public class TimeLineAdapter extends ArrayAdapter<TimeLineItem> {

    private static final String TAG = "TimeLineAdapter";
    private int resource;
    private Activity activity;
    private LayoutInflater inflater;
    private TimeLineItem[] objects;

    public TimeLineAdapter(Activity activity, int resource, TimeLineItem[] objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public TimeLineAdapter(Activity activity, int resource, List<TimeLineItem> listTime) {
        super(activity, resource, listTime);
        this.activity = activity;
        this.resource = resource;
        this.objects = new TimeLineItem[listTime.size()];
        this.objects = listTime.toArray(objects);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TimeLineItem getItem(int position) {
        return objects[position];
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
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
        Log.e(TAG, "Setting details");

        for (String key: keys){
            String value = timeLineItem.get(key);
            Log.e(TAG, "key: " + key + ", value: " + value);
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

        return view;
    }

    //La clase ya es privada, así que las variables que tenemos on public solo se pueden utilizar dentro de este metodo private
    private class ViewHolder {
        public TextView title;
        public DetailPanel detailPanel;
//        public TextView text;
//        public TextView address;
//        public ImageView imageView;
//        public View share;
    }

}
