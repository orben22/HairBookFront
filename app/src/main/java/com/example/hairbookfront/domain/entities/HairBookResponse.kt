package com.example.hairbookfront.domain.entities

data class HairBookResponse(
    val message: String,
    val data: Any?,
    val state: Int
)