package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by pyh42 on 2016-12-09.
 */

public interface User {
    @Headers("Authorization: Basic ZHVkZ25zMDUwNzp5aDI1ODUyNDQ5")
    @GET("user-students/{userName}")
    Call<UserInfo> getUser(@Path("userName") String userName);

}
