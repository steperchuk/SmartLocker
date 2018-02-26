package me.andika.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sergeyteperchuk on 2/26/18.
 */

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    Animation animShake;

    String password = "";

    ImageButton x_button;
    ImageButton one_button;
    ImageButton two_button;
    ImageButton three_button;
    ImageButton four_button;
    ImageButton five_button;
    ImageButton six_button;
    ImageButton seven_button;
    ImageButton eight_button;
    ImageButton nine_button;
    ImageButton zero_button;

    TextView label;
    EditText password_field;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authentication);

        label = (TextView) findViewById(R.id.label);
        password_field = (EditText) findViewById(R.id.password_field);

        x_button = (ImageButton) findViewById(R.id.x_button);
        one_button = (ImageButton) findViewById(R.id.one_button);
        two_button = (ImageButton) findViewById(R.id.two_button);
        three_button = (ImageButton) findViewById(R.id.tree_button);
        four_button = (ImageButton) findViewById(R.id.four_button);
        five_button = (ImageButton) findViewById(R.id.five_button);
        six_button = (ImageButton) findViewById(R.id.six_button);
        seven_button = (ImageButton) findViewById(R.id.seven_button);
        eight_button = (ImageButton) findViewById(R.id.eight_button);
        nine_button = (ImageButton) findViewById(R.id.nine_button);
        zero_button = (ImageButton) findViewById(R.id.zero_button);


        x_button.setOnClickListener(AuthenticationActivity.this);
        one_button.setOnClickListener(AuthenticationActivity.this);
        two_button.setOnClickListener(AuthenticationActivity.this);
        three_button.setOnClickListener(AuthenticationActivity.this);
        four_button.setOnClickListener(AuthenticationActivity.this);
        five_button.setOnClickListener(AuthenticationActivity.this);
        six_button.setOnClickListener(AuthenticationActivity.this);
        seven_button.setOnClickListener(AuthenticationActivity.this);
        eight_button.setOnClickListener(AuthenticationActivity.this);
        nine_button.setOnClickListener(AuthenticationActivity.this);
        zero_button.setOnClickListener(AuthenticationActivity.this);

        x_button.setVisibility(View.INVISIBLE);

        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        if(getPassword() != null){
            label.setText("Введите Пароль");
        }

    }

    @Override
    public void onClick(View view) {

        x_button.setVisibility(View.VISIBLE);

        ImageButton button = (ImageButton)view;
        String buttonText = "";

        switch (button.getId()){
            case R.id.one_button:
                buttonText = "1";
                break;
            case R.id.two_button:
                buttonText = "2";
                break;
            case R.id.tree_button:
                buttonText = "3";
                break;
            case R.id.four_button:
                buttonText = "4";
                break;
            case R.id.five_button:
                buttonText = "5";
                break;
            case R.id.six_button:
                buttonText = "6";
                break;
            case R.id.seven_button:
                buttonText = "7";
                break;
            case R.id.eight_button:
                buttonText = "8";
                break;
            case R.id.nine_button:
                buttonText = "9";
                break;
            case R.id.zero_button:
                buttonText = "0";
                break;
            case R.id.x_button:
                password_field.setText("");
                break;
        }
        label.setText("");
        button.startAnimation(buttonClick);
        password_field.setText(password_field.getText() + buttonText);

        if(password_field.getText().length() == 6){
            password = password_field.getText().toString();
            if(getPassword() == null){
                savePassword(password);
                finish();
            }
            else {
                validatePassword(password);
            }
        }
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);

    private void savePassword(String password){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("password", password);
        editor.commit();
    }

    private String getPassword(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String password = sharedPref.getString("password", null);
        return password;
    }

    private void validatePassword(String password){
        if(getPassword().equals(password)){
            finish();
        }
        else {
            x_button.setVisibility(View.INVISIBLE);
            label.setText("Неверный Пароль");
            Animate();
            password_field.setText("");
        }
    }

    private void Animate(){
        label.startAnimation(animShake);
        password_field.startAnimation(animShake);
        //x_button.startAnimation(animShake);
        one_button.startAnimation(animShake);
        two_button.startAnimation(animShake);
        three_button.startAnimation(animShake);
        four_button.startAnimation(animShake);
        five_button.startAnimation(animShake);
        six_button.startAnimation(animShake);
        seven_button.startAnimation(animShake);
        eight_button.startAnimation(animShake);
        nine_button.startAnimation(animShake);
        zero_button.startAnimation(animShake);
    }

}
