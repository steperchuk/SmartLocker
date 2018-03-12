package me.andika.lockscreen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SubjectsActivity extends Activity {

    ListView list;
    CheckBox stateCheckbox;

    List<String> subjects = new ArrayList<>();
    List<Drawable> images = new ArrayList<>();
    List<Boolean> states = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        final Queries queries = new Queries(getApplicationContext());
        final String klass = queries.getKlass();
        subjects = queries.getSubjectsNames(klass);
        states = queries.getSubjectsStates(klass);
        images = queries.getSubjectsImages(klass);


        list = (ListView) findViewById(R.id.subjects_list);
        stateCheckbox = (CheckBox) findViewById(R.id.state_checkbox);

        SubjectsListAdapter adapter = new SubjectsListAdapter(this, subjects, images, states);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CheckBox stateCheckbox = (CheckBox) view.findViewById(R.id.state_checkbox);
                Boolean state = stateCheckbox.isChecked();
                stateCheckbox.setChecked(!state);
                queries.updateSubjectsState(klass, !state, position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Queries queries = new Queries(getApplicationContext());
        int selectedItemsCount = queries.getSelectedSubjectsNames(queries.getKlass()).size();
        if(selectedItemsCount < 1){
            Toast.makeText(getApplicationContext(), "Предметы не выбраны", Toast.LENGTH_SHORT).show();
        }
        else {
            finish();
        }
    }
}
