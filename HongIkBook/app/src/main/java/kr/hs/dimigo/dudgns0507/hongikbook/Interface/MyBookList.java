package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import java.util.ArrayList;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookAbb;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pyh42 on 2016-12-10.
 */

public interface MyBookList {
    @GET("mybooks")
    Call<ArrayList<BookAbb>> getMyBook(@Query("serial") String serial);
}
