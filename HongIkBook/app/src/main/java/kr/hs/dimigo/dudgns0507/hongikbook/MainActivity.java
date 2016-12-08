package kr.hs.dimigo.dudgns0507.hongikbook;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyh42 on 2016-12-07.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "MainActivity";
    private static final long RIPPLE_DURATION = 250;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout root = (FrameLayout)findViewById(R.id.root_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        View contentHamburger = findViewById(R.id.content_hamburger);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        guillotineMenu.findViewById(R.id.profile_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.activity_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.feed_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.settings_group).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_group :
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_group :
                Toast.makeText(this, "activity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.feed_group :
                Toast.makeText(this, "feed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings_group :
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
