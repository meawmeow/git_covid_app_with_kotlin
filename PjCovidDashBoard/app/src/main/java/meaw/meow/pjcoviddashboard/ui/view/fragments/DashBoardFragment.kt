package meaw.meow.pjcoviddashboard.ui.view.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import meaw.meow.pjcoviddashboard.R
import meaw.meow.pjcoviddashboard.data.model.response.statthcasesum.Gender
import meaw.meow.pjcoviddashboard.ui.view.activity.MainActivity
import meaw.meow.pjcoviddashboard.ui.viewmodel.MainViewModel
import meaw.meow.pjcoviddashboard.utils.*
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import kotlinx.android.synthetic.main.fragment_dash_board.*
import kotlinx.coroutines.launch


class DashBoardFragment : Fragment(R.layout.fragment_dash_board) {

    val nameFragment = "DashBoardFragment"
    lateinit var mainViewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        mainViewModel.setTitleBar("Cases in Thailand")
        setupCaseSumObserver()
        setupDataChartObserver()
        mainViewModel.fetchCasesSumThai()


        btn_Next.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                mainViewModel.getPageProvinceData()
            }

        }
        btn_Back.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                mainViewModel.getPageProvinceData(false)
            }

        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    fun getDrawBarChar(gender: Gender,dayUpdate:String,arrayValueBar: Array<Int> ,arrayNameBar: Array<String>){

        var titleStyle = AAStyle().color(getHexColor(ContextCompat.getColor(activity!!, R.color.teal_700)))
        var subtitleStyle = AAStyle().color("#fe117c")
        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Bar)
            .title("Cases Update: ${dayUpdate}")
            .titleStyle(titleStyle)
            .subtitle("Gender Male:${gender.Male} / Female:${gender.Female} / Unknown:${gender.Unknown}")
            .subtitleStyle(subtitleStyle)
            .axesTextColor(
                getHexColor(ContextCompat.getColor(activity!!, R.color.teal_700))
            )
            .backgroundColor("#ffffff")//4b2b7f
            .dataLabelsEnabled(true)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Province")
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


        aa_chart_view_case_thai?.aa_drawChartWithChartModel(aaChartModel)
    }


    private fun setupDataChartObserver(){
        mainViewModel.dataChart.observe(this, Observer {
            when (it.status) {
                StatusResource.LOADING -> {
                    progress_bar_cases_thai.show()
                }
                StatusResource.SUCCESS -> {
                    progress_bar_cases_thai.hide()
                    if (it.data != null && it.data.nameBarList.size != 0) {
                        val arrayValueBar: Array<Int> = it.data.valueBarList.toTypedArray()
                        val arrayNameBar: Array<String> = it.data.nameBarList.toTypedArray()
                        getDrawBarChar(it.data.gender,it.data.dayUpdate,arrayValueBar,arrayNameBar)
                    }
                }
                StatusResource.NOTHING -> {
                    progress_bar_cases_thai.hide()
                }
            }
        })
    }


    private fun setupCaseSumObserver() {
        mainViewModel.caseSumThailad.observe(this, Observer {
            when (it.status) {
                StatusResource.LOADING -> {
                    progress_bar_cases_thai.show()
                }
                StatusResource.SUCCESS -> {
                    progress_bar_cases_thai.hide()
                    if (it.data != null && it.data.provinceList.size != 0) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            mainViewModel.getPageProvinceData()
                        }

                    }

                }
                StatusResource.ERROR -> {
                    progress_bar_cases_thai.hide()
                    context?.toast("${it.message}")
                }
            }
        })
    }


}