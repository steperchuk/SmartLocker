package me.sergeyteperchuk.lockscreen;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends Activity {

    ListView list;

    List<String> subjects = new ArrayList<>();
    List<Drawable> images = new ArrayList<>();
    List<Integer> answersCount = new ArrayList<>();
    List<Integer> correctAnswersCount = new ArrayList<>();
    List<String> statistics = new ArrayList<>();
    List<Integer> progress = new ArrayList<>();

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
            progress.add(getRating(correctAnswersCount.get(i), answersCount.get(i)));
        }

        StatisticsListAdapter adapter=new StatisticsListAdapter(this, subjects, images, statistics, progress);
        list.setAdapter(adapter);
    }

    private int getRating(Integer answersCount, Integer totalQuestions){
        int percent = (answersCount * 100) / totalQuestions;
        if (percent > 0 && percent< 20){return 1;}
        if (percent > 20 && percent < 40){return 2;}
        if (percent > 40 && percent < 60){return 3;}
        if (percent > 60 && percent < 80){return 4;}
        if (percent == 100){return 5;}

        return 0;
    }
}
