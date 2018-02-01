package com.superfactory.sunyatsin.Communication

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  18:28:13
 * @ClassName 这里输入你的类名(或用途)
 */
interface RetrofitImpl {

    @GET("{path}")
    fun eraseBadge(@Path("path") path: String): Observable<ResponseBody>


    @POST("login?__ajax")
    fun login(@Query("username") username: String, @Query("password") password: String,
              @Query("mobileLogin") mobileLogin: Boolean): Observable<ResponseBody>

    @GET("sys/user/infoData;JSESSIONID={JSESSIONID}?")
    fun loginAfter(@Path("JSESSIONID") JSESSIONID: String,
                   @Query("__ajax=true&mobileLogin") mobileLogin: Boolean): Observable<ResponseBody>


    @POST("sys/user/savePwd;JSESSIONID={JSESSIONID}?")
    fun changePsw(@Path("JSESSIONID") JSESSIONID: String,
                  @Field("oldPassword") oldPassword: String,
                  @Field("newPassword") newPassword: String,
                  @Query("__ajax=true&mobileLogin") mobileLogin: Boolean): Observable<ResponseBody>

    @POST("api/naire/data;JSESSIONID={JSESSIONID}?")
    fun questionnaireList(@Path("JSESSIONID") JSESSIONID: String,
                          @Query("__ajax=true&mobileLogin") mobileLogin: Boolean): Observable<ResponseBody>


    @POST("api/naire/findQuestionByParentId;JSESSIONID={JSESSIONID}?")
    fun questionnaireDetail(@Path("JSESSIONID") JSESSIONID: String, @Field("parentId") parentId: String,
                            @Query("__ajax=true&mobileLogin") mobileLogin: Boolean): Observable<ResponseBody>

    @POST("api/naire/save;JSESSIONID={JSESSIONID}?")
    fun storeQuestionnaire(@Path("JSESSIONID") JSESSIONID: String, @Body() param: String,
                           @Query("__ajax=true&mobileLogin") mobileLogin: Boolean): Observable<ResponseBody>

}