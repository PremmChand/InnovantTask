import com.example.innovantapp.data.model.Product
import com.example.innovantapp.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
  /*  @GET("productdetails/{categoryId}/{productId}")
    suspend fun getProduct(
        @Path("categoryId") categoryId: Int,
        @Path("productId") productId: Int,
        @Query("lang") lang: String,
        @Query("store") store: String
    ): Product*/

    /*@GET("productdetails/6701/253620?lang=en&store=KWD")
    suspend fun getProduct(): ProductResponse*/

    @GET("productdetails/6701/253620")
    suspend fun getProduct(
        @Query("lang") lang: String = "en",
        @Query("store") store: String = "KWD"
    ): ProductResponse

}