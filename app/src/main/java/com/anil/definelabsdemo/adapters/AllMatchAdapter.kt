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

        if (venue != null) {
            holder.bind(context, venue)
        }

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

//        venueListData.let {
//            if (it.isNotEmpty()) {
//                val venueListData: MutableList<Venue> = mutableListOf()
//                venueList?.forEach {
//                    if (it.isStarred) {
//                        venueListData.add(Venue(venueList!![0].id,venueList!![0].name,true))
//                    } else {
//                        venueListData.add(Venue(venueList!![0].id,venueList!![0].name,false))
//                    }
//                }
//                venueList = venueListData
//            }
//
//        }



        notifyDataSetChanged()
    }
}