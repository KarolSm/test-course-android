package com.example.ryzen.k8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class SavedBoardsActivity extends AppCompatActivity {
    private ListView lv1;
    String[] array = new String[]{"wynik 1","wynik 2","wynik 3"}; //tablica dla lv1



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_boards);

//        lv1=findViewById(R.id.lv1);
//
//        Intent intent = new Intent(MainActivity.this, SavedBoardsActivity.class);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this,         // Context
//                R.layout.activity_saved_boards,       // nazwa pliku xml naszego wiersza
//                R.id.tv1,                  // id pola txt w wierszu
//                array );                   // tablica przechowująca dane


    }
}
