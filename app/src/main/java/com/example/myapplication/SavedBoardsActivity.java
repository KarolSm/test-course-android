package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class SavedBoardsActivity extends MainActivity {

    private ListView scoresList;
//    private TextView testText;
    String[] values = new String[]{
            "Android List View",
            "Adapter implementation",
            "Simple List View In Android",
            "Create List View Android",
            "Android Example",
            "List View Source Code",
            "List View Array Adapter",
            "Android Example List View"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_boards);

        scoresList = findViewById(R.id.scoresList);

//        testText = findViewById(R.id.testText);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_saved_boards, R.id.testText, values);
        scoresList.setAdapter(adapter);

    }
}
