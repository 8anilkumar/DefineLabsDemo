package com.anil.definelabsdemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anil.definelabsdemo.R
import com.anil.definelabsdemo.databinding.AllMatchedRowBinding
import com.anil.definelabsdemo.models.Venue
import com.anil.definelabsdemo.models.VenueResponse
import com.anil.definelabsdemo.utils.MatchListner

class AllMatchAdapter(
    private val context: Context,
    private var venueList: MutableList<Venue>? = mutableListOf(),
    val matchListner: MatchListner) : RecyclerView.Adapter<AllMatchAdapter.MyViewHolder>() {

    private var venueStoredList: MutableList<Venue>? = mutableListOf()

    class MyViewHolder(private val binding: AllMatchedRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var imgStar: ImageView = binding.imgStar

        fun bind(context: Context, result: Venue) {
            binding.txtId.text = result.id
            binding.txtName.text = result.name
            if (result.isStarred) {
                binding.imgStar.setColorFilter(ContextCompat.getColor(context, R.color.teal_200))
            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AllMatchedRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val venue = venueList?.get(position)
        holder.imgStar.setColorFilter(ContextCompat.getColor(context, R.color.grey))

        venue?.let{ venueData->
            for (venu in venueStoredList!!){
                if(venueData.id == venu.id){
                    holder.bind(context, Venue(venueData.id, venueData.name,true))
                } else {
                   holder.bind(context, Venue(venueData.id, venueData.name,false))
                }
            }
        }

//        if (venue != null) {
//            holder.bind(context, venue)
//        }

        holder.imgStar.setOnClickListener {
            venueList?.let {
                matchListner.matchListener(Venue(it[position].id, it[position].name, true))
            }
        }
    }

    override fun getItemCount(): Int {
        return venueList?.size ?: 0
    }

    fun setData(newData: VenueResponse, venueListData: MutableList<Venue>) {
        venueList = newData.venues as MutableList<Venue>
        venueStoredList = venueListData
        notifyDataSetChanged()
    }
}