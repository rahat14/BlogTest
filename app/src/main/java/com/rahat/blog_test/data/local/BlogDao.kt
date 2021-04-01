package com.rahat.blog_test.data.local

import androidx.room.*
import com.rahat.blog_test.data.Blog
import kotlinx.coroutines.flow.Flow

@Dao
interface BlogDao {
    @Transaction // multiple query happening here
    @Query("SELECT * FROM blog_list_table ORDER BY p_id DESC")
    fun getBlogList(): Flow<List<Blog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blog: Blog)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(blog: Blog)

    @Delete
    suspend fun delete(blog: Blog)
}