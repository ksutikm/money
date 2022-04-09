package com.example.expensemanager.view.stats

import androidx.lifecycle.ViewModel
import com.example.expensemanager.data.local.ExpenseDao
import com.example.expensemanager.data.model.Expense
import com.example.expensemanager.util.Constants.expenseTags
import java.util.*

class StatsViewModel(
    private val dao: ExpenseDao,
    private val startDate: Date,
    private val endDate: Date
): ViewModel() {



    private var _data: Map<String, Double> = getSumCategories()
    val data: Map<String, Double>
        get() = _data


    private fun getDaoFiltered(tag: String): List<Expense> {
        return dao.findAllByDate(tag, startDate.toString(), endDate.toString())
    }

    private fun getSumByCategory(category: String): Double {
        var sum = 0f
        for (expense in getDaoFiltered(category)) {
            sum = (sum + expense.amount).toFloat()
        }
        return sum.toDouble()
    }

    private fun getSumCategories(): Map<String, Double> {
        val current = mutableMapOf<String, Double>()
        for (category in expenseTags) {
            current.put(category, getSumByCategory(category))
        }
        return current
    }

}
