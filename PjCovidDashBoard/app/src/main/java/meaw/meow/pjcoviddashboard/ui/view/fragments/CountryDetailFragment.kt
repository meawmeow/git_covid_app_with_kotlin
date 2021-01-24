package meaw.meow.pjcoviddashboard.ui.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import meaw.meow.pjcoviddashboard.R
import meaw.meow.pjcoviddashboard.databinding.FragmentCountryDetailBinding
import meaw.meow.pjcoviddashboard.ui.view.activity.MainActivity
import meaw.meow.pjcoviddashboard.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_country_detail.*


class CountryDetailFragment : Fragment(R.layout.fragment_country_detail) {

    val nameFragment = "CountryDetailFragment"
    lateinit var mainViewModel: MainViewModel
    lateinit var binding : FragmentCountryDetailBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        binding.mainViewModel = mainViewModel
        mainViewModel.setTitleBar(mainViewModel.countriesResponseItem.value?.country)

        btn_Historical.setOnClickListener {
            loadFragment(HistoricalFragment(),HistoricalFragment().nameFragment)
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainViewModel.setTitleBar("Countries All")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
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




}