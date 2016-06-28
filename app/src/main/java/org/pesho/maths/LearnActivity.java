package org.pesho.maths;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LearnActivity extends AppCompatActivity {

    int x=15;
    int y =73;
    LearnView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        lv= (LearnView) findViewById(R.id.screen);
        lv.setX(x);
        lv.setY(y);

    }
}
