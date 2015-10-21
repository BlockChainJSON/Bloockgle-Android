package anonymous.line.bloockgle.bloockgle;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by Mr.Marshall on 30/09/2015.
 */
public class FileItemAdapter extends ArrayAdapter<File> {

    private Activity activity;
    private int resource;
    private LayoutInflater inflater;
    private File[] objects;

    public FileItemAdapter(Activity activity, int resource, File[] objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public FileItemAdapter(Activity activity, int resource, List<File> listTime) {
        super(activity, resource, listTime);
        this.activity = activity;
        this.resource = resource;
        this.objects = new File[listTime.size()];
        this.objects = listTime.toArray(objects);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        final File file = getItem(position);

        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(resource, null);
            holder = new ViewHolder();

            holder.name = (TextView) view.findViewById(R.id.name);
            holder.size = (TextView) view.findViewById(R.id.size);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(file.getName());
        holder.size.setText(String.valueOf(file.length() / 1024) + " kb");

        return view;
    }

    //La clase ya es privada, así que las variables que tenemos on public solo se pueden utilizar dentro de este metodo private
    private class ViewHolder {
        public TextView name;
        public TextView size;
    }

}
