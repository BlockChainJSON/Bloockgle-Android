package anonymous.line.bloockgle.bloockgle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by Mr.Marshall on 30/09/2015.
 */
public class TimeLineAdapter extends ArrayAdapter<TimeLineItem> {

    private Activity activity;
    private int resource;
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
    public View getView(int position, View view, ViewGroup viewGroup){
        final TimeLineItem timeLineItem = getItem(position);

        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(resource, null);
            holder = new ViewHolder();

            holder.title = (TextView) view.findViewById(R.id.title);
            holder.detailPanel = (DetailPanel) view.findViewById(R.id.detailPanel);
//            holder.text = (TextView) view.findViewById(R.id.text_content);
//            holder.address = (TextView) view.findViewById(R.id.address);
//            holder.imageView = (ImageView) view.findViewById(R.id.imageView);
//            holder.share = view.findViewById(R.id.share);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(timeLineItem.getTitle());
        Set<String> keys = timeLineItem.getContent().keySet();
        for (String key: keys){
            Log.e("TmeLineAdapter", key + ": " + timeLineItem.getContent().get(key));
            holder.detailPanel.addDetail(key + ": ", timeLineItem.getContent().get(key));
        }
//        holder.address.setText(timeLineItem.getAddress());
//        holder.address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String uri = "bitcoin://" + timeLineItem.getAddress();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(uri));
//                activity.startActivity(intent);
//            }
//        });
//        holder.text.setText(timeLineItem.getFile());
//        final String[] contentType = timeLineItem.getFile().split(":");
//
//        final FileType fileType = FileType.getFileType(contentType[0]);
//        final File otherFile = Utils.decodeFile(contentType[1], activity);
//        switch (fileType) {
//            case TXT:
//                holder.text.setText(contentType[1]);
//                holder.text.setVisibility(View.VISIBLE);
//                break;
//
//            case LINK:
//               final String enlace = contentType[1] + ":" + contentType[2];
//                holder.text.setText(enlace);
//                holder.text.setVisibility(View.VISIBLE);
//                holder.text.setMovementMethod(LinkMovementMethod.getInstance());
//                holder.text.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(enlace));
//                        activity.startActivity(intent);
//                    }
//                });
//                break;
//
//            case IMAGE:
//                holder.imageView.setImageDrawable(Utils.GetBitmapDrawable(contentType[1]));
//                holder.imageView.setVisibility(View.VISIBLE);
//                holder.text.setVisibility(View.GONE);
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(otherFile), "image/*");
//                        activity.startActivity(intent);
//                    }
//                });
//                break;
//
//            case AUDIO:
//                holder.text.setText(otherFile.getName());
//                holder.text.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(otherFile), "audio/*");
//                        activity.startActivity(intent);
//                    }
//                });
//
//                break;
//
//            case VIDEO:
//                holder.text.setText(otherFile.getName());
//                holder.text.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(otherFile), "video/*");
//                        activity.startActivity(intent);
//                    }
//                });
//
//                break;
//
//            case OTHER:
//                holder.text.setText(otherFile.getName());
//                holder.text.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(otherFile), "*/*");
//                        activity.startActivity(intent);
//                    }
//                });
//        }
//        holder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sharingIntent = null;
//                switch (fileType){
//                    case LINK:
//                        sharingIntent = new Intent(Intent.ACTION_SEND);
//                        sharingIntent.setType("text/plain");
//                        sharingIntent.putExtra(Intent.EXTRA_TEXT, contentType[1] + ":" + contentType[2]);
//                        break;
//                    case TXT:
//                        sharingIntent = new Intent(Intent.ACTION_SEND);
//                        sharingIntent.setType("text/plain");
//                        sharingIntent.putExtra(Intent.EXTRA_TEXT, contentType[1]);
//                        break;
//                        default:
//                            sharingIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
//                            sharingIntent.setType("*/*");
//                            Uri screenshotUri = Uri.parse("file://" + otherFile.getAbsolutePath());
//                            sharingIntent.putExtra(Intent.EXTRA_TEXT, "shdfhasasdasdasdasdadas");
//                            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
//
//                }
//
//                activity.startActivity(Intent.createChooser(sharingIntent, "Share with..."));
//            }
//
//
//
//        });

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
