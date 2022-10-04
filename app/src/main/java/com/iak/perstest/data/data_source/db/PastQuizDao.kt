package com.iak.perstest.data.data_source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.iak.perstest.data.entity.PastQuiz

@Dao
interface PastQuizDao {
    @Query("select * from past_quiz order by id desc")
    fun getAll(): List<PastQuiz>

    @Insert
    fun insert(quiz: PastQuiz)
}