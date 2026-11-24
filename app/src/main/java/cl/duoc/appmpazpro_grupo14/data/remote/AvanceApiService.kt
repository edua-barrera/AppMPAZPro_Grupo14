package cl.duoc.appmpazpro_grupo14.data.remote

import retrofit2.Response
import retrofit2.http.*

interface AvanceApiService {

    @GET("api/avances")
    suspend fun obtenerAvances(): List<AvanceRemotoDto>

    @POST("api/avances")
    suspend fun crearAvance(@Body avance: AvanceRemotoDto): AvanceRemotoDto

    @PUT("api/avances/{id}")
    suspend fun actualizarAvance(
        @Path("id") id: Long,
        @Body avance: AvanceRemotoDto
    ): AvanceRemotoDto

    @DELETE("api/avances/{id}")
    suspend fun eliminarAvance(@Path("id") id: Long): Response<Unit>
}