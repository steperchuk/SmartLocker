package me.andika.lockscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class LockScreenActivity extends AppCompatActivity implements View.OnClickListener {

    String correctAnswer;
    TextView questionLabel;
    ImageView image;

    List<Question> questions;
    Question question;

    Queries queries;

    Button answer_A_Button;
    Button answer_B_Button;
    Button answer_C_Button;
    Button answer_D_Button;

    TextView result;
    TextView resultText;

    Animation animShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        queries = new Queries(getApplicationContext());
        String klass = queries.getKlass();
        questions = queries.getQuestions(klass);
        question = getRandomQuestion(questions);

        questionLabel = (TextView) findViewById(R.id.question);
        questionLabel.setText(question.Question);

        image = (ImageView) findViewById(R.id.imageQuestion);

        answer_A_Button = (Button) findViewById(R.id.answer_A_Button);
        answer_B_Button = (Button) findViewById(R.id.answer_B_Button);
        answer_C_Button = (Button) findViewById(R.id.answer_C_Button);
        answer_D_Button = (Button) findViewById(R.id.answer_D_Button);
        answer_A_Button.setText(question.Answer_A.trim());
        answer_B_Button.setText(question.Answer_B.trim());
        answer_C_Button.setText(question.Answer_C.trim());
        answer_D_Button.setText(question.Answer_D.trim());

        result = (TextView) findViewById(R.id.answer_Result);
        resultText = (TextView) findViewById(R.id.answer_Text);
        resultText.setText(question.Answer_Info);

        result.setVisibility(View.INVISIBLE);
        resultText.setVisibility(View.INVISIBLE);

        correctAnswer = question.Correct_Answer;

        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        answer_A_Button.setOnClickListener(LockScreenActivity.this);
        answer_B_Button.setOnClickListener(LockScreenActivity.this);
        answer_C_Button.setOnClickListener(LockScreenActivity.this);
        answer_D_Button.setOnClickListener(LockScreenActivity.this);

        // save 1 min interval
        Queries.saveTempInterval(getApplicationContext(), 1);

    }

    @Override
    public void onClick(View view) {

        Button button = (Button)view;
        String buttonText = button.getText().toString();

        if(buttonText.equals(correctAnswer.trim())){
            //queries.updateAnswerState(question.Table, true, question.Question);

            // save selected in settings interval
            queries.saveTempInterval(getApplicationContext(), queries.getIntervalValue(getApplicationContext()));

            finish();
        }
        else
        {
            // save 1 min interval
            Queries.saveTempInterval(getApplicationContext(), 1);

            result.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);
            button.startAnimation(animShake);

            setNextQuestion();
        }
    }


    @Override
    public void onAttachedToWindow() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                //WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onAttachedToWindow();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((LockApplication) getApplication()).lockScreenShow = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((LockApplication) getApplication()).lockScreenShow = false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }


    private Question getRandomQuestion(List<Question> questions){

        Question question = questions.get((new Random()).nextInt(questions.size()));

        return question;
    }

    private void setNextQuestion(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 10 seconds
                updateUI();
            }
        }, 10000);


    }

    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                question = getRandomQuestion(questions);
                questionLabel.setText(question.Question);
                answer_A_Button.setText(question.Answer_A.trim());
                answer_B_Button.setText(question.Answer_B.trim());
                answer_C_Button.setText(question.Answer_C.trim());
                answer_D_Button.setText(question.Answer_D.trim());

                resultText.setText(question.Answer_Info);
                correctAnswer = question.Correct_Answer.trim();

                result.setVisibility(View.INVISIBLE);
                resultText.setVisibility(View.INVISIBLE);
            }
        });
    }
}
