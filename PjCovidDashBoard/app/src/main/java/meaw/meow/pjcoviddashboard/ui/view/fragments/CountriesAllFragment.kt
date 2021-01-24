package meaw.meow.pjcoviddashboard.ui.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import meaw.meow.pjcoviddashboard.R
import meaw.meow.pjcoviddashboard.adapter.AdapterCountries
import meaw.meow.pjcoviddashboard.adapter.CountriesItemClickListenner
import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponseItem
import meaw.meow.pjcoviddashboard.ui.view.activity.MainActivity
import meaw.meow.pjcoviddashboard.ui.viewmodel.MainViewModel
import meaw.meow.pjcoviddashboard.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_countries_all.*


class CountriesAllFragment : Fragment(R.layout.fragment_countries_all), CountriesItemClickListenner {

    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: AdapterCountries

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        mainViewModel.setTitleBar("Countries All")

        adapter = AdapterCountries(this)
        recyc_countries.layoutManager = LinearLayoutManager(activity)
        recyc_countries.adapter = adapter


        setupObserver()
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
    private fun loadFragment(fragment: Fragment,nameFragment:String){
        if(fragment != null) {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                add(R.id.flFragment, fragment)
                addToBackStack(nameFragment)
                commit()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.setValue(Resource.nothing())
        mainViewModel.searchCountry(null)

    }

    override fun onStart() {
        super.onStart()
        mainViewModel.fetchCountries()
    }


    private fun setupObserver() {
        mainViewModel.countries.observe(this, Observer {
            when (it.status) {
                StatusResource.LOADING -> {
                    progress_bar.show()
                }
                StatusResource.SUCCESS -> {
                    progress_bar.hide()
                    adapter.submitList(it.data){
                        recyc_countries.scrollToPosition(0)
                    }
                    //context?.toast("Success : ${it.data?.size}")
                }
                StatusResource.ERROR -> {
                    progress_bar.hide()
                    context?.toast("${it.message}")
                }
                StatusResource.QUERY_STR -> {
                    adapter.submitList(it.data){
                        recyc_countries.scrollToPosition(0)
                    }


                }
                StatusResource.NOTHING -> {

                }

            }
        })
    }

    override fun onCountriesItemClick(countriesResponseItem: CountriesResponseItem) {
        //context?.toast("${countriesResponseItem.country}")
        mainViewModel.setSelectedCountriesItem(countriesResponseItem).apply {
            loadFragment(CountryDetailFragment(),CountryDetailFragment().nameFragment)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mdeathMax ->{
                mainViewModel.sortDeathMaxList()
            }
            R.id.mdeathMin ->{
               mainViewModel.sortDeathMinList()
            }
            R.id.mcasesMax ->{
                mainViewModel.sortCasesMaxList()
            }
            R.id.mcasesMin ->{
                mainViewModel.sortCasesMinList()
            }
            R.id.mnewcaseMax ->{
                mainViewModel.sortNewCasesMaxList()
            }
            R.id.mnewcaseMin ->{
                mainViewModel.sortNewCasesMinList()
            }
        }
        return true

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.countries_sort_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    recyc_countries.scrollToPosition(0)
                    mainViewModel.searchCountry(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText != "") {
                    mainViewModel.searchCountry(newText)
                }else{
                    mainViewModel.searchCountry(null)
                    recyc_countries.scrollToPosition(0)
                }

                return true
            }
        })
    }

}