package anand.textme.crud3.ui.todo_list

import anand.textme.crud3.data.Todo

sealed class TodoListEvent{
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    object OnAddTodoClick: TodoListEvent()
    data class OnDeleteTodoClick(val todo: Todo): TodoListEvent()
    object OnUndoDeleteTodoClick: TodoListEvent()
}