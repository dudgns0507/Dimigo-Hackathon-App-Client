package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.LoginInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by pyh42 on 2016-12-09.
 */

public interface Login {
    @Headers("Authorization: Basic ZHVkZ25zMDUwNzp5aDI1ODUyNDQ5")
    @GET("users/identify")
    Call<LoginInfo> login(@Query("username") String userName, @Query("password") String password);
}
