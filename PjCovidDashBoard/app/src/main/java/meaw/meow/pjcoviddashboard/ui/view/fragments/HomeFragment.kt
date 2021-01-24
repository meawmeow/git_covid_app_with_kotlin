package meaw.meow.pjcoviddashboard.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import meaw.meow.pjcoviddashboard.R
import meaw.meow.pjcoviddashboard.data.model.response.coronasummary.CoronaSummaryResponse
import meaw.meow.pjcoviddashboard.databinding.FragmentHomeBinding
import meaw.meow.pjcoviddashboard.ui.view.activity.MainActivity
import meaw.meow.pjcoviddashboard.ui.viewmodel.MainViewModel
import meaw.meow.pjcoviddashboard.utils.*
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import kotlinx.android.synthetic.main.fragment_countries_all.*
import kotlinx.android.synthetic.main.fragment_countries_all.progress_bar
import kotlinx.android.synthetic.main.fragment_historical.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    val nameFragment = "HomeFragment"
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        mainViewModel.setTitleBar("Coronavirus Summary")
        setupCoronaSummaryObserver()

        mainViewModel.fetchCoronaSummary()
    }

    fun setupCoronaSummaryObserver() {
        mainViewModel.coronaSummary.observe(this, Observer {
            when (it.status) {
                StatusResource.LOADING -> {
                    progress_bar.show()
                }
                StatusResource.SUCCESS -> {
                    progress_bar.hide()
                    binding.coronaSummary = it.data
                    it.data?.let { it1 -> getSummaryDataChart(it1) }
                    //context?.toast("Success")
                }
                StatusResource.ERROR -> {
                    progress_bar.hide()
                    context?.toast("${it.message}")
                }

            }
        })
    }

    fun getSummaryDataChart(data: CoronaSummaryResponse) {
        var valueBarList: ArrayList<Int> = arrayListOf()
        var nameBarList: ArrayList<String> = arrayListOf()


        valueBarList.add(data.cases)
        valueBarList.add(data.recovered)
        valueBarList.add(data.deaths)

        nameBarList.add("Total Cases")
        nameBarList.add("Total Recovered")
        nameBarList.add("Total Deaths")

        val arrayValueBar: Array<Int> = valueBarList.toTypedArray()
        val arrayNameBar: Array<String> = nameBarList.toTypedArray()


        var titleStyle =
            AAStyle().color(getHexColor(ContextCompat.getColor(activity!!, R.color.teal_700)))
        var subtitleStyle = AAStyle().color("#fe117c")

        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            //.title("")
            .axesTextColor(
                getHexColor(ContextCompat.getColor(activity!!, R.color.teal_700))
            )
            .titleStyle(titleStyle)
            //.subtitle("Historical")
            .subtitleStyle(subtitleStyle)
            .backgroundColor("#ffffff")//4b2b7f
            .dataLabelsEnabled(true)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Coronavirus Summary Cases")
                        .data(arrayValueBar as Array<Any>)
                        .color(getHexColor(ContextCompat.getColor(activity!!, R.color.barColumn)))
                        .dataLabels(
                            AADataLabels().color(
                                getHexColor(
                                    ContextCompat.getColor(
                                        activity!!,
                                        R.color.summary
                                    )
                                )
                            )
                        )
                )
            )
        aaChartModel
            .categories(
                arrayNameBar
            )
            .colorsTheme(arrayOf("#fe117c"))
            .animationType(AAChartAnimationType.EaseInCubic)
            .animationDuration(1200)


        aa_chart_view_summary?.aa_drawChartWithChartModel(aaChartModel)
    }


}