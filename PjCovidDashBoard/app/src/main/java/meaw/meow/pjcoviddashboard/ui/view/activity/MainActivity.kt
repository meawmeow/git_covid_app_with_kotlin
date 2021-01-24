package meaw.meow.pjcoviddashboard.ui.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import meaw.meow.pjcoviddashboard.R
import meaw.meow.pjcoviddashboard.data.repository.Repository
import meaw.meow.pjcoviddashboard.ui.view.fragments.CountriesAllFragment
import meaw.meow.pjcoviddashboard.ui.view.fragments.DashBoardFragment
import meaw.meow.pjcoviddashboard.ui.view.fragments.HomeFragment
import meaw.meow.pjcoviddashboard.ui.view.fragments.InfoFragment
import meaw.meow.pjcoviddashboard.ui.viewmodel.MainViewModel
import meaw.meow.pjcoviddashboard.ui.viewmodel.MainViewModelFactory
import meaw.meow.pjcoviddashboard.utils.NetworkHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(tool_bar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        getSupportActionBar()?.setLogo(R.drawable.ic_baseline_coronavirus_24);
        getSupportActionBar()?.setDisplayUseLogoEnabled(true);


        val repository = Repository()
        val networkHelper = NetworkHelper(applicationContext)
        val mainViewModelFactory = MainViewModelFactory(repository, networkHelper)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.titlebar.observe(this, Observer {
            toolbar_title.text = it
        })


        val homeFragment = HomeFragment()
        val countriesAllFragment = CountriesAllFragment()
        val dashBoardFragment = DashBoardFragment()

        setCurrentFragment(homeFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mHome -> {
                    setCurrentFragment(homeFragment)
                }
                R.id.mlist -> {
                    setCurrentFragment(countriesAllFragment)
                }
                R.id.mdashboard -> {
                    setCurrentFragment(dashBoardFragment)
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mAbout->{
                setCurrentFragment(InfoFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onBackPressed() {
        val isFragmentPopped = handleNestedFragmentBackStack(supportFragmentManager)
        if (!isFragmentPopped) {
            super.onBackPressed()
        }
    }


    private fun handleNestedFragmentBackStack(fragmentManager: FragmentManager): Boolean {
        val childFragmentList = fragmentManager.fragments
        if (childFragmentList.size > 0) {
            for (index in childFragmentList.size - 1 downTo 0) {
                val fragment = childFragmentList[index]
                val isPopped = handleNestedFragmentBackStack(fragment.childFragmentManager)
                return when {
                    isPopped -> true
                    fragmentManager.backStackEntryCount > 0 -> {
                        fragmentManager.popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }
        return false
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

}