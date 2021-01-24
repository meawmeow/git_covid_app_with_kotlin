package meaw.meow.pjcoviddashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import meaw.meow.pjcoviddashboard.data.repository.Repository
import meaw.meow.pjcoviddashboard.utils.NetworkHelper

class MainViewModelFactory(
    val repository: Repository,
    val networkHelper: NetworkHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository,networkHelper) as T
    }
}