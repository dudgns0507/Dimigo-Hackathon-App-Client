package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pyh42 on 2016-12-10.
 */

public interface DeleteBook {
    @GET("/delete/{id}")
    Call<BookList> delete(@Path("id") int id);
}
