package meaw.meow.pjcoviddashboard.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import meaw.meow.pjcoviddashboard.R
import meaw.meow.pjcoviddashboard.data.model.response.historical.HistoricalResponse
import meaw.meow.pjcoviddashboard.ui.view.activity.MainActivity
import meaw.meow.pjcoviddashboard.ui.viewmodel.MainViewModel
import meaw.meow.pjcoviddashboard.utils.*
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import kotlinx.android.synthetic.main.fragment_countries_all.*
import kotlinx.android.synthetic.main.fragment_countries_all.progress_bar
import kotlinx.android.synthetic.main.fragment_historical.*


class HistoricalFragment : Fragment(R.layout.fragment_historical) {

    val nameFragment = "HistoricalFragment"
    lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        mainViewModel.setTitleBar("Historical")
        setupObserver()
        mainViewModel.fetchHistorical()
    }
    private fun setupObserver() {
        mainViewModel.historical.observe(this, Observer {
            when (it.status) {
                StatusResource.LOADING -> {
                    progress_bar.show()
                }
                StatusResource.SUCCESS -> {
                    progress_bar.hide()
                    getHistoricalData(it.data!!)
                }
                StatusResource.ERROR -> {
                    progress_bar.hide()
                    context?.toast("${it.message}")
                }
            }
        })
    }

    fun getHistoricalData(data: HistoricalResponse) {
        var valueBarList: ArrayList<Int> = arrayListOf()
        var nameBarList: ArrayList<String> = arrayListOf()

        for (d in data.timeline.cases.entrySet()) {
            valueBarList.add(d.value.asInt)
            nameBarList.add("${d.key}")
        }
        val arrayValueBar: Array<Int> = valueBarList.toTypedArray()
        val arrayNameBar: Array<String> = nameBarList.toTypedArray()

        var titleStyle = AAStyle().color(getHexColor(ContextCompat.getColor(activity!!, R.color.teal_700)))
        var subtitleStyle = AAStyle().color("#fe117c")
        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Bar)
            .title("Coronavirus").axesTextColor(
                getHexColor(ContextCompat.getColor(activity!!, R.color.teal_700))
            )
            .titleStyle(titleStyle)
            .subtitle("Historical")
            .subtitleStyle(subtitleStyle)
            .backgroundColor("#ffffff")//4b2b7f
            .dataLabelsEnabled(true)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("${data.country}")
                        .data(arrayValueBar as Array<Any>)
                )
            )
        aaChartModel
            .categories(
                arrayNameBar
            )
            .colorsTheme(arrayOf("#fe117c"))
            .animationType(AAChartAnimationType.EaseInCubic)
            .animationDuration(1200)


        aa_chart_view?.aa_drawChartWithChartModel(aaChartModel)
    }

}