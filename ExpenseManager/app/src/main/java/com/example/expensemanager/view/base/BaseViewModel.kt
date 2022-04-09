package com.example.expensemanager.view.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope
import com.example.expensemanager.data.model.Expense
import com.example.expensemanager.data.repo.ExpenseRepository
import com.example.expensemanager.util.Constants
import com.example.expensemanager.util.DetailState
import com.example.expensemanager.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 ### `Common class for using viewModel classes`
 */

@HiltViewModel
class BaseViewModel @Inject  constructor(
    application: Application,
    private val repo: ExpenseRepository,
) : AndroidViewModel(application)
{
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _detailState = MutableStateFlow<DetailState>(DetailState.Loading)


    val viewState: StateFlow<ViewState> = _viewState
    val detailState : StateFlow<DetailState> = _detailState


    fun insertExpense(expense: Expense) = viewModelScope.launch {
        repo.insert(expense)
    }


    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        repo.delete(expense)
    }

    fun getById(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading
        repo.getById(id).collect { result: Expense? ->
            if (result != null) {
                _detailState.value = DetailState.Success(result)
            }
        }
    }
    init {
        //Does not block a main thread, dispatches on background thread
        viewModelScope.launch {
            repo.getAllItem().collect { result ->
                if(result.isNullOrEmpty()) {
                    _viewState.value = ViewState.Empty
                }
                else {
                    _viewState.value = ViewState.Success(result)
                }
            }
        }
    }

//    private var _data: Map<String, Double> = getSumCategories()
//    val data: Map<String, Double>
//        get() = _data


    private fun getDaoFiltered(tag: String, startDate: String, endDate: String): List<Expense> {
        return repo.findByFilter(tag, startDate.toString(), endDate.toString())
    }

    private fun getSumByCategory(category: String, startDate: String, endDate: String): Double {
        var sum = 0f
        for (expense in getDaoFiltered(category, startDate, endDate)) {
            sum = (sum + expense.amount).toFloat()
        }
        return sum.toDouble()
    }

    fun getSumCategories(startDate: String, endDate: String): Map<String, Double> {
        val current = mutableMapOf<String, Double>()
        for (category in Constants.expenseTags) {
            current.put(category, getSumByCategory(category, startDate, endDate))
        }
        return current
    }


}



