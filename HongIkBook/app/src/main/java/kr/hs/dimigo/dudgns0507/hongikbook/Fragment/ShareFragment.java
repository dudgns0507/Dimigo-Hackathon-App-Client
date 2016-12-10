package kr.hs.dimigo.dudgns0507.hongikbook.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookAbb;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.BookList;
import kr.hs.dimigo.dudgns0507.hongikbook.List.ListViewAdapter;
import kr.hs.dimigo.dudgns0507.hongikbook.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {

    private final static String TAG = "ShareFragment";
    private final int type = 1;
    private ListViewAdapter mAdapter;
    private int page_cnt = 1;
    private SwipyRefreshLayout refreshLayout;

    ListView listView;
    LinearLayout linlaHeaderProgress;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);

        linlaHeaderProgress = (LinearLayout)view.findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.VISIBLE);

        mAdapter = new ListViewAdapter(getContext());
        listView.setAdapter(mAdapter);

        refreshLayout = (SwipyRefreshLayout)view.findViewById(R.id.swipyrefreshlayout);
        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                addList(page_cnt++);
            }
        });

        addList(page_cnt++);

        return view;
    }

    void addList(int page) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        BookList bookList = retrofit.create(BookList.class);

        Call<ArrayList<BookAbb>> call = bookList.getBookList(type, page);
        call.enqueue(new Callback<ArrayList<BookAbb>>() {
            @Override
            public void onResponse(Call<ArrayList<BookAbb>> call, Response<ArrayList<BookAbb>> response) {
                ArrayList<BookAbb> bookList = response.body();

                linlaHeaderProgress.setVisibility(View.GONE);
                for(int i = 0 ; i < bookList.size() ; i++) {
                    BookAbb bookAbb = bookList.get(i);
                    mAdapter.addItem(bookAbb.getTitle(), bookAbb.getAuthor(), bookAbb.getPublisher(), bookAbb.getRental_state(), bookAbb.getId());
                    mAdapter.dataChange();
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<BookAbb>> call, Throwable t) {
                Toast.makeText(getContext(), "로딩에 실패했습니다.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
