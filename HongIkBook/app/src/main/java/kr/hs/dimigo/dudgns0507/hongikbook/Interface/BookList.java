package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import java.util.ArrayList;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookAbb;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pyh42 on 2016-12-10.
 */

public interface BookList {
    @GET("books_short/{type}/{page}")
    Call<ArrayList<BookAbb>> getBookList(@Path("type") int type, @Path("page") int page);
}
