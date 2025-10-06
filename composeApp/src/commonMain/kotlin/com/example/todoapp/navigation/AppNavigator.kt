package com.example.todoapp.navigation

import cafe.adriel.voyager.navigator.Navigator
import com.example.todoapp.presentation.todoDetail.TodoDetailScreen
import com.example.todoapp.presentation.todoList.TodoListScreen

object AppNavigator {

    /**
     * TODOリスト画面への遷移
     */
    fun navigateToList(navigator: Navigator) {
        navigator.push(TodoListScreen())
    }

    /**
     * TODO詳細画面への遷移
     * @param todoId 表示するTODOのID
     */
    fun navigateToDetail(navigator: Navigator, todoId: Long) {
        navigator.push(TodoDetailScreen(todoId))
    }

    /**
     * 前の画面に戻る
     */
    fun navigateBack(navigator: Navigator) {
        navigator.pop()
    }

    /**
     * ルート画面まで戻る
     */
    fun navigateToRoot(navigator: Navigator) {
        navigator.popUntilRoot()
    }

    /**
     * 現在の画面を置き換えてTODOリスト画面へ遷移
     */
    fun replaceWithList(navigator: Navigator) {
        navigator.replace(TodoListScreen())
    }

    /**
     * 現在の画面を置き換えてTODO詳細画面へ遷移
     * @param todoId 表示するTODOのID
     */
    fun replaceWithDetail(navigator: Navigator, todoId: Long) {
        navigator.replace(TodoDetailScreen(todoId))
    }
}
