package com.example.utilities

sealed class ResourceState<T> {
    class LOADING<T> : ResourceState<T>()
    data class SUCCESS<T>(val data: T) : ResourceState<T>()
    data class ERROR<T>(val error: String) : ResourceState<T>()

}