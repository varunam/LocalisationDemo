package app.localisationdemo.com.localisationdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    
    private Button englishButton, hindiButton, teluguButton, tamilButton, nextScreenButton;
    private TextView textToDisplay;
    private Locale appLocale;
    private String currentLanguage;
    private String CURRENT_LANG_KEY = "current-lang";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Log.e(TAG, getLang());
        updateLocale();
        
        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_LANG_KEY))
            currentLanguage = savedInstanceState.getString(CURRENT_LANG_KEY);
        
        englishButton = findViewById(R.id.button);
        hindiButton = findViewById(R.id.button2);
        teluguButton = findViewById(R.id.button3);
        tamilButton = findViewById(R.id.button4);
        textToDisplay = findViewById(R.id.textToDisplayID);
        nextScreenButton = findViewById(R.id.next_screen_button_id);
        
        englishButton.setOnClickListener(this);
        hindiButton.setOnClickListener(this);
        teluguButton.setOnClickListener(this);
        tamilButton.setOnClickListener(this);
        nextScreenButton.setOnClickListener(this);
        appLocale = new Locale(getLang());
        currentLanguage = appLocale.getLanguage();
        textToDisplay.setText(getResources().getString(R.string.learn_kannada));
        nextScreenButton.setText(getResources().getString(R.string.go_to_next_screen));
        
    }
    
   public void updateLocale(){
       Locale locale = new Locale(getLang());
       Resources resources = getResources();
       DisplayMetrics dm = resources.getDisplayMetrics();
       Configuration configuration = resources.getConfiguration();
       configuration.setLocale(locale);
       resources.updateConfiguration(configuration, dm);
       Log.e(TAG, "Updated locale config");
   }
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Toast.makeText(getApplicationContext(), appLocale.getLanguage(), Toast.LENGTH_LONG).show();
                setLocale("en");
                break;
            case R.id.button2:
                Toast.makeText(getApplicationContext(), appLocale.getLanguage(), Toast.LENGTH_LONG).show();
                setLocale("hi");
                break;
            case R.id.button3:
                Toast.makeText(getApplicationContext(), appLocale.getLanguage(), Toast.LENGTH_LONG).show();
                setLocale("te");
                break;
            case R.id.button4:
                Toast.makeText(getApplicationContext(), appLocale.getLanguage(), Toast.LENGTH_LONG).show();
                setLocale("ta");
                break;
            case R.id.next_screen_button_id:
                startActivity(new Intent(this, SecondActivity.class));
        }
    }
    
    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            appLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = appLocale;
            saveLang(appLocale);
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, MainActivity.class);
            refresh.putExtra(CURRENT_LANG_KEY, localeName);
            startActivity(refresh);
        } else
            Toast.makeText(getApplicationContext(), "Already selected", Toast.LENGTH_LONG).show();
    }
    
    public void saveLang(Locale locale) {
        SharedPreferences sharedPreferences = getSharedPreferences("lang", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected-lang", locale.getLanguage());
        editor.apply();
    }
    
    public String getLang() {
        return getSharedPreferences("lang", MODE_PRIVATE).getString("selected-lang", "en");
    }
}
