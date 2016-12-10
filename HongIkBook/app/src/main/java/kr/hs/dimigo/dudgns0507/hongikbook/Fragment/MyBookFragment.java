package kr.hs.dimigo.dudgns0507.hongikbook.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookAbb;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookResult;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.Item;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.UserData;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.AsyncResponse;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.BookList;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.BookSearch;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.MyBookList;
import kr.hs.dimigo.dudgns0507.hongikbook.List.ListViewAdapter;
import kr.hs.dimigo.dudgns0507.hongikbook.R;
import kr.hs.dimigo.dudgns0507.hongikbook.Util.GetImageFromUrl;
import kr.hs.dimigo.dudgns0507.hongikbook.Util.SwipeDismissListViewTouchListener;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookFragment extends Fragment implements AsyncResponse{

    private final static String key = "be4f116f673eafce91358e46b9773540";
    private final static String search_url = "https://apis.daum.net/";
    private final static String TAG = "MyBookFragment";
    private ListViewAdapter mAdapter;
    View view;
    ListView listView;
    Item result_item;
    LinearLayout linlaHeaderProgress;
    ProgressDialog asyncDialog;

    public MyBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_book, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new ListViewAdapter(getContext());
        listView.setAdapter(mAdapter);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, final int[] reverseSortedPositions) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setMessage("이 도서의 공유를 취소하겠습니까?")
                                        .setCancelable(false)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int whichButton){
                                                for (int position : reverseSortedPositions) {
                                                    mAdapter.remove(position);
                                                }
                                                mAdapter.dataChange();
                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int whichButton){
                                                dialog.cancel();
                                            }
                                        });


                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());

        linlaHeaderProgress = (LinearLayout)view.findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.VISIBLE);

        addList();

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "ALL");
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

    void addList() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        MyBookList myBookList = retrofit.create(MyBookList.class);

        Call<ArrayList<BookAbb>> call = myBookList.getMyBook(UserData.userInfo.getSerial());
        call.enqueue(new Callback<ArrayList<BookAbb>>() {
            @Override
            public void onResponse(Call<ArrayList<BookAbb>> call, Response<ArrayList<BookAbb>> response) {
                ArrayList<BookAbb> bookList = response.body();

                linlaHeaderProgress.setVisibility(View.GONE);
                for(int i = 0 ; i < bookList.size() ; i++) {
                    BookAbb bookAbb = bookList.get(i);
                    mAdapter.addItem(bookAbb.getTitle(), bookAbb.getAuthor(), bookAbb.getPublisher(), bookAbb.getRental_state());
                    mAdapter.dataChange();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BookAbb>> call, Throwable t) {
                Toast.makeText(getContext(), "로딩에 실패했습니다.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_OK)
            {
                final String contents = data.getStringExtra("SCAN_RESULT");
                searchBook(contents);
            }
        }
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                linlaHeaderProgress.setVisibility(View.VISIBLE);
                cleanList();
                addList();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    void cleanList() {
        while(true) {
            if(mAdapter.mListData.isEmpty()) {
                break;
            }
            mAdapter.remove(0);
        }
    }

    private void searchBook(final String contents) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(search_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BookSearch bookSearch = retrofit.create(BookSearch.class);

        asyncDialog = new ProgressDialog(getContext());
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
                    result_item = bookResult.getChannel().getItem()[0];

                    Log.w(TAG, "Get Image From URL");
                    GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                    getImageFromUrl.delegate = MyBookFragment.this;
                    getImageFromUrl.getData(result_item.getCover_l_url());
                } else {
                    Toast.makeText(getContext(), "도서가 없습니다.", Toast.LENGTH_SHORT).show();
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

        Bundle args = new Bundle();
        args.putString("title", result_item.getTitle());
        args.putString("author", result_item.getAuthor());
        args.putString("publisher", result_item.getPub_nm());
        args.putString("price", result_item.getList_price());
        args.putString("description", result_item.getDescription());
        args.putString("isbn", result_item.getIsbn13());
        args.putString("publication", result_item.getPub_date());
        args.putString("owner_serial", UserData.userInfo.getSerial());

        asyncDialog.dismiss();
        if(output != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            output.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            args.putByteArray("bitmap", byteArray);

            AddBookCheckFragment bookCheckFragment = new AddBookCheckFragment();
            bookCheckFragment.setArguments(args);
            bookCheckFragment.setTargetFragment(this, 1);
            bookCheckFragment.show(getFragmentManager(), "AddBookCheckFragment");
        }
    }
}
