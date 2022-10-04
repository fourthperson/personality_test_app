package com.iak.perstest.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "past_quiz")
data class PastQuiz(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "outcome") val outcome: String,
    @ColumnInfo(name = "data") val date: String
)