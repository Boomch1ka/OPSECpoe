package com.example.budget_app.activities

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budget_app.R
import com.example.budget_app.data.BudgetDatabase
import com.example.budget_app.data.Category
import com.example.budget_app.data.CategorySpendingSummary
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class GraphActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var summaryText: TextView
    private lateinit var selectMonthButton: Button
    private lateinit var db: BudgetDatabase

    private var userId: Long = -1L
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        userId = intent.getLongExtra("userId", -1L)
        if (userId == -1L) {
            finish()
            return
        }

        barChart = findViewById(R.id.barChart)
        summaryText = findViewById(R.id.summaryText)
        selectMonthButton = findViewById(R.id.selectMonthButton)
        db = BudgetDatabase.getDatabase(this)

        val back=findViewById<Button>(R.id.backBtn)
        back.setOnClickListener {
            finish()
        }
        // Default to current month
        val calendar = Calendar.getInstance()
        selectedMonth = calendar.get(Calendar.MONTH)
        selectedYear = calendar.get(Calendar.YEAR)

        selectMonthButton.setOnClickListener {
            showMonthPicker()
        }

        loadSpendingDataForMonth(selectedMonth, selectedYear)
    }



    private fun showMonthPicker() {
        val months = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        val calendar = Calendar.getInstance()

        val dialogView = layoutInflater.inflate(R.layout.dialog_month_year_picker, null)
        val monthPicker = dialogView.findViewById<NumberPicker>(R.id.monthPicker)
        val yearPicker = dialogView.findViewById<NumberPicker>(R.id.yearPicker)

        monthPicker.minValue = 0
        monthPicker.maxValue = 11
        monthPicker.displayedValues = months
        monthPicker.value = selectedMonth

        yearPicker.minValue = 2000
        yearPicker.maxValue = calendar.get(Calendar.YEAR)
        yearPicker.value = selectedYear

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setView(dialogView)
            .setTitle("Select Month and Year")
            .setPositiveButton("OK") { _, _ ->
                selectedMonth = monthPicker.value
                selectedYear = yearPicker.value
                loadSpendingDataForMonth(selectedMonth, selectedYear)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun loadSpendingDataForMonth(month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        val startDate = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = calendar.timeInMillis

        lifecycleScope.launch {
            val categories = withContext(Dispatchers.IO) {
                db.categoryDao().getCategoriesByUser(userId)
            }

            val summaries = mutableListOf<CategorySpendingSummary>()
            withContext(Dispatchers.IO) {
                for (cat in categories) {
                    val total = db.expenseDao().getTotalSpentByCategory(
                        userId = userId,
                        categoryId = cat.categoryId,
                        startDate = startDate,
                        endDate = endDate
                    ) ?: 0.0
                    summaries.add(CategorySpendingSummary(cat.categoryId, total))
                }
            }

            drawChart(categories, summaries)
        }
    }

    private fun drawChart(categories: List<Category>, summaries: List<CategorySpendingSummary>) {
        val entriesSpent = ArrayList<BarEntry>()
        val entriesMin = ArrayList<BarEntry>()
        val entriesMax = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        var overGoal = 0
        var underGoal = 0

        categories.forEachIndexed { index, category ->
            val spent = summaries.find { it.categoryId == category.categoryId }?.totalSpent ?: 0.0
            entriesSpent.add(BarEntry(index.toFloat(), spent.toFloat()))
            entriesMin.add(BarEntry(index.toFloat(), category.minGoal.toFloat()))
            entriesMax.add(BarEntry(index.toFloat(), category.maxGoal.toFloat()))
            labels.add(category.name)

            if (spent > category.maxGoal) overGoal++
            else if (spent < category.minGoal) underGoal++
        }

        val spentSet = BarDataSet(entriesSpent, "Spent").apply { color = Color.BLUE }
        val minSet = BarDataSet(entriesMin, "Min Goal").apply { color = Color.GREEN }
        val maxSet = BarDataSet(entriesMax, "Max Goal").apply { color = Color.RED }

        val data = BarData(spentSet, minSet, maxSet)

        // Group bar spacing values
        val groupSpace = 0.3f
        val barSpace = 0.05f
        val barWidth = 0.2f
        val groupCount = categories.size

        data.barWidth = barWidth
        barChart.data = data

        val groupWidth = data.getGroupWidth(groupSpace, barSpace)

        barChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labels)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            isGranularityEnabled = true
            axisMinimum = 0f
            axisMaximum = 0f + groupCount * groupWidth
            setCenterAxisLabels(true)
            setDrawGridLines(false)
            labelRotationAngle = -20f
        }

        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.setVisibleXRangeMaximum(groupCount.toFloat())
        barChart.setFitBars(true)
        barChart.groupBars(0f, groupSpace, barSpace)
        barChart.animateY(1000)
        barChart.invalidate()

        summaryText.text =
            "You are over budget in $overGoal categories and under budget in $underGoal categories."
    }
}
