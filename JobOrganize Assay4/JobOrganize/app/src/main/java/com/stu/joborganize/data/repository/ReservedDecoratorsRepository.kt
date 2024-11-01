package com.stu.joborganize.data.repository

import com.stu.joborganize.data.database.ReservedDecoratorsDao
import com.stu.joborganize.data.model.Decorator

class ReservedDecoratorsRepository(private val reservedDecoratorsDao: ReservedDecoratorsDao) {

    fun getAllDecorators() = reservedDecoratorsDao.getAll()

    suspend fun insertDecorator(decorator: Decorator) = reservedDecoratorsDao.insertDecorator(decorator)

    suspend fun deleteDecorator(decoratorId: Int) = reservedDecoratorsDao.deleteDecorator(decoratorId)
}