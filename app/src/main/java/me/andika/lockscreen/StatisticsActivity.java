package me.andika.lockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class StatisticsActivity extends Activity {

    ListView list;

    String[] subjects =
    {
            "Математика",
            "Логика",
            "Английский"
    };

    Integer[] imgid= {R.drawable.ic_lock,R.drawable.ic_lock,R.drawable.ic_lock};

    String[] statistics = {"12/157", "2/34", "3/56"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        list = (ListView) findViewById(R.id.statistics_list);

        StatisticsListAdapter adapter=new StatisticsListAdapter(this, subjects, imgid, statistics);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String Slecteditem= subjects[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });

    }





}
