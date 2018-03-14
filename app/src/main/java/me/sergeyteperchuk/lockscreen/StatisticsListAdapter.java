package me.sergeyteperchuk.lockscreen;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class StatisticsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> subject;
    private final List<Drawable> image;
    private final List<String> statistics;
    private final List<Integer> progress;

    public StatisticsListAdapter(Activity context, List<String> subjects, List<Drawable> image, List<String> statistics, List<Integer> progress) {
        super(context, R.layout.statistics_list_item, subjects);

        this.context = context;
        this.subject = subjects;
        this.image = image;
        this.statistics = statistics;
        this.progress = progress;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.statistics_list_item, null, true);

        TextView subjectTxt = (TextView) rowView.findViewById(R.id.subject);
        TextView statisticsTxt = (TextView) rowView.findViewById(R.id.statistic);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.myRatingBar);
        ratingBar.setMax(5);

        subjectTxt.setText(this.subject.get(position));
        statisticsTxt.setText(this.statistics.get(position));
        imageView.setImageDrawable(this.image.get(position));
        ratingBar.setProgress(this.progress.get(position));
        return rowView;
    };
}