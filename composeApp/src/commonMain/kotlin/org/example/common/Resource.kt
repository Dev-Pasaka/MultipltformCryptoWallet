package org.example.common



sealed class Resource<T>(
    val data:T? = null,
    val message:String? = null,
) {
    class Success<T>(data: T, message: String):Resource<T>(data = data, message)
    class Error<T>(message: String, data: T? = null):Resource<T>(message = message,data = data)
    class Loading<T>(data: T? = null, message: String = "Loading ..."):Resource<T>(data, message)
}