package com.polina.vk2.network

import com.polina.vk2.catModel.CatImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImageApi {
    @GET("v1/images/search?limit=10")
    @Headers("x-api-key: live_ssyALQBcQkji6KfNgOluG0zk4o8vEKWKsKQrvE8NHluVE5rd2yMBWM5XlqbY4d9H")
    suspend fun getImages()
            : Response<List<CatImage>>
}