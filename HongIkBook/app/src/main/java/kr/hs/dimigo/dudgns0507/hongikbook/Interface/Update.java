package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookFull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pyh42 on 2016-12-10.
 */

public interface Update {
    @GET("/update")
    Call<BookFull> update(@Query("id") int id, @Query("state") int state);
}
