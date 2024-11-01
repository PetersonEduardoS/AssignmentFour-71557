package com.stu.joborganize.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stu.joborganize.data.model.Decorator
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservedDecoratorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecorator(decorator: Decorator)

    @Query("SELECT * FROM decorators")
    fun getAll(): Flow<List<Decorator>>

    @Query("DELETE FROM decorators WHERE id = :decoratorId")
    suspend fun deleteDecorator(decoratorId: Int)

}