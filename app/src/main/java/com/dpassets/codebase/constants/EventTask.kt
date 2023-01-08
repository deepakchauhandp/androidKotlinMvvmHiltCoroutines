package com.dpassets.codebase.constants

sealed class EventTask <T> (
    val data: T? = null,
    val status: Status,
    val task: Task,
    val message: String? = null) {

    class Success<T>(data: T, task: Task) : EventTask<T>(data, Status.SUCCESS, task, null)

    class Error<T>(msg: String, status: Status, task: Task) : EventTask<T>(null, status, task, msg)

    class Loading<T> (task: Task) : EventTask<T>(null,  Status.LOADING, task, null)
}