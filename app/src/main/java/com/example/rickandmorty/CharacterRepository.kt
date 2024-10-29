
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepository {
    suspend fun getCharacters(): List<Character> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.api.getCharacters().results
        }
    }
}
        