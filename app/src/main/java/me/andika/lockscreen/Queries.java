package me.andika.lockscreen;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergeyteperchuk on 3/6/18.
 */

public class Queries {

    Cursor userCursor;
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    Context context;

    public Queries(Context context){
        this.context = context;
    }

    public List<String> getSubjectsNames(String klass) {

        List<String> names = new ArrayList<>();
        openDBConnection();
        userCursor = db.query(klass, null, null, null, null, null, null);

        if (userCursor.moveToFirst()) {
            do {
                names.add(userCursor.getString(userCursor.getColumnIndex("Name")));
            } while (userCursor.moveToNext());
        }

        closeDBConnection();

        return names;
    }

    public List<Boolean> getSubjectsStates(String klass) {

        List<Boolean> states = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        openDBConnection();
        userCursor = db.query(klass, null, null, null, null, null, null);

        if (userCursor.moveToFirst()) {
            do {
                temp.add(userCursor.getString(userCursor.getColumnIndex("Selected")));
                Boolean state = Boolean.valueOf(userCursor.getString(userCursor.getColumnIndex("Selected")));
                states.add(state);
            } while (userCursor.moveToNext());
        }

        closeDBConnection();
        return states;
    }

    public void updateSubjectsState(String tableName ,Boolean state, int rowId){

        openDBConnection();

        ContentValues cv = new ContentValues();
        cv.put("Selected", state.toString());
        int a = db.update(tableName, cv, "Id = ?", new String[]{String.valueOf(rowId)});

        closeDBConnection();
    }

    public List<Drawable> getSubjectsImages(String klass) {

        List<Drawable> images = new ArrayList<>();
        openDBConnection();
        userCursor = db.query(klass, null, null, null, null, null, null);

        if (userCursor.moveToFirst()) {
            do {
                String imageName = userCursor.getString(userCursor.getColumnIndex("Image"));
                images.add(getDrawableResourceByName(imageName));
            } while (userCursor.moveToNext());
        }

        closeDBConnection();

        return images;
    }

    public List<String> getSubjectsTables(String klass) {

        List<String> tables = new ArrayList<>();
        openDBConnection();

        userCursor =  db.rawQuery("select Subjects from " + klass + " where Selected =? ", new String[]{"true"});

        if (userCursor.moveToFirst()) {
            do {
                tables.add(userCursor.getString(userCursor.getColumnIndex("Subjects")));
            } while (userCursor.moveToNext());
        }

        closeDBConnection();

        return tables;
    }

    public List<Question> getQuestions(String klass){
        List<String> sugbjectsTables = getSubjectsTables(klass);
        List<Question> questions = new ArrayList<>();

        openDBConnection();

        for (int i = 0; i < sugbjectsTables.size(); i++) {
            String questionsTable = sugbjectsTables.get(i);

            userCursor =  db.rawQuery("select * from " + questionsTable, null);

            if (userCursor.moveToFirst()) {
                do {
                    Question question = new Question();

                    question.Question = userCursor.getString(userCursor.getColumnIndex("Question"));
                    question.Answer_A = userCursor.getString(userCursor.getColumnIndex("Answer_A"));
                    question.Answer_B = userCursor.getString(userCursor.getColumnIndex("Answer_B"));
                    question.Answer_C = userCursor.getString(userCursor.getColumnIndex("Answer_C"));
                    question.Answer_D = userCursor.getString(userCursor.getColumnIndex("Answer_D"));
                    question.Correct_Answer = userCursor.getString(userCursor.getColumnIndex("Correct_Answer"));
                    question.Answer_Info = userCursor.getString(userCursor.getColumnIndex("Answer_Info"));
                    question.Image = userCursor.getString(userCursor.getColumnIndex("Image_URL"));

                    questions.add(question);

                } while (userCursor.moveToNext());
            }
        }

        return questions;
    }

    public Drawable getDrawableResourceByName(String name){
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        return resources.getDrawable(resourceId);
    }

    private void openDBConnection(){
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.getWritableDatabase();
        db = databaseHelper.open();
    }

    private void closeDBConnection(){
        userCursor.close();
        db.close();
    }

    public String getKlass() {
        SharedPreferences sharedPref = context.getSharedPreferences("Preferences", context.MODE_PRIVATE);
        String klass = sharedPref.getString("klass_value", null);

        return parseKlass(klass);
    }

    private String parseKlass(String klass) {
        switch (klass) {
            case "1 класс":
                return "Klass_1";
            case "2 класс":
                return "Klass_2";
            case "3 класс":
                return "Klass_3";
            case "4 класс":
                return "Klass_4";
            case "5 класс":
                return "Klass_5";
            case "6 класс":
                return "Klass_6";
            case "7 класс":
                return "Klass_7";
        }
        return null;
    }
}