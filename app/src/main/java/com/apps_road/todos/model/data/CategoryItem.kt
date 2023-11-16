package com.apps_road.todos.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MainItemData::class,
            parentColumns = ["id"],
            childColumns = ["id_fk_main_item"],
            onDelete = CASCADE
        )
    ]
)
data class CategoryItem(
    @ColumnInfo(name = "id_fk_main_item")
    var id_fk_main_item: Long = 0,

    @ColumnInfo(name = "itemName")
    var itemName: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
