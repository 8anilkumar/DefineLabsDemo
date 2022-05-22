package com.anil.definelabsdemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.anil.definelabsdemo.adapters.SavedVenueAdapter
import com.anil.definelabsdemo.databinding.FragmentSavedMatchedBinding
import com.anil.definelabsdemo.models.Venue
import com.anil.definelabsdemo.utils.Constants
import com.anil.definelabsdemo.utils.DatabaseHandler
import com.anil.definelabsdemo.utils.MatchListner
import com.anil.definelabsdemo.viewModels.SavedMatchedViewModel
import java.util.*

class SavedMatchedFragment : Fragment() , MatchListner {

    private var binding: FragmentSavedMatchedBinding? = null
    private lateinit var mainViewModel: SavedMatchedViewModel
    private lateinit var mAdapter: SavedVenueAdapter
    private lateinit var databaseHandler: DatabaseHandler
    private var venueListData:MutableList<Venue> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(SavedMatchedViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSavedMatchedBinding.inflate(inflater, container, false)

        setUpDB()
        initRecyclerView()

        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        retrieveMatchList()
    }

    private fun retrieveMatchList() {
        venueListData = databaseHandler.matchInterface()?.getAllMatch() as MutableList<Venue>
        venueListData.let {
            if(it.isNotEmpty()){
                binding?.recyclerview?.visibility = View.VISIBLE
                binding?.tvNoMatch?.visibility = View.GONE
                val venueList: MutableList<Venue> = mutableListOf()
                it.forEach {
                    if(it.isStarred){
                        venueList.add(it)
                    }
                }

                mAdapter.setData(venueList)

            }else{
                binding?.recyclerview?.visibility = View.GONE
                binding?.tvNoMatch?.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        mAdapter = SavedVenueAdapter(requireContext(),venueListData,this)
        binding?.recyclerview?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerview?.adapter = mAdapter
    }

    private fun setUpDB() {
        databaseHandler = Room.databaseBuilder(requireContext(), DatabaseHandler::class.java, Constants.VENUE_TABLE)
            .allowMainThreadQueries().build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun matchListener(venueList: Venue) {
        databaseHandler.matchInterface()?.deleteFavoriteVenue(venueList)
        retrieveMatchList()
        mAdapter.notifyDataSetChanged()
        Toast.makeText(binding?.root?.context,"Venue Delete successfull!",Toast.LENGTH_SHORT).show()
    }
}