package com.example.smilyrating;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import su.levenetc.android.badgeview.BadgeView;

public class MainActivity extends AppCompatActivity implements SmileRating.OnSmileySelectionListener, SmileRating.OnRatingSelectedListener {

    private static final String TAG = "MainActivity";

    private SmileRating mSmileRating;
    private BadgeView badgeView;
    private FrameLayout frame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.activity_main);
        mSmileRating = (SmileRating) findViewById(R.id.ratingView);
        badgeView = (BadgeView) findViewById(R.id.badge_view);
        frame = (FrameLayout) findViewById(R.id.frame);
        badgeView.setVisibility(View.INVISIBLE);
        frame.setVisibility(View.INVISIBLE);
        mSmileRating.setOnSmileySelectionListener(this);
        mSmileRating.setOnRatingSelectedListener(this);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "overpass_bold.otf");
        mSmileRating.setTypeface(typeface);
    }


    Runnable afterExe = new Runnable() {
        public void run() {
            badgeView.setVisibility(View.INVISIBLE);
            frame.setVisibility(View.INVISIBLE);

        }
    };
    Bitmap icon = null;

    @Override
    public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
        switch (smiley) {

            case SmileRating.BAD:
                icon = BitmapFactory.decodeResource(getResources(), R.mipmap.poor);
                showResponse(icon);
                break;
            case SmileRating.GOOD:
                icon = BitmapFactory.decodeResource(getResources(), R.mipmap.good);
                showResponse(icon);
                break;
            case SmileRating.GREAT:

                icon = BitmapFactory.decodeResource(getResources(), R.mipmap.smile);
                showResponse(icon);

                break;
            case SmileRating.OKAY:
                icon = BitmapFactory.decodeResource(getResources(), R.mipmap.average);
                showResponse(icon);
                break;
            case SmileRating.TERRIBLE:
                icon = BitmapFactory.decodeResource(getResources(), R.mipmap.verypoor);
                showResponse(icon);
                break;
            case SmileRating.NONE:
                Log.i(TAG, "None");
                break;
        }
    }

    @Override
    public void onRatingSelected(int level, boolean reselected) {
        Log.i(TAG, "Rated as: " + level + " - " + reselected);
    }

    void showResponse(final Bitmap icon) {

        badgeView.setVisibility(View.VISIBLE);
        frame.setVisibility(View.VISIBLE);

        badgeView.postDelayed(new Runnable() {
            @Override
            public void run() {

                final int duration = 750;
                badgeView.setValue(icon);
                new BadgeView.AnimationSet(badgeView)
                        .add("Thank You ", duration)
                        .add("We appreciate your remarks.", duration)
                        .play();

                badgeView.postDelayed(afterExe, 3500);
            }
        }, 100);
    }

    void fullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
