package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private int[] board = new int[9]; //tablica
    int k = 0;

    String[] array = new String[]{"wynik 1", "wynik 2", "wynik 3"}; //tablica do spinnera


    //point
    private Point wymiary;
    //boolden
    private boolean shouldBeCross;
    private boolean win;

    //przyciski
    private Button bt1;
    private Button bt2;
    private Button scoresButton;
    private Button bt4;

    //TextView
    private TextView tv1;

    //liczbowe
    int szerokość;
    int długość;
    int ekran;


    //layouty
    private FrameLayout frame;
    private FrameLayout.LayoutParams framep;
    private RelativeLayout relativeLayout;

    //imige viiew
    private ImageView klik;


    private void nowa_tarcza() {
        k = 0;
        board = new int[9]; //tablica
        shouldBeCross = false; // pierwszy obrazek bedzie kolkiem - shouldBeCross

        //wymiary ekranu
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point(); // moze przyjac dwie wartosci 'x' i 'y'
        display.getSize(size);
        Log.d("wymiary", "szerokość ekranu " + size.x);
        Log.d("wymiary", "wysokość ekranu " + size.y);
        szerokość = size.x / ekran;//zmień później szerokosc jednego kwadratcika
        długość = size.y / ekran;
        //tworzenie tarczy

        FrameLayout.LayoutParams wymiary_klik = new FrameLayout.LayoutParams(szerokość - 10, szerokość - 10);
        wymiary_klik.setMargins(6, 6, 6, 6);

        //pierwsza pętla
        for (int p = 0; p < ekran; p++) {
            for (int d = 0; d < ekran; d++) {
                ImageView klik = new ImageView(MainActivity.this);
                klik.setBackgroundColor(Color.BLUE);  //nadanie koloru obrazka
                klik.setLayoutParams(wymiary_klik);    //ustawianie wymiarów
                klik.setX(szerokość * p);  // nadanie wymiarów
                klik.setY(szerokość * d);
                klik.setId(k);  //nadanie mu id "d"
                k++; //zmienna do ID TextView

                klik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView figura = (ImageView) v;
                        Log.d("figura", String.valueOf((figura.getId())));
                        figura.setPadding(10, 10, 10, 10); //marginesy kółka/krzyżyka


                        if (figura.getDrawable() == null) {
                            Log.d("dupa", "id " + String.valueOf((figura.getId())));
                            if (!shouldBeCross) {
                                figura.setImageResource(R.drawable.circle);
                                shouldBeCross = true;
                                board[figura.getId()] = 1;
                            } else {
                                figura.setImageResource(R.drawable.cross);
                                shouldBeCross = false;
                                board[figura.getId()] = 2;
                            }
                        } else {
                            Log.d("second", "id " + String.valueOf((figura.getId())));
                        } //koniec pętli if
                        Log.d("tab", "tablica " + Arrays.toString(board));
                        wygrana(1);// check wygranej
                        wygrana(2);// check wygranej
                    }//koniec pętli guzika klik OVERRIDE
                });//koniec ogólenj pętli guzika
                frame.addView(klik);
            } //koenic pętli for -d

        }//koniec pętli for -p


    }//koniec funkcji


    //początek funkcji GenerujToast()
    private void GenerujToast() {

        //inflater - obiekt przetwarzający xml na javę

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, null);

//szukanie kontrolek w layoucie

        TextView text = (TextView) layout.findViewById(R.id.txt);
        text.setText("abc");
        text.setTextSize(60);


//ustawienie wyglądu layoutu
        layout.setBackgroundColor(0xffffff00);

//przypisanie całości do Toasta, oraz ustawienie go na ekranie

        Toast toast = new Toast(MainActivity.this);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }//koniec funkcji GenerujToast()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo refactor create buttons method

        // createButtons();
        scoresButton = findViewById(R.id.scoresButton);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scoresIntent = new Intent(view.getContext(), SavedBoardsActivity.class);
                startActivityForResult(scoresIntent, 0);
            }
        });

        // files
        filesExplorer();


        //początek
        frame = findViewById(R.id.frame);
        Display wyswietlacz = getWindowManager().getDefaultDisplay();       //zebranie informacji o rozmiarze ekranu
        wymiary = new Point();                                  //utworzenie nowej dwuelementowej zmiennej typu Point;
        wyswietlacz.getSize(wymiary);              //pobranie wymiarów wyświetlacza
        ekran = 3;
        nowa_tarcza(); //rysuje nową tarczę
        Log.d("tab", "tablica " + board);

        tv1 = findViewById(R.id.tv1);


        relativeLayout = findViewById(R.id.relativeLayout);


        bt1 = findViewById(R.id.bt1);//guzik czyszczący gre
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame.removeAllViews();
                nowa_tarcza();
            }
        });
        //koniec pierwszego guzika

        bt2 = findViewById(R.id.bt2);//guzik zmieniający
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ekran++;
                if (ekran > 6) {
                    ekran = 3;
                    frame.removeAllViews();
                    nowa_tarcza();
                }//koniec pętli if
                else {
                    frame.removeAllViews();
                    nowa_tarcza();
                    //jeżeli ekran>6 to =3
                }//koniec else


            }//koniec guzika


        }); //koniec guzika zmienającego


    }//koniec overide

    private void filesExplorer() {
        Calendar calendar = Calendar.getInstance();

        final String dateFormat = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String date = simpleDateFormat.format(calendar.getTime());


        // todo add check for permissions


        File parentFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!parentFolder.exists()) {
            parentFolder.mkdir();
        }

        final String myFolderName = "SmolenScores";

        File scoresFolder = new File(parentFolder, myFolderName);
        scoresFolder.mkdir();

        Log.d("scoresFolder", String.valueOf(scoresFolder));

        String dataToWrite = Arrays.toString(board);

        File scoreFile = new File(scoresFolder, date);
        FileWriter writer = null;
        try {
            writer = new FileWriter(scoreFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.append(dataToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("scoreFile", "scoreFile SAVED");

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //wywoływanie podglądu plików
//        intent.setType("text/plain");
//        startActivity(intent);
    }


    //-----------------

    private void wygrana(int co) {

        if (board[0] == co && board[1] == co && board[2] == co) win = true;
        if (board[3] == co && board[4] == co && board[5] == co) win = true;
        if (board[6] == co && board[7] == co && board[8] == co) win = true;
        if (board[0] == co && board[3] == co && board[6] == co) win = true;
        if (board[1] == co && board[4] == co && board[7] == co) win = true;
        if (board[2] == co && board[5] == co && board[8] == co) win = true;
        if (board[0] == co && board[4] == co && board[8] == co) win = true;
        if (board[2] == co && board[4] == co && board[6] == co) win = true;


        if (win) {
            if (co == 1) Log.d("aaa", "o wygrywają");
            if (co == 2) Log.d("bbb", "x wygywają");
            Log.d("isVisible", "isVisible? :" + String.valueOf(tv1.getVisibility()));
            tv1.setVisibility(View.VISIBLE);
        }

    } //koniec funkcji wygrana


    //początek spinnera
//    private void makeBoard(int size) {
//
//        // usuwanie poprzednio wygenerowanej planszy
//
//        frame.removeAllViews();
//
//        //tworzenie planszy o rozmiarach podanych w Spinnerze
//        //teraz trzeba dostosować rozmiar jednego elementu planszy
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                // ...
//            }
//        }
//    }

}//koniec apki
