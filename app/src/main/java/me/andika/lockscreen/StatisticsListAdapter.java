package me.andika.lockscreen;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class StatisticsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] subject;
    private final String[] statistics;
    private final Integer[] imgid;

    public StatisticsListAdapter(Activity context, String[] subjects, Integer[] imgid, String[] statistics) {
        super(context, R.layout.statistics_list_item, subjects);

        this.context=context;
        this.subject =subjects;
        this.imgid=imgid;
        this.statistics =statistics;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.statistics_list_item, null, true);

        TextView subjectTxt = (TextView) rowView.findViewById(R.id.subject);
        TextView statisticsTxt = (TextView) rowView.findViewById(R.id.statistic);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        subjectTxt.setText(this.subject[position]);
        statisticsTxt.setText(this.statistics[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;
    };
}