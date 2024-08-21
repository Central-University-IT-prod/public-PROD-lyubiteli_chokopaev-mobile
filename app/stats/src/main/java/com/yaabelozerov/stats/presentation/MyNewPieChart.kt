package com.yaabelozerov.stats.presentation

import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.color.MaterialColors

@Composable
fun MyPieChart(langs: Map<String, Double>) {
    AndroidView(factory = { context ->
        PieChart(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            this.description.isEnabled = false
            this.isDrawHoleEnabled = false
            this.legend.isEnabled = false
            this.legend.textSize = 14F
            this.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            this.setEntryLabelColor(Color.BLACK)
            this.setUsePercentValues(true)
        }
    }, modifier = Modifier.wrapContentSize().padding(5.dp), update = {
        updatePieChartWithData(it, langs)
    })
}

fun updatePieChartWithData(
    chart: PieChart, data: Map<String, Double>
) {
    val entries = ArrayList<PieEntry>()
    for (i in data.keys) {
        val item = data[i]
        entries.add(PieEntry(item?.toFloat()!! * 100, i))
    }
    val ds = PieDataSet(entries, "")
    ds.colors = arrayListOf(
        Color.rgb(208, 219, 151),
        Color.rgb(105, 181, 120),
        Color.rgb(132, 169, 192),
        Color.rgb(204, 151, 142),
        Color.rgb(243, 156, 107),
        Color.rgb(242, 132, 130),
        Color.rgb(206, 132, 173)
    )
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.sliceSpace = 2f
    ds.valueTextColor = Color.BLACK
    ds.valueTextSize = 18f
    ds.valueTypeface = Typeface.DEFAULT_BOLD
    val d = PieData(ds)
    chart.data = d
    chart.invalidate()
}
