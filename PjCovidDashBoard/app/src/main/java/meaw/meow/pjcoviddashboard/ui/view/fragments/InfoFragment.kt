package meaw.meow.pjcoviddashboard.ui.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import meaw.meow.pjcoviddashboard.R
import meaw.meow.pjcoviddashboard.ui.view.activity.MainActivity
import meaw.meow.pjcoviddashboard.ui.viewmodel.MainViewModel
import meaw.meow.pjcoviddashboard.utils.InfoApp
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : Fragment(R.layout.fragment_info) {

    val nameFragment = "InfoFragment"
    lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        mainViewModel.setTitleBar("Info App")
        txt_info_about.text = "About ${InfoApp().infoNameApp} ${InfoApp().infoversion}"
        txt_info_update.text = "${InfoApp().infoLastUpdate}"
        txt_info_api.text = "${InfoApp().infoApiData}"
        txt_info_by.text = "${InfoApp().infoCreateBy}"

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

}