package com.example.rollcall.ui.user.mainboard

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemMarkAdapter
import com.example.rollcall.data.model.Mark
import com.example.rollcall.databinding.FragmentMainBoardBinding
import com.example.rollcall.utils.BaseFragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import java.text.DecimalFormat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainBoardFragment : BaseFragment<FragmentMainBoardBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main_board
    }

    lateinit var adapterDiem:ItemMarkAdapter
    val viewModel by viewModels<MainBoardViewModel>()
    override fun onCreateViews() {
        lineChart()
        getDiem()
    }

    private fun lineChart() {
        baseBinding.apply {
            val entries = ArrayList<Entry>()

            entries.add(Entry(0f, 3.2f))
            entries.add(Entry(1f, 2.2f))
            entries.add(Entry(2f, 2.7f))
            entries.add(Entry(3f, 2.8f))
            entries.add(Entry(4f, 2.7f))
            entries.add(Entry(5f, 3.0f))
            val hocki = ArrayList<String>()
            hocki.add("học kì 1")
            hocki.add("học kì 2")
            hocki.add("học kì 3")
            hocki.add("học kì 4")
            hocki.add("học kì 5")
            hocki.add("học kì 6")


            class DataValueFormatter : ValueFormatter() {
                private val format = DecimalFormat("###,##0.00")

                // override this for e.g. LineChart or ScatterChart
                override fun getPointLabel(entry: Entry?): String {
                    return format.format(entry?.y)
                }
            }

            val xAxis = linechart.xAxis
            xAxis.granularity = 1f
            xAxis.setDrawLabels(false)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            val yAxisL = linechart.axisLeft
            yAxisL.setDrawGridLines(false)
            yAxisL.textColor = Color.rgb(175, 175, 175)
            yAxisL.mAxisMaximum = 4.0f
            yAxisL.granularity = 0.1f
            val dataset = LineDataSet(entries, "Điểm Tích Lũy").apply {
                setDrawFilled(true)
                //set.fillColor = Color.rgb(99, 80, 200)
                mode = LineDataSet.Mode.CUBIC_BEZIER
                lineWidth = 3f
                valueFormatter = DataValueFormatter()
                valueTextSize = 8f
                circleRadius = 6f
                circleHoleRadius = 3f
                cubicIntensity = 0.2f
                valueTextColor = Color.rgb(0, 215, 193)
                color = Color.rgb(0, 215, 193)
                setCircleColor(Color.argb(40, 0, 235, 213))
                circleHoleColor = Color.rgb(0, 215, 193)
                fillFormatter = IFillFormatter { _, _ -> linechart.axisLeft.axisMinimum }
                // set color of filled area
                fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_graph)
            }
            val data: ArrayList<ILineDataSet> = ArrayList()
            data.add(dataset)
            val lineData = LineData(data)
            val des = Description()
            des.text = ""

            val legend = linechart.legend
            legend.isEnabled = false
            linechart.apply {
                axisRight.isEnabled = false
                this.data = lineData
                setDrawBorders(false)
//                bd.linechart.setBorderColor(Color.BLACK)
//                bd.linechart.setBorderWidth(1f)
                description = des
                setScaleEnabled(false)
                isDoubleTapToZoomEnabled = false
                setTouchEnabled(true)
                isHighlightPerDragEnabled = false
                isHighlightPerTapEnabled = true
            }
            val mv = CustomMarkerView(
                context,
                R.layout.axis_label,
                hocki,
                linechart.width,
                linechart.height
            )
            linechart.marker = mv
            linechart.invalidate()
        }

    }
    private fun getDiem(){

        val listDiem = mutableListOf<Mark>()
        listDiem.add(Mark("Thực tập","8.3","8.3","B+"))
        listDiem.add(Mark("Tốt nghiệp","","0.0","F"))
        adapterDiem = ItemMarkAdapter {
        }
        baseBinding.recyclerViewDiem.apply {
            adapter = adapterDiem
            layoutManager = LinearLayoutManager(context)
        }
        adapterDiem.submitList(listDiem)
    }
}