package meaw.meow.pjcoviddashboard.adapter

import androidx.recyclerview.widget.DiffUtil
import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponseItem

class CountriesDiffCallback : DiffUtil.ItemCallback<CountriesResponseItem>() {
    override fun areItemsTheSame(oldItem: CountriesResponseItem, newItem: CountriesResponseItem): Boolean {
        return oldItem.country == newItem.country
    }

    override fun areContentsTheSame(oldItem: CountriesResponseItem, newItem: CountriesResponseItem): Boolean {
        return oldItem == newItem
    }
}