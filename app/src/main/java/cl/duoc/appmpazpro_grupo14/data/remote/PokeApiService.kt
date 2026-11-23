package cl.duoc.appmpazpro_grupo14.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class PokemonResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeSlot>,
    val sprites: PokemonSprites
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: NamedResource
)

data class NamedResource(
    val name: String,
    val url: String
)

data class PokemonSprites(
    @SerializedName("front_default") val frontDefault: String?,
    val other: OtherSprites?
)

data class OtherSprites(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork?
)

data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String?
)

data class PokemonSpeciesResponse(
    @SerializedName("gender_rate") val genderRate: Int,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>
)

data class FlavorTextEntry(
    @SerializedName("flavor_text") val flavorText: String,
    val language: NamedResource
)

data class PokemonRecompensaUi(
    val numeroPokedex: String,
    val nombre: String,
    val tipo: String,
    val sexo: String,
    val altura: String,
    val peso: String,
    val descripcion: String,
    val imagenUrl: String
)

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun obtenerPokemon(@Path("id") id: Int): PokemonResponse

    @GET("pokemon-species/{id}")
    suspend fun obtenerEspecie(@Path("id") id: Int): PokemonSpeciesResponse

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        val instance: PokeApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokeApiService::class.java)
        }
    }
}

class PokemonRepository(
    private val api: PokeApiService = PokeApiService.instance
) {
    private val tiposEnEspanol = mapOf(
        "normal" to "Normal", "fire" to "Fuego", "water" to "Agua",
        "electric" to "Eléctrico", "grass" to "Planta", "ice" to "Hielo",
        "fighting" to "Lucha", "poison" to "Veneno", "ground" to "Tierra",
        "flying" to "Volador", "psychic" to "Psíquico", "bug" to "Bicho",
        "rock" to "Roca", "ghost" to "Fantasma", "dragon" to "Dragón",
        "dark" to "Siniestro", "steel" to "Acero", "fairy" to "Hada"
    )

    suspend fun obtenerRecompensaAleatoria(idForzado: Int? = null): Result<PokemonRecompensaUi> {
        return try {
            val id = idForzado ?: (1..151).random()
            val pokemon = api.obtenerPokemon(id)
            val especie = api.obtenerEspecie(id)

            val numeroFormateado = "#${pokemon.id.toString().padStart(3, '0')}"
            val nombreFormateado = pokemon.name.replaceFirstChar { it.uppercase() }
            val tiposFormateados = pokemon.types.joinToString(" / ") {
                tiposEnEspanol[it.type.name] ?: it.type.name.replaceFirstChar { c -> c.uppercase() }
            }

            val sexoTexto = when {
                especie.genderRate == -1 -> "Sin género"
                especie.genderRate == 0 -> "100% Macho ♂"
                especie.genderRate == 8 -> "100% Hembra ♀"
                else -> if ((0..1).random() == 0) "Macho ♂" else "Hembra ♀"
            }

            val alturaMetros = String.format("%.1f m", pokemon.height / 10.0)
            val pesoKilos = String.format("%.1f kg", pokemon.weight / 10.0)

            val descripcionEs = especie.flavorTextEntries
                .firstOrNull { it.language.name == "es" }
                ?.flavorText
                ?.replace("\n", " ")
                ?.replace("\u000c", " ")
                ?: "Un valioso compañero de estudio descubierto tras completar con éxito tu desafío escolar."

            val imagen = pokemon.sprites.other?.officialArtwork?.frontDefault
                ?: pokemon.sprites.frontDefault
                ?: ""

            Result.success(
                PokemonRecompensaUi(
                    numeroPokedex = numeroFormateado,
                    nombre = nombreFormateado,
                    tipo = tiposFormateados,
                    sexo = sexoTexto,
                    altura = alturaMetros,
                    peso = pesoKilos,
                    descripcion = descripcionEs,
                    imagenUrl = imagen
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}