package com.rahat.blog_test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rahat.blog_test.data.Blog
import com.rahat.blog_test.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Blog::class], version = 6)
abstract class BlogDatabase : RoomDatabase() {

    abstract fun blogDao(): BlogDao

    class Callback @Inject constructor(
        private val database: Provider<BlogDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)


        }
    }
}