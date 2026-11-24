package cl.duoc.appmpazpro_grupo14.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendClient {
    private const val BASE_URL = "http://localhost:8080/"

    val api: AvanceApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AvanceApiService::class.java)
    }
}