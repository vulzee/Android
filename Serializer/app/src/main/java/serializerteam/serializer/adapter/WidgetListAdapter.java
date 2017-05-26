package serializerteam.serializer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import serializerteam.serializer.R;
import serializerteam.serializer.dto.ShowDto;

/**
 * Created by xxx on 2017-05-26.
 */

public class WidgetListAdapter  extends ArrayAdapter<String> {
    private final Context context;
    //private final ShowDto[] showTitles;
    private final String[] values;

    public WidgetListAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;//(ShowDto[])values.keySet().toArray();
        //this.episodesDate = (String[])values.values().toArray();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.widget_next_show_item, parent, false);
        TextView showTitle = (TextView) rowView.findViewById(R.id.appwidget_text);
        TextView episodeDate = (TextView) rowView.findViewById(R.id.widget_next_episode);
        showTitle.setText(this.values[position]);
        episodeDate.setText(this.values[position+values.length/2]);
//        // change the icon for Windows and iPhone
//        String s = values[position];
//        if (s.startsWith("iPhone")) {
//            imageView.setImageResource(R.drawable.no);
//        } else {
//            imageView.setImageResource(R.drawable.ok);
//        }

        return rowView;
    }
}