package kr.hs.dimigo.dudgns0507.hongikbook.Interface;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pyh42 on 2016-12-10.
 */

public interface SerialUser {
    @Headers("Authorization: Basic ZHVkZ25zMDUwNzp5aDI1ODUyNDQ5")
    @GET("user-students/search")
    Call<UserInfo> getUser(@Query("serial") String serial);
}
