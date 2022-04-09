package com.example.expensemanager.view.stats

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.expensemanager.R
//import com.example.expensemanager.data.repo.ExpenseRepository
import com.example.expensemanager.view.base.BaseFragment
import com.example.expensemanager.view.base.BaseViewModel
import com.example.expensemanager.databinding.FragmentStatsBinding
import com.example.expensemanager.util.modifyIntoDatePicker
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList

class StatsFragment() : BaseFragment<FragmentStatsBinding, BaseViewModel>() {

    
    override val viewModel: BaseViewModel by activityViewModels()
//    override val viewModel = StatsViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        createPieChart()


        val foo = binding.button.setOnClickListener {
            binding.startDate.modifyIntoDatePicker(
                requireContext(),
                "dd/MM/E",
                Date()
            )
            binding.endDate.modifyIntoDatePicker(
                requireContext(),
                "dd/MM/E",
                Date()
            )

            val startDate = binding.startDate.text.toString()
            val endDate = binding.endDate.text.toString()
            pieChart(startDate, endDate)
        }

    }




    private fun createPieChart() {
        // xvalues
        val xvalues = ArrayList<String >()
        xvalues.add("Coal")
        xvalues.add("Petrolium")
        xvalues.add("Natural Gas")
        xvalues.add("Renewable Energy")
        xvalues.add("Nuclear")

        // yvalues

        val pieChartEntry = mutableListOf<PieEntry>()
        pieChartEntry.add(PieEntry(23.5f))
        pieChartEntry.add(PieEntry(45.5f))
        pieChartEntry.add(PieEntry(68.5f))
//        pieChartEntry.add( Entry(23.5f, 0f ))
//        pieChartEntry.add( Entry(45.5f, 1f ))
//        pieChartEntry.add( Entry(68.5f, 2f ))

        // fill the chart
        val pieDataSet = PieDataSet(pieChartEntry," Consumption")

        pieDataSet.color= resources.getColor(R.color.crayola)


        pieDataSet.sliceSpace=3f
        val data = PieData(pieDataSet)
        binding.pieChart.data = data
        binding.pieChart.holeRadius = 5f
//        binding.pieChart.setBackgroundColor(resources.getColor(R.color.white))
    }

    private fun pieChart(startDate: String, endDate: String) {
        val pieChart: PieChart = binding.pieChart
        pieChart.description.isEnabled = false

        pieChart.dragDecelerationFrictionCoef = 0.99f

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.parseColor("#FCCE5C"))
        pieChart.transparentCircleRadius = 60f
        pieChart.centerText = "Good Job"
        pieChart.setCenterTextColor(Color.parseColor("#533D2B"))
        pieChart.setCenterTextSize(50f)



//        val categories = viewModel.getSumCategories(startDate.toString(), endDate.toString())
        val pieData = mutableListOf<PieEntry>()
//        for ((key, value) in categories) {
//            pieData.add(PieEntry(value, key))
//        }

        pieData.add(PieEntry(25f, "foo"))
        pieData.add(PieEntry(75f, "cool" ))


        val dataSet = PieDataSet(pieData, "Hello")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = ColorTemplate.JOYFUL_COLORS.toMutableList()

        pieChart.data = PieData(dataSet)
        pieChart.data.setValueTextColor(Color.parseColor("#533D2B"))
        pieChart.data.setValueTextSize(10f)

        pieChart.animateXY(2000,2000)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentStatsBinding.inflate(inflater, container, false)

//    override fun PieEntry(value: Double, key: String): PieEntry {
//        return PieEntry(value, key)
//    }
}
