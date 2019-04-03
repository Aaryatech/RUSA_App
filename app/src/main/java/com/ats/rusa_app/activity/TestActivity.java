package com.ats.rusa_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ats.rusa_app.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        String temp = "<p><iframe allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen=\"\" frameborder=\"0\" height=\"250\" src=\"https://www.youtube.com/embed/awwz92pVeXc\" width=\"275\"></iframe></p>\n" +
                "\n" +
                "<p><iframe allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen=\"\" frameborder=\"0\" height=\"250\" src=\"https://www.youtube.com/embed/awwz92pVeXc\" width=\"275\"></iframe></p>\n";

        Log.e("OLD STR ", "--------------------------" + temp);


        Log.e("NEW STR ", "--------------------------" + temp.replace("\\", ""));

        Log.e("SRC LOC ", "-------------------------- " + temp.lastIndexOf("src"));

        Log.e("STR AT 147 ", "-------------------------- " + temp.substring(147));

        //    Log.e("GET INDEX ","--------------------------- "+temp.indexOf('"',147));
        //    Log.e("GET LAST INDEX ","--------------------------- "+temp.indexOf('"',(temp.indexOf('"',147)+1)));

        //    Log.e("YOUTUBE LINK - ","--------------------------- "+temp.substring(152,195).replace("\\",""));

        //    Log.e("CODE ","-------------------------- "+extractYTId(temp.substring(153,195)));

        // StringBuilder str=new StringBuilder(extractYTId(temp.substring(153,195)));

        //   Log.e("OUTPUT ","--------------------------- "+extractYTId(temp.substring(153,195)).replace("\\",""));

        //https://www.youtube.com/embed/awwz92pVeXc
        //Log.e("CODE ","-------------------------- "+extractYTId("https://www.youtube.com/embed/awwz92pVeXc"));

    }


    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }

}
