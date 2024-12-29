package java.nure.labtask4;


import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.nure.labtask5.R;



public class BaseActivity extends AppCompatActivity {
    public int applyStyles(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String fontSizeStyle = sharedPreferences.getString("fontSize", "mediumFontSize");
        switch (fontSizeStyle){
            case "smallFontSize":
                setTheme(R.style.smallFontSize);
                break;
            case "mediumFontSize":
                setTheme(R.style.mediumFontSize);
                break;
            case "highFontSize":
                setTheme(R.style.largeFontSize);
                break;
        }
        int color = 0;
        String style = sharedPreferences.getString("style", "blue");
        switch (style){
            case "brown":
                setTheme(R.style.greenStyle);
                color = R.color.green;
                break;
            case "blue":
                setTheme(R.style.purpleStyle);
                color = R.color.purple;
                break;
            default:
                setTheme(R.style.purpleStyle);
                color = R.color.purple;
                break;
        }
        return color;
    }
}