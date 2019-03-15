package ru.sberbank.homework21;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private MyCustomView mMyCustomView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyCustomView = findViewById(R.id.myView);
        mMyCustomView.setOnTouchListener(new MyCutomViewOnDragTouchListener(mMyCustomView));
    }

}
