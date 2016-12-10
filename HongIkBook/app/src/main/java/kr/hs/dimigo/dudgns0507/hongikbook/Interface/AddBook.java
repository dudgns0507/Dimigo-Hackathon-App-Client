package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import java.util.HashMap;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookFull;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by pyh42 on 2016-12-10.
 */

public interface AddBook {
    @Headers("Content-type: application/json")
    @POST("books/")
    Call<BookFull> push(@Body HashMap<String, Object> json);
}
