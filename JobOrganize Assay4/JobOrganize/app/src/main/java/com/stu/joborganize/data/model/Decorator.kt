package com.stu.joborganize.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "decorators")
@Serializable
data class Decorator(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String,
    val isAvailable: Boolean,
    val jobType: String,
    val availableFrom: String,
    val availableTo: String,
    val contactNumber: String
)
