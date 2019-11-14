package ocom.xixi.intelligent.http

import android.text.TextUtils
import com.xixi.intelligent.BuildConfig
import com.xixi.intelligent.base.Uris
import com.xixi.intelligent.http.KotlinService
import com.xixi.intelligent.utils.L
import com.xixi.intelligent.utils.SPUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by xixi on 2019/10/28.
 */
class ApiClient private constructor() {

    private val retrofit: Retrofit

    val kotlinService: KotlinService

    init {
        val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                ))
                .addInterceptor(RequestInterceptor())
                .addInterceptor(ResponseInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(Uris.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        kotlinService = retrofit.create(KotlinService::class.java)
    }

    companion object {
        val instance by lazy { ApiClient() }
    }

    /**
     * 请求拦截器
     */
    inner class RequestInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val oldRequest = chain?.request()
            val requestBuilder = oldRequest?.newBuilder()
            val access_token = SPUtils.getInstance().getString("access_token", "")
            if (!TextUtils.isEmpty(access_token)) {
                requestBuilder?.header("Authorization", "bearer$access_token")
            }
            L.i(" RequestInterceptor -- "+access_token)
            return chain!!.proceed(requestBuilder?.build())
        }
    }

    inner class ResponseInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val response = chain?.proceed(chain?.request());
            /*val auth = response?.header("Authenticator")
            if (auth != null) {
                SPUtils.getInstance().put("Authenticator", auth, true)
            }*/
            L.e(" ResponseInterceptor --$response")
            if (response?.body() != null && response!!.body()?.contentType() != null) {
                val mediaType = response!!.body()?.contentType()
                val message = response!!.body()?.string()
                val responseBody = ResponseBody.create(mediaType, message);
                return response.newBuilder().body(responseBody).build();
            } else {
                return response!!
            }

        }
    }

}