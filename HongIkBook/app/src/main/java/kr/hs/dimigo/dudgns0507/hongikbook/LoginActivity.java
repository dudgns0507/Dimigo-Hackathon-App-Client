package kr.hs.dimigo.dudgns0507.hongikbook;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    private final static String key = "be4f116f673eafce91358e46b9773540";
    private final static String search_url = "https://apis.daum.net/";
    private final static String TAG = "LoginActivity";

    private ProgressDialog asyncDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.scan_btn).setOnClickListener(this);

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
            case R.id.scan_btn:
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "ALL");
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_OK)
            {
                final String contents = data.getStringExtra("SCAN_RESULT");
                searchBook(contents);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void searchBook(final String contents) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(search_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BookSearch bookSearch = retrofit.create(BookSearch.class);

        asyncDialog = new ProgressDialog(LoginActivity.this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("검색중입니다...");

        asyncDialog.show();

        Call<BookResult> call = bookSearch.search(key, "json", contents, "isbn");

        call.enqueue(new Callback<BookResult>() {
            @Override
            public void onResponse(Call<BookResult> call, Response<BookResult> response) {
                Log.e(TAG, response.message());
                Log.e(TAG, response.raw().toString());

                BookResult bookResult = response.body();

                if(Integer.parseInt(bookResult.getChannel().getTotalCount()) >= 1) {
                    Item result_item = bookResult.getChannel().getItem()[0];

                    TextView title = (TextView) findViewById(R.id.title);
                    title.setText("도서 : " + result_item.getTitle());

                    TextView isbn = (TextView) findViewById(R.id.isbn);
                    isbn.setText("ISBN : " + contents);

                    TextView author = (TextView) findViewById(R.id.author);
                    author.setText("저자 : " + result_item.getAuthor());

                    TextView pub_date = (TextView) findViewById(R.id.pub_date);
                    pub_date.setText("출판일 : " + result_item.getPub_date());

                    TextView pub_nm = (TextView) findViewById(R.id.pub_nm);
                    pub_nm.setText("출판사 : " + result_item.getPub_nm());

                    TextView list_price = (TextView) findViewById(R.id.list_price);
                    list_price.setText("가격 : " + result_item.getList_price());

                    TextView description = (TextView) findViewById(R.id.description);
                    description.setText("설명 : " + description.getText() + result_item.getDescription());

                    Log.w(TAG, "Get Image From URL");
                    GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                    getImageFromUrl.delegate = LoginActivity.this;
                    getImageFromUrl.getData(result_item.getCover_l_url());
                } else {
                    Toast.makeText(LoginActivity.this, "도서가 없습니다.", Toast.LENGTH_SHORT).show();
                    asyncDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BookResult> call, Throwable t) {
                Log.e(TAG ,t.getMessage());
            }
        });
    }

    @Override
    public void processFinish(Bitmap output) {
        Log.w(TAG, "process finish");

        if(output != null) {
            ImageView cover_s_url = (ImageView) findViewById(R.id.cover_s_url);
            cover_s_url.setImageBitmap(output);
        }

        asyncDialog.dismiss();
    }
}
