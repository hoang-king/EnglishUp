package com.example.englishup.core

abstract class Mapper<T> {
    abstract fun toEntity(): T
}
