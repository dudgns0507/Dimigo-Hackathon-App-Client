package kr.hs.dimigo.dudgns0507.hongikbook.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dimigo.dudgns0507.hongikbook.Fragment.LibraryFragment;
import kr.hs.dimigo.dudgns0507.hongikbook.Fragment.MyBookFragment;
import kr.hs.dimigo.dudgns0507.hongikbook.R;
import kr.hs.dimigo.dudgns0507.hongikbook.Fragment.ShareFragment;

/**
 * Created by pyh42 on 2016-12-07.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "MainActivity";
    private static final long RIPPLE_DURATION = 250;
    private View guillotineMenu;
    private GuillotineAnimation guillotineAnimation;

    @BindView(R.id.root_main) FrameLayout root;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.content_hamburger) View contentHamburger;
    @BindView(R.id.content_search) ImageView search_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        guillotineMenu.findViewById(R.id.share_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.library_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.mybook_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.logout_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.settings_group).setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, new ShareFragment()).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_group :
                search_btn.setVisibility(View.VISIBLE);
                guillotineAnimation.close();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new ShareFragment()).commit();
                break;
            case R.id.library_group :
                search_btn.setVisibility(View.VISIBLE);
                guillotineAnimation.close();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new LibraryFragment()).commit();
                break;
            case R.id.mybook_group :
                guillotineAnimation.close();
                search_btn.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new MyBookFragment()).commit();
                break;
            case R.id.logout_group :
                search_btn.setVisibility(View.GONE);
                dimigoLogout();
                break;
            case R.id.settings_group :
                search_btn.setVisibility(View.GONE);
                guillotineAnimation.close();
                break;
        }
    }

    private void dimigoLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("로그아웃 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        SharedPreferences sp = getSharedPreferences("autoLogin", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();

                        edit.putString("userName", null);
                        edit.putString("password", null);

                        edit.commit();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("앱을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
