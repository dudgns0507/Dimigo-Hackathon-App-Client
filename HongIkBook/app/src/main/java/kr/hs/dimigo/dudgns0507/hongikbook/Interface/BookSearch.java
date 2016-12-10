package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pyh42 on 2016-11-24.
 */

public interface BookSearch {
    @GET("search/book")
    Call<BookResult> search(@Query("apikey") String key, @Query("output") String output, @Query("q") String query, @Query("searchType") String type);
}
