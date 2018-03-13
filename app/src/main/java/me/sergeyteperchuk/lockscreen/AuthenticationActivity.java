package me.sergeyteperchuk.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by sergeyteperchuk on 2/26/18.
 */

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    Animation animShake;

    String password = "";

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

    RadioButton button_1;
    RadioButton button_2;
    RadioButton button_3;
    RadioButton button_4;
    RadioButton button_5;
    RadioButton button_6;

    TextView label;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authentication);

        label = (TextView) findViewById(R.id.label);

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

        button_1 = (RadioButton) findViewById(R.id.radioButton_1);
        button_2 = (RadioButton) findViewById(R.id.radioButton_2);
        button_3 = (RadioButton) findViewById(R.id.radioButton_3);
        button_4 = (RadioButton) findViewById(R.id.radioButton_4);
        button_5 = (RadioButton) findViewById(R.id.radioButton_5);
        button_6 = (RadioButton) findViewById(R.id.radioButton_6);


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


        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        if(getPassword() != null){
            label.setText("Введите Пароль");
        }
        else {
            label.setText("Укажите родительский пароль");
        }

        skipRadioButtonsStates();
    }

    @Override
    public void onClick(View view) {

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
        }

        label.setText("");
        button.startAnimation(buttonClick);
        password = password + buttonText;
        int passwordLenght = password.length();

        setRadioButtons(password.length());
        if(passwordLenght == 6){
            if(getPassword() == null){
                savePassword(password);
                showSettings();
            }
            else {
                validatePassword(password);
            }
        }
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);

    private void savePassword(String password){
        SharedPreferences sharedPref = getSharedPreferences("PasswordPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("password", password);
        editor.commit();
    }

    private String getPassword(){
        SharedPreferences sharedPref = getSharedPreferences("PasswordPreferences",Context.MODE_PRIVATE);
        String password = sharedPref.getString("password", null);
        return password;
    }

    private void validatePassword(String value){
        if(getPassword().equals(value)){
            finish();
            showSettings();
        }
        else {
            label.setText("Неверный Пароль");
            Animate();
            password = "";
            skipRadioButtonsStates();
        }
    }

    private void Animate(){
        label.startAnimation(animShake);
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

    private void setRadioButtons(int passLength){
        switch (passLength){
            case 0:
                skipRadioButtonsStates();
                break;
            case 1:
                button_1.setChecked(true);
                break;
            case 2:
                button_2.setChecked(true);
                break;
            case 3:
                button_3.setChecked(true);
                break;
            case 4:
                button_4.setChecked(true);
                break;
            case 5:
                button_5.setChecked(true);
                break;
            case 6:
                button_6.setChecked(true);
                break;
        }
    }

    private void skipRadioButtonsStates(){
        button_1.setChecked(false);
        button_2.setChecked(false);
        button_3.setChecked(false);
        button_4.setChecked(false);
        button_5.setChecked(false);
        button_6.setChecked(false);
    }

    private void showSettings(){
        Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
        settings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(settings);
        finish();
    }


}
