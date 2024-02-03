package com.example.hairbookfront.util

/**
 * Represents the state of a resource.
 */
sealed class ResourceState<T> {
    class LOADING<T> : ResourceState<T>()
    data class SUCCESS<T>(val data: T) : ResourceState<T>()
    data class ERROR<T>(val error: String) : ResourceState<T>()

}