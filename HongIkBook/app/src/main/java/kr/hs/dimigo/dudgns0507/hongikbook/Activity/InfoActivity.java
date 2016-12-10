package kr.hs.dimigo.dudgns0507.hongikbook.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookAbb;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookFull;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookResult;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.UserData;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.UserInfo;
import kr.hs.dimigo.dudgns0507.hongikbook.Fragment.AddBookCheckFragment;
import kr.hs.dimigo.dudgns0507.hongikbook.Fragment.MyBookFragment;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.AsyncResponse;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.BookList;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.BookSearch;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.MyBookList;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.OneBook;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.SerialUser;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.Update;
import kr.hs.dimigo.dudgns0507.hongikbook.R;
import kr.hs.dimigo.dudgns0507.hongikbook.Util.GetImageFromUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pyh42 on 2016-12-10.
 */

public class InfoActivity extends AppCompatActivity implements AsyncResponse {

    private static final String TAG = "InfoActivity";
    private int id = 0;
    private boolean state = false;
    private BookFull bookInfo;

    @BindView(R.id.book_image) ImageView book_image;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.publisher) TextView publisher;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.location) TextView location;
    @BindView(R.id.owner) TextView owner;

    @BindView(R.id.rent_btn) Button rent_btn;
    @BindView(R.id.res_btn) Button res_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        state = intent.getBooleanExtra("state", false);

        final int st = (state?2:1);

        getBook();

        rent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .readTimeout(20, TimeUnit.SECONDS)
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getResources().getString(R.string.api_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();

                Update update = retrofit.create(Update.class);

                Call<BookFull> call = update.update(id, st);
                call.enqueue(new Callback<BookFull>() {
                    @Override
                    public void onResponse(Call<BookFull> call, Response<BookFull> response) {
                        Toast.makeText(InfoActivity.this, "대여 신청이 완료되었습니다.\n도서를 가져가시면 됩니다.", Toast.LENGTH_LONG).show();
                        Runnable run = new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        };

                        Handler h = new Handler();
                        h.postDelayed(run, 500);
                    }

                    @Override
                    public void onFailure(Call<BookFull> call, Throwable t) {
                        Toast.makeText(InfoActivity.this, "로딩에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InfoActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getUserWithSerial(final String serial) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.dimigo_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        SerialUser serialUser = retrofit.create(SerialUser.class);

        Call<UserInfo> call = serialUser.getUser(serial);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                owner.setText("주인 : " + response.body().getName() + " - " + serial);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    void getBook() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OneBook oneBook = retrofit.create(OneBook.class);

        Call<BookFull> call = oneBook.getBook(id);
        call.enqueue(new Callback<BookFull>() {
            @Override
            public void onResponse(Call<BookFull> call, Response<BookFull> response) {
                bookInfo = response.body();

                GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                getImageFromUrl.delegate = InfoActivity.this;
                getImageFromUrl.getData(bookInfo.getImage_url());
            }

            @Override
            public void onFailure(Call<BookFull> call, Throwable t) {
                Toast.makeText(InfoActivity.this, "로딩에 실패했습니다.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void processFinish(Bitmap output) {
        if(output != null) {
            book_image.setImageBitmap(output);
        }
        title.setText(bookInfo.getTitle());
        author.setText("저자 : " + bookInfo.getAuthor());
        publisher.setText("출판사 : " + bookInfo.getPublisher());
        price.setText("가격 : " + bookInfo.getPrice());
        description.setText("소개 : " + bookInfo.getDescription().replace("&#39", "'"));

        getUserWithSerial(bookInfo.getOwner_serial());
        location.setText("장소 : " + bookInfo.getOwner_serial().substring(0, 1) + "학년 " + bookInfo.getOwner_serial().substring(1, 2) + "반");

    }
}
