package me.andika.lockscreen;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import me.andika.lockscreen.utils.LockScreen;

import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE;

public class SubjectsActivity extends Activity {

    ListView list;
    CheckBox stateCheckbox;
    ImageButton saveButton;

    String[] subjects ={
            "Математика",
            "Логика",
            "Английский"
    };

    Integer[] imgid={R.drawable.ic_lock,R.drawable.ic_lock,R.drawable.ic_lock};
    Boolean[] states={false, true, false};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        saveButton = (ImageButton)findViewById(R.id.save_button);

        list = (ListView) findViewById(R.id.subjects_list);
        stateCheckbox = (CheckBox) findViewById(R.id.state_checkbox);

        CustomListAdapter adapter=new CustomListAdapter(this, subjects, imgid, states);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= subjects[+position];
                CheckBox stateCheckbox = (CheckBox)view.findViewById(R.id.state_checkbox);
                stateCheckbox.setChecked(!stateCheckbox.isChecked());
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });

    }




}
