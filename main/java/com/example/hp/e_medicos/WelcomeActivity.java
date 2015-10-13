package com.example.hp.e_medicos;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomeActivity extends ActionBarActivity {
    TextView tv;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        iv=(ImageView)findViewById(R.id.imageView2);
        tv=(TextView)findViewById(R.id.textView16);
        ObjectAnimator anim = ObjectAnimator.ofFloat(tv, "rotationY", 0, 90);
        anim.setDuration(1000);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,MenuScreenActivity.class));
                finish();
            }
        });
    }
}
