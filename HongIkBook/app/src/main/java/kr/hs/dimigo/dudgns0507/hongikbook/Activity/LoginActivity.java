package kr.hs.dimigo.dudgns0507.hongikbook.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.Login;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.User;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.LoginInfo;
import kr.hs.dimigo.dudgns0507.hongikbook.R;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.UserData;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
    HongIkBook
    Everybody is equal in front of the book.

    Daum Api Key : 58df52473d70e2cd69b9be4c88f2a57e
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "LoginActivity";

    private ProgressDialog loginDialog;

    @BindView(R.id.login_btn) CircularProgressButton login_btn;
    @BindView(R.id.edit_id) EditText edit_id;
    @BindView(R.id.edit_pw) EditText edit_pw;
    @BindView(R.id.auto_login) CheckBox auto_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        checkAutoLogin();

        putBitmap(R.id.login_background, R.drawable.login_background, 4);
        findViewById(R.id.login_btn).setOnClickListener(this);
        getPermission();
    }

    private void getPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(LoginActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.INTERNET)
                .check();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn :
                if(edit_id.getText().toString().equals("")) {
                    Snackbar.make(findViewById(R.id.activity_login), "ID를 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if(edit_pw.getText().toString().equals("")) {
                    Snackbar.make(findViewById(R.id.activity_login), "PW를 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                } else {
                    login_btn.startAnimation();
                    dimigoLogin(edit_id.getText().toString(), edit_pw.getText().toString());
                }
                break;
        }
    }

    void checkAutoLogin() {
        SharedPreferences sp = getSharedPreferences("autoLogin", MODE_PRIVATE);

        String userName = sp.getString("userName", null);
        String password = sp.getString("password", null);

        if(userName != null && password != null) {
            loginDialog = new ProgressDialog(LoginActivity.this);
            loginDialog.setMessage("로그인 중입니다..");
            loginDialog.show();
            dimigoLogin(userName, password);
        }
    }

    void dimigoLogin(final String userName, final String password) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.dimigo_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Login login = retrofit.create(Login.class);

        Call<LoginInfo> call = login.login(userName, password);
        call.enqueue(new Callback<LoginInfo>() {
            @Override
            public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                UserData.loginInfo = response.body();

                if(UserData.loginInfo == null) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "로그인에 실패했습니다.", Snackbar.LENGTH_LONG).show();
                    login_btn.revertAnimation();
                } else {
                    User user = retrofit.create(User.class);

                    Call<UserInfo> call1 = user.getUser(UserData.loginInfo.getUsername());
                    call1.enqueue(new Callback<UserInfo>() {
                        @Override
                        public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                            UserData.userInfo = response.body();

                            if (UserData.userInfo == null) {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "로그인에 실패했습니다.", Snackbar.LENGTH_LONG).show();
                                login_btn.revertAnimation();
                            } else {
                                if (loginDialog != null && loginDialog.isShowing())
                                    loginDialog.dismiss();

                                UserData.userInfo.setmClass(UserData.userInfo.getSerial().toString().substring(0, 1));

                                Log.w(TAG, UserData.userInfo.toString());

                                if (auto_login.isChecked()) {
                                    SharedPreferences sp = getSharedPreferences("autoLogin", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sp.edit();

                                    edit.putString("userName", userName);
                                    edit.putString("password", password);

                                    edit.commit();
                                }

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserInfo> call, Throwable t) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "로그인에 실패했습니다.", Snackbar.LENGTH_LONG).show();
                            t.printStackTrace();
                            login_btn.revertAnimation();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<LoginInfo> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "로그인에 실패했습니다.", Snackbar.LENGTH_LONG).show();
                t.printStackTrace();
                login_btn.revertAnimation();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycleView(R.id.login_background);
    }

    private void putBitmap(int imageViewId, int drawableId, int scale) {
        ImageView imageView = (ImageView)findViewById(imageViewId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = scale;

        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), drawableId, options));
    }

    private void recycleView(int id) {
        ImageView view = (ImageView)findViewById(id);

        Drawable d = view.getDrawable();
        if(d instanceof BitmapDrawable) {
            Bitmap b = ((BitmapDrawable) d).getBitmap();
            view.setImageBitmap(null);
            b.recycle();
            b = null;
        }
        d.setCallback(null);
        System.gc();
        Runtime.getRuntime().gc();
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
