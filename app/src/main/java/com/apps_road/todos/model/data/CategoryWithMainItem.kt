package com.apps_road.todos.model.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class CategoryWithMainItem(
    @Embedded
    var mainItemData: MainItemData,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    var categoryItems: List<CategoryItem>
)
