package com.sergeyteperchuk.lockscreen;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SubjectsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> subject;
    private final List<Drawable> image;
    private List<Boolean> state;

    public SubjectsListAdapter(Activity context, List<String> subjects, List<Drawable> image, List<Boolean> state) {
        super(context, R.layout.subject_list_item, subjects);

        this.context=context;
        this.subject = subjects;
        this.image=image;
        this.state=state;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.subject_list_item, null, true);

        TextView subjectTxt = (TextView) rowView.findViewById(R.id.subject);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        CheckBox stateChbx = (CheckBox) rowView.findViewById(R.id.state_checkbox);

        subjectTxt.setText(this.subject.get(position));
        imageView.setImageDrawable(image.get(position));
        stateChbx.setChecked((state.get(position)));
        return rowView;
    };
}