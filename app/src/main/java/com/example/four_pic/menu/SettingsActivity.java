package com.example.four_pic.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.four_pic.R;
import com.example.four_pic.manager.LanguageManager;
import com.example.four_pic.utils.SharedPreferencesHelper;

import java.util.Objects;


public class SettingsActivity extends AppCompatActivity {
//    Switch volume_sw;
    AppCompatButton eng;
    AppCompatButton uz;
    AppCompatButton ru;
    LanguageManager languageManager;
    Intent intent;
    ImageButton settings_to_menu;
    SharedPreferencesHelper shared;
    AppCompatButton ok;
    CheckBox easy;
    CheckBox medium;
    CheckBox hard;
    CheckBox checkBox_uzb;
    CheckBox checkBox_eng;
    CheckBox checkBox_rus;
    String difficult;
    String language;
    AppCompatImageView volume_set;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        getDifficult();
        clickCheckbocks();
        settings_to_menu.setOnClickListener(view -> {
            startActivity(intent);
            finish();
        });
        ok.setOnClickListener(view -> {
            setDifficult(difficult);
            language(language);
            startActivity(intent);
            finish();
        });
    }



    private void language(String language) {
        languageManager.updateResourse(language);
        recreate();
    }

    public void initViews(){
//        volume_sw = findViewById(R.id.volume_music);
        eng = findViewById(R.id.eng);
        uz = findViewById(R.id.uzb);
        ru = findViewById(R.id.ru);
        ok = (AppCompatButton) findViewById(R.id.settings_ok);
        easy = (CheckBox) findViewById(R.id.checkbox_easy);
        medium = (CheckBox) findViewById(R.id.checkbox_medium);
        hard = (CheckBox) findViewById(R.id.checkbox_hard);
        languageManager = new LanguageManager(SettingsActivity.this);
        intent = new Intent(getApplicationContext(), MenuActivity.class);
        settings_to_menu = findViewById(R.id.settings_to_menu);
        shared = new SharedPreferencesHelper(this);
        difficult = "EASY";
        checkBox_eng = (CheckBox) findViewById(R.id.checkbox_eng);
        checkBox_rus = (CheckBox) findViewById(R.id.checkbox_rus);
        checkBox_uzb = (CheckBox) findViewById(R.id.checkbox_uzb);
        volume_set = (AppCompatImageView) findViewById(R.id.volume_set);
    }
    void getDifficult(){
        if (Objects.equals(shared.getDifficulty(), "EASY")){
            easy.setChecked(true);
            medium.setChecked(false);
            medium.setChecked(false);
        }else if (Objects.equals(shared.getDifficulty(),"MEDIUM")){
            medium.setChecked(true);
            easy.setChecked(false);
            hard.setChecked(false);
        }else if (Objects.equals(shared.getDifficulty(),"HARD")){
            medium.setChecked(false);
            easy.setChecked(false);
            hard.setChecked(true);
        }
        if (Objects.equals(shared.getLanguage(), "ru")){
            checkBox_rus.setChecked(true);
            checkBox_uzb.setChecked(false);
            checkBox_eng.setChecked(false);
            language = "ru";
            shared.setLanguage("ru");
        }else if (Objects.equals(shared.getLanguage(), "uz")){
            checkBox_rus.setChecked(false);
            checkBox_uzb.setChecked(true);
            checkBox_eng.setChecked(false);
            language = "uz";
            shared.setLanguage("uz");
        }else if (Objects.equals(shared.getLanguage(), "eng")){
            checkBox_rus.setChecked(false);
            checkBox_uzb.setChecked(false);
            checkBox_eng.setChecked(true);
            language = "eng";
            shared.setLanguage("eng");
        }
        if (shared.getSound()){
            volume_set.setImageResource(R.drawable.ic_baseline_volume_up_24);
            shared.setSound(true);
        }else {
            volume_set.setImageResource(R.drawable.ic_baseline_volume_off_24);
            shared.setSound(false);
        }
    }
    void clickCheckbocks(){
        checkBox_rus.setOnClickListener(view -> {
            checkBox_rus.setChecked(true);
            checkBox_uzb.setChecked(false);
            checkBox_eng.setChecked(false);
            language = "ru";
        });
        checkBox_eng.setOnClickListener(view -> {
            checkBox_rus.setChecked(false);
            checkBox_uzb.setChecked(false);
            checkBox_eng.setChecked(true);
            language = "eng";
        });
        checkBox_uzb.setOnClickListener(view -> {
            checkBox_rus.setChecked(false);
            checkBox_uzb.setChecked(true);
            checkBox_eng.setChecked(false);
            language = "uz";
        });
        ru.setOnClickListener(view -> {
            checkBox_rus.setChecked(true);
            checkBox_uzb.setChecked(false);
            checkBox_eng.setChecked(false);
            language = "ru";
        });
        uz.setOnClickListener(view -> {
            checkBox_rus.setChecked(false);
            checkBox_uzb.setChecked(true);
            checkBox_eng.setChecked(false);
            language = "uz";
        });
        eng.setOnClickListener(view -> {
            checkBox_rus.setChecked(false);
            checkBox_uzb.setChecked(false);
            checkBox_eng.setChecked(true);
            language = "eng";
        });

        easy.setOnClickListener(view -> {
            easy.setChecked(true);
            medium.setChecked(false);
            hard.setChecked(false);
            difficult = "EASY";
        });
        medium.setOnClickListener(view -> {
            medium.setChecked(true);
            easy.setChecked(false);
            hard.setChecked(false);
            difficult = "MEDIUM";
        });
        hard.setOnClickListener(view -> {
            medium.setChecked(false);
            easy.setChecked(false);
            hard.setChecked(true);
            difficult = "HARD";
        });
        volume_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!shared.getSound()){
                    volume_set.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    shared.setSound(true);
                }else {
                    volume_set.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    shared.setSound(false);
                }
            }
        });
    }
    void setDifficult(String difficult){
        shared.setDifficulty(difficult);
        shared.setCoin(0);
        shared.setLevel(0);
    }


}
