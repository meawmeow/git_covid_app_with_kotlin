package meaw.meow.pjcoviddashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import meaw.meow.pjcoviddashboard.adapter.viewholder.CountriesViewHolder
import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponseItem
import meaw.meow.pjcoviddashboard.databinding.CountriesRowItemBinding

class AdapterCountries(private val listenner: CountriesItemClickListenner) : ListAdapter<CountriesResponseItem,CountriesViewHolder>(CountriesDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val v = CountriesRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountriesViewHolder(v)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val data =getItem(position)
        holder.bind(data,listenner)
    }
}