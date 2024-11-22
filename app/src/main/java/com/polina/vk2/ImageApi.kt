package com.polina.vk2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageApi {
    @GET("v1/images/search")
    @Headers("x-api-key: live_ssyALQBcQkji6KfNgOluG0zk4o8vEKWKsKQrvE8NHluVE5rd2yMBWM5XlqbY4d9H")
    suspend fun getImages(@Query("live_ssyALQBcQkji6KfNgOluG0zk4o8vEKWKsKQrvE8NHluVE5rd2yMBWM5XlqbY4d9H") clientId: String)
            : Response<List<CatImage>>
}