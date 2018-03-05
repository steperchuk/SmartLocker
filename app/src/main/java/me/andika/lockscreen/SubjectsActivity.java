package me.andika.lockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SubjectsActivity extends Activity {

    ListView list;
    CheckBox stateCheckbox;
    ImageButton saveButton;

    String[] subjects =
    {
            "Математика",
            "Логика",
            "Английский"
    };

    Integer[] imgid= {R.drawable.ic_lock,R.drawable.ic_lock,R.drawable.ic_lock};
    Boolean[] states= {false, true, false};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        saveButton = (ImageButton)findViewById(R.id.save_button);
        View.OnClickListener saveButtonOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save settings (states array)
            }
        };
        saveButton.setOnClickListener(saveButtonOnClick);

        list = (ListView) findViewById(R.id.subjects_list);
        stateCheckbox = (CheckBox) findViewById(R.id.state_checkbox);

        SubjectsListAdapter adapter=new SubjectsListAdapter(this, subjects, imgid, states);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String Slecteditem= subjects[+position];
                CheckBox stateCheckbox = (CheckBox)view.findViewById(R.id.state_checkbox);
                stateCheckbox.setChecked(!stateCheckbox.isChecked());
                states[position] = stateCheckbox.isChecked();
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });

    }





}
