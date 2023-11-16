package com.apps_road.todos.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MainItemData(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "detail")
    val detail: String,

    @ColumnInfo(name = "imgPath")
    val imgPath: String,
): Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}