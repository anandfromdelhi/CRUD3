package anand.textme.crud3.ui.todo_list

import anand.textme.crud3.data.Todo
import anand.textme.crud3.data.TodoRepository
import anand.textme.crud3.util.Routes
import anand.textme.crud3.util.UiEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    val todos = repository.getTodos()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnTodoClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(route = Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
                }
            }
            is TodoListEvent.OnAddTodoClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(route = Routes.ADD_EDIT_TODO))
                }
            }
            is TodoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackbar(message = "Todo Deleted", action = "Undo"))
                }
            }
            is TodoListEvent.OnUndoDeleteTodoClick -> {
                deletedTodo?.let { todo -> {
                        viewModelScope.launch {
                            repository.insertTodo(todo)
                        }
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}