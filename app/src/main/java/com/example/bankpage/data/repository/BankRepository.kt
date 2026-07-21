package com.example.bankpage.data.repository

import com.example.bankpage.data.Transaction
import com.example.bankpage.data.db.TransactionDao
import com.example.bankpage.data.db.toDomain
import com.example.bankpage.data.db.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BankRepository(private val transactionDao: TransactionDao) {

    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()
        .map { entities -> entities.map { it.toDomain() } }

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }
}
