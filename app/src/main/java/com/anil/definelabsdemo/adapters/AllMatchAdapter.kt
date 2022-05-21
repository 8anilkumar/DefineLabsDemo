package com.anil.definelabsdemo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.anil.definelabsdemo.databinding.AllMatchedRowBinding
import com.anil.definelabsdemo.models.AllMatchedResponse
import com.anil.definelabsdemo.models.MatchResponse
import com.anil.definelabsdemo.models.Venue
import com.anil.definelabsdemo.utils.MatchListner
import com.google.android.material.card.MaterialCardView
import java.time.temporal.ValueRange

class AllMatchAdapter(private var venueList: MutableList<Venue> = mutableListOf(), val matchListner: MatchListner) : RecyclerView.Adapter<AllMatchAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: AllMatchedRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var imgStar: ImageView =  binding.imgStar

        fun bind(result: Venue) {
            binding.txtId.text = result.id
            binding.txtName.text = result.name
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
        val currentRecipe = venueList[position]

        holder.bind(currentRecipe)

        holder.imgStar.setOnClickListener {
            matchListner.matchListener(venueList[position])
        }
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

    fun setData(newData: AllMatchedResponse){
        venueList = newData.response.venues as MutableList<Venue>
    }
}