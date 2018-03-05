package me.andika.lockscreen;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SubjectsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] subject;
    private final Integer[] imgid;
    private Boolean[] state;

    public SubjectsListAdapter(Activity context, String[] subjects, Integer[] imgid, Boolean[] state) {
        super(context, R.layout.subject_list_item, subjects);

        this.context=context;
        this.subject =subjects;
        this.imgid=imgid;
        this.state=state;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.subject_list_item, null, true);

        TextView subjectTxt = (TextView) rowView.findViewById(R.id.subject);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        CheckBox stateChbx = (CheckBox) rowView.findViewById(R.id.state_checkbox);

        subjectTxt.setText(this.subject[position]);
        imageView.setImageResource(imgid[position]);
        stateChbx.setChecked((state[position]));
        return rowView;
    };
}