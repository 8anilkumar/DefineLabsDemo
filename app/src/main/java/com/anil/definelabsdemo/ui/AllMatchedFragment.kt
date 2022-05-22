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
import com.anil.definelabsdemo.adapters.AllMatchAdapter
import com.anil.definelabsdemo.databinding.FragmentAllMatchedBinding
import com.anil.definelabsdemo.models.Venue
import com.anil.definelabsdemo.models.VenueResponse
import com.anil.definelabsdemo.utils.Constants
import com.anil.definelabsdemo.utils.DatabaseHandler
import com.anil.definelabsdemo.utils.MatchListner
import com.anil.definelabsdemo.viewModels.AllMatchedViewModel

class AllMatchedFragment : Fragment() , MatchListner {

    private var binding: FragmentAllMatchedBinding? = null
    private lateinit var mainViewModel: AllMatchedViewModel
    private lateinit var mAdapter: AllMatchAdapter
    private lateinit var databaseHandler: DatabaseHandler
    private var venueResponseData: VenueResponse?=null
    private var venueListData:MutableList<Venue> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(AllMatchedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentAllMatchedBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setUpDB()

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        requestApiData()
    }
    private fun setUpDB() {
        databaseHandler =
            Room.databaseBuilder(requireContext(), DatabaseHandler::class.java, Constants.VENUE_TABLE)
                .allowMainThreadQueries().build()
    }

    private fun setupRecyclerView(){
        mAdapter = AllMatchAdapter(requireContext(),venueResponseData?.venues as MutableList<Venue>?,this)
        binding?.recyclerview?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerview?.adapter = mAdapter
    }

     fun requestApiData() {
        binding?.mainProgressBar?.visibility = View.VISIBLE
        mainViewModel.callApiToGetVenues()
        mainViewModel.getVenues().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                binding?.mainProgressBar?.visibility = View.GONE
                venueResponseData = response
                venueResponseData?.let { venues->
                    if(venues.venues.isNotEmpty()){
                        binding?.recyclerview?.visibility = View.VISIBLE
                        binding?.tvNoMatch?.visibility = View.GONE

                        venueListData = databaseHandler.matchInterface()?.getAllMatch() as MutableList<Venue>
                        mAdapter.setData(venues,venueListData)
                        mAdapter.notifyDataSetChanged()
                    } else {
                        binding?.recyclerview?.visibility = View.GONE
                        binding?.tvNoMatch?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun matchListener(venue: Venue) {
        databaseHandler.matchInterface()?.addAllMatched(venue)
        mAdapter.notifyDataSetChanged()
        requestApiData()
        Toast.makeText(binding?.root?.context,"Venue Add successful!", Toast.LENGTH_SHORT).show()
    }
}