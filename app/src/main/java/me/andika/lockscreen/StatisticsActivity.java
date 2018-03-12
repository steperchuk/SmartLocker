package me.andika.lockscreen;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends Activity {

    ListView list;

    List<String> subjects = new ArrayList<>();
    List<Drawable> images = new ArrayList<>();
    List<Integer> answersCount = new ArrayList<>();
    List<Integer> correctAnswersCount = new ArrayList<>();
    List<String> statistics = new ArrayList<>(); //{"12 из 157", "2 из 34", "3 из 56"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        list = (ListView) findViewById(R.id.statistics_list);

        final Queries queries = new Queries(getApplicationContext());
        final String klass = queries.getKlass();
        subjects = queries.getSelectedSubjectsNames(klass);
        images = queries.getSelectedSubjectsImages(klass);
        answersCount = queries.getSubjectAnswersCount(klass);
        correctAnswersCount = queries.getSubjectCorrectAnswersCount(klass);

        for(int i = 0 ; i < subjects.size(); i++){
            statistics.add(correctAnswersCount.get(i) + " из " + answersCount.get(i));
        }

        StatisticsListAdapter adapter=new StatisticsListAdapter(this, subjects, images, statistics);
        list.setAdapter(adapter);

    }
}
