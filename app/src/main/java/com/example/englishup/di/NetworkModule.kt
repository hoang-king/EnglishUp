package com.example.englishup.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

import com.example.englishup.adaptors.datasources.remote.GrammarApiService
import com.example.englishup.adaptors.datasources.remote.ListeningApiService
import com.example.englishup.adaptors.datasources.remote.QuizApiService
import com.example.englishup.adaptors.datasources.remote.ReadingApiService
import com.example.englishup.adaptors.datasources.remote.VocabularyApiService
import com.example.englishup.adaptors.datasources.remote.TranslationApiService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DictionaryRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QuizRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GrammarRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ListeningRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReadingRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TranslationRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // 1. Dictionary & Vocabulary (Giữ nguyên vì cái này chuẩn)
    @Provides
    @Singleton
    @DictionaryRetrofit
    fun provideDictionaryRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 1. Grammar - Kiểm tra lỗi ngữ pháp
    @Provides
    @Singleton
    @GrammarRetrofit
    fun provideGrammarRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.languagetoolplus.com/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 2. Reading - Lấy nội dung đọc ngẫu nhiên
    @Provides
    @Singleton
    @ReadingRetrofit
    fun provideReadingRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.quotable.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 3. Listening - Tận dụng API từ điển để lấy file âm thanh của từ
    @Provides
    @Singleton
    @ListeningRetrofit
    fun provideListeningRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 4. Quiz (Dùng Open Trivia DB)
    @Provides
    @Singleton
    @QuizRetrofit
    fun provideQuizRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 5. Translation (Dịch thuật - MyMemory)
    @Provides
    @Singleton
    @TranslationRetrofit
    fun provideTranslationRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mymemory.translated.net/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGrammarApiService(@GrammarRetrofit retrofit: Retrofit): GrammarApiService {
        return retrofit.create(GrammarApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideVocabularyApiService(@DictionaryRetrofit retrofit: Retrofit): VocabularyApiService {
        return retrofit.create(VocabularyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizApiService(@QuizRetrofit retrofit: Retrofit): QuizApiService {
        return retrofit.create(QuizApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideListeningApiService(@ListeningRetrofit retrofit: Retrofit): ListeningApiService {
        return retrofit.create(ListeningApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideReadingApiService(@ReadingRetrofit retrofit: Retrofit): ReadingApiService {
        return retrofit.create(ReadingApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTranslationApiService(@TranslationRetrofit retrofit: Retrofit): TranslationApiService {
        return retrofit.create(TranslationApiService::class.java)
    }
}
