
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.ui.unit.dp

@Composable
fun CharacterListScreen(navController: NavController, viewModel: CharacterViewModel) {
    val characters by viewModel.characters.collectAsState()

    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
        items(characters) { character ->
            CharacterCard(character) {
                navController.navigate("characterDetails/${character.id}")
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberImagePainter(data = character.image),
                contentDescription = character.name,
                modifier = Modifier.size(100.dp)
            )
            Text(text = character.name, style = MaterialTheme.typography.h6)
            Text(text = character.species, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun CharacterDetailsScreen(characterId: Int, viewModel: CharacterViewModel) {
    val character = viewModel.characters.collectAsState().value.find { it.id == characterId }

    character?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = it.image),
                contentDescription = it.name,
                modifier = Modifier.size(200.dp)
            )
            Text(text = "Name: ${it.name}", style = MaterialTheme.typography.h5)
            Text(text = "Species: ${it.species}", style = MaterialTheme.typography.body1)
            Text(text = "Status: ${it.status}", style = MaterialTheme.typography.body1)
            Text(text = "Gender: ${it.gender}", style = MaterialTheme.typography.body1)
            Text(text = "Location: ${it.location.name}", style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun MainScreen(viewModel: CharacterViewModel = CharacterViewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "characterList") {
        composable("characterList") {
            CharacterListScreen(navController, viewModel)
        }
        composable("characterDetails/{characterId}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")?.toInt() ?: 0
            CharacterDetailsScreen(characterId, viewModel)
        }
    }
}
        