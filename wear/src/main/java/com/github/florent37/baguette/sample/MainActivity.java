package com.github.florent37.baguette.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.florent37.baguette.Baguette;

public class MainActivity extends Activity {

    private final static String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Baguette.makeText(MainActivity.this, R.string.baguette_sample, Baguette.LENGTH_SHORT).enableUndo(new Baguette.BaguetteListener() {
                    @Override
                    public void onActionClicked() {
                        Log.d(TAG, "undo");
                    }
                }).show();
            }
        });

    }
}
