package com.example.bankpage.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bankpage.data.Transaction

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val value: Double,
    val date: String,
    val isExpense: Boolean
)

fun TransactionEntity.toDomain() = Transaction(
    id = id,
    title = title,
    value = value,
    date = date,
    isExpense = isExpense
)

fun Transaction.toEntity() = TransactionEntity(
    id = if (id == 0) 0 else id, // letting room generate if it's 0
    title = title,
    value = value,
    date = date,
    isExpense = isExpense
)
