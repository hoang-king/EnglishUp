package com.example.englishup.core

interface BaseRepository<T> {
    suspend fun getAll(): List<T>
    suspend fun getById(id: String): T?
    suspend fun insert(item: T): Long
    suspend fun update(item: T)
    suspend fun delete(item: T)
}
