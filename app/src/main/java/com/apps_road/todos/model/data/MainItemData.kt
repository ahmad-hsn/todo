package com.apps_road.todos.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainItemData(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "detail")
    val detail: String,

    @ColumnInfo(name = "imgPath")
    val imgPath: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}