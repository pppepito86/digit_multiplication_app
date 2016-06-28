package org.pesho.maths;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CalculatorView dv ;
    TextView stats;
    TextView stats1;
    TextView stats2;
    TextView task;
    Button clear;
    Button submit;
    EditText answer;
    String output;
    String rightAnswer;
    int y;
    int x;
    Random r = new Random();
    int rightAnswers=0;
    int wrongAnswers=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dv = new CalculatorView(this);
//        getLayoutInflater().inflate();
        setContentView(R.layout.activity_main);
        dv=(CalculatorView)findViewById(R.id.draw);
        task= (TextView) findViewById(R.id.task);
        clear= (Button) findViewById(R.id.clear);
        stats= (TextView) findViewById(R.id.stats);
        stats1= (TextView) findViewById(R.id.stats1);
        stats2= (TextView) findViewById(R.id.stats2);
        SharedPreferences statsFile = getSharedPreferences("stats", MODE_PRIVATE);
        rightAnswers=statsFile.getInt("rightAnswers",0);
        wrongAnswers=statsFile.getInt("wrongAnswers",0);
        stats.setText(rightAnswers+"");
        stats2.setText(wrongAnswers+"");
        x = r.nextInt(100 - 10)+10;
        y = r.nextInt(100 - 10)+10;
        //task.setText("   "+x+"\n"+"x "+y);
        task.setText("\n"+x+"."+""+y);
        submit= (Button) findViewById(R.id.submit);
        answer= (EditText) findViewById(R.id.answer);
        answer.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    validate();
                    reset();
                    return true;
                }
                return false;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                verifyStoragePermissions(MainActivity.this);

                String digit = new Gson().toJson(dv.record);
                String digitName = answer.getText().toString();

                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS+"/digits");
                Log.e("pesho", directory.getAbsolutePath());
                directory.mkdirs();
                File file = new File(directory, digitName + ".txt");
                try {
                    PrintWriter pw = new PrintWriter(file);
                    pw.println(digit);
                    pw.close();
                }catch (Exception e) {
                    Log.e("pesho", "ex", e);

                }*/
/*
                List<CalculatorView.RecordItem> l = dv.record;
                for (CalculatorView.RecordItem i : l) {
                    if (i.action == MotionEvent.ACTION_DOWN) {
                        dv.touch_start(i.x * 500 + 500, i.y * 500 + 500);
                    } else if (i.action == MotionEvent.ACTION_MOVE) {
                        dv.touch_move(i.x * 500 + 500, i.y * 500 + 500);

                    } else if (i.action == MotionEvent.ACTION_UP) {
                        dv.touch_up();
                    }

                    runOnUiThread(new Thread() {
                        @Override
                        public void run() {
                            dv.invalidate();
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }*/

                validate();
                reset();

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dv.clearScreen();
                dv.invalidate();
            }
        });



    }

    void  alert(){
        new AlertDialog.Builder(this)
                .setTitle("wrong")
                .setMessage(x+"*"+y+"="+rightAnswer)
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        reset();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    void validate(){
        output = answer.getText().toString().trim();
        rightAnswer = Integer.toString(x * y);
        if (output.equals(rightAnswer)) {
            Toast.makeText(MainActivity.this, "well done", Toast.LENGTH_LONG).show();
            rightAnswers++;
            stats.setText(rightAnswers+"");
            stats2.setText(wrongAnswers+"");
            x = r.nextInt(100 -10)+10;
            y = r.nextInt(100 - 10)+10;
        } else {
            //Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();
            alert();
            wrongAnswers++;
            stats.setText(rightAnswers+"");
            stats2.setText(wrongAnswers+"");
        }

        SharedPreferences stats = getSharedPreferences("stats", MODE_PRIVATE);
        SharedPreferences.Editor edit = stats.edit();
        edit.putInt("wrongAnswers", wrongAnswers);
        edit.putInt("rightAnswers", rightAnswers);
        edit.apply();
        answer.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    void reset(){
        x = r.nextInt(100 -10)+10;
        y = r.nextInt(100 - 10)+10;
        task.setText("   "+x+"\n"+"x "+y);
        task.setText("\n"+x+"."+""+y);
        dv.clearScreen();
        dv.invalidate();

    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


}
