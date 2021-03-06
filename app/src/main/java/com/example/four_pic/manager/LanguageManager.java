package com.example.four_pic.manager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    private Context ct;

    public LanguageManager(Context ct) {
        this.ct = ct;
    }

    public void updateResourse(String code){
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = ct.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale=locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
