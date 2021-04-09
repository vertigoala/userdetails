package au.org.ala.recaptcha

import groovy.transform.CompileStatic
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@CompileStatic
interface RecaptchaClient {

    @POST("siteverify")
    @FormUrlEncoded
    Call<RecaptchaResponse> verify(@Field("secret") secret, @Field("response") String response, @Field("remoteip") String remoteip)

}