package anand.textme.crud3

import anand.textme.crud3.ui.add_edit_todo.AddEditTodoScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import anand.textme.crud3.ui.theme.CRUD3Theme
import anand.textme.crud3.ui.todo_list.TodoListScreen
import anand.textme.crud3.util.Routes
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CRUD3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.TODO_LIST) {
                        composable(route = Routes.TODO_LIST) {
                            TodoListScreen(onNavigate = {
                                navController.navigate(it.route)
                            })
                        }
                        composable(route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                            arguments = listOf(
                                navArgument(name = "todoId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditTodoScreen(onPopBackStack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}

