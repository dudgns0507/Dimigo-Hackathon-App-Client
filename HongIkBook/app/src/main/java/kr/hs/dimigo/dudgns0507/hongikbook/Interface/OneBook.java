package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookFull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pyh42 on 2016-12-10.
 */

public interface OneBook {
    @GET("books_full")
    Call<BookFull> getBook(@Query("id") int id);
}
