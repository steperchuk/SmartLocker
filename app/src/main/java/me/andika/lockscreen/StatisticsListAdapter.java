package me.andika.lockscreen;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StatisticsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> subject;
    private final List<Drawable> image;
    private final List<String> statistics;

    public StatisticsListAdapter(Activity context, List<String> subjects, List<Drawable> image, List<String> statistics) {
        super(context, R.layout.statistics_list_item, subjects);

        this.context = context;
        this.subject = subjects;
        this.image = image;
        this.statistics = statistics;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.statistics_list_item, null, true);

        TextView subjectTxt = (TextView) rowView.findViewById(R.id.subject);
        TextView statisticsTxt = (TextView) rowView.findViewById(R.id.statistic);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        subjectTxt.setText(this.subject.get(position));
        statisticsTxt.setText(this.statistics.get(position));
        imageView.setImageDrawable(this.image.get(position));
        return rowView;
    };
}