package kr.hs.dimigo.dudgns0507.hongikbook;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pyh42 on 2016-12-09.
 */

public interface User {
    @Headers("Authorization: Basic ZHVkZ25zMDUwNzp5aDI1ODUyNDQ5")
    @GET("user-students/{userName}")
    Call<UserInfo> getUser(@Path("userName") String userName);

}
