package meaw.meow.pjcoviddashboard.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import meaw.meow.pjcoviddashboard.adapter.CountriesItemClickListenner
import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponseItem
import meaw.meow.pjcoviddashboard.databinding.CountriesRowItemBinding

class CountriesViewHolder(private val binding: CountriesRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(countriesResponseItem: CountriesResponseItem, listenner: CountriesItemClickListenner){
        binding.countriesItem = countriesResponseItem
        binding.clickListenner = listenner

//        binding.cardItem.setOnClickListener {
//            Log.d("DSS","CountriesViewHolder CLICK")
//            //listenner?.let {it.onCountriesItemClick(countriesResponseItem)}
//        }
    }
}