package me.andika.lockscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static me.andika.lockscreen.R.color.colorAccent;


public class LockScreenActivity extends AppCompatActivity implements View.OnClickListener {

    public String correctAnswer = "2";

    TextView subject;
    TextView question;

    ImageView image;

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

        subject = (TextView) findViewById(R.id.subject_Title);
        question = (TextView) findViewById(R.id.question);

        image = (ImageView) findViewById(R.id.imageQuestion);

        answer_A_Button = (Button) findViewById(R.id.answer_A_Button);
        answer_B_Button = (Button) findViewById(R.id.answer_B_Button);
        answer_C_Button = (Button) findViewById(R.id.answer_C_Button);
        answer_D_Button = (Button) findViewById(R.id.answer_D_Button);

        result = (TextView) findViewById(R.id.answer_Result);
        resultText = (TextView) findViewById(R.id.answer_Text);

        result.setVisibility(View.INVISIBLE);
        resultText.setVisibility(View.INVISIBLE);

        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        answer_A_Button.setOnClickListener(LockScreenActivity.this);
        answer_B_Button.setOnClickListener(LockScreenActivity.this);
        answer_C_Button.setOnClickListener(LockScreenActivity.this);
        answer_D_Button.setOnClickListener(LockScreenActivity.this);
    }

    @Override
    public void onClick(View view) {

        Button button = (Button)view;
        String buttonText = button.getText().toString();

        if(buttonText.equals(correctAnswer)){
            finish();
        }
        else
        {
            result.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);
            button.startAnimation(animShake);
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
}
