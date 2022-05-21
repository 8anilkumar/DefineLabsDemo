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
import com.anil.definelabsdemo.databinding.FragmentSavedMatchedBinding
import com.anil.definelabsdemo.models.AllMatchedResponse
import com.anil.definelabsdemo.models.MatchResponse
import com.anil.definelabsdemo.models.Venue
import com.anil.definelabsdemo.utils.DatabaseHandler
import com.anil.definelabsdemo.utils.MatchListner
import com.anil.definelabsdemo.viewModels.SavedMatchedViewModel

class SavedMatchedFragment : Fragment() , MatchListner {

    private var binding: FragmentSavedMatchedBinding? = null
    private lateinit var mainViewModel: SavedMatchedViewModel
    private lateinit var mAdapter: AllMatchAdapter
    private lateinit var databaseHandler: DatabaseHandler
    private var venueListData:MutableList<Venue> = mutableListOf()
    private var venueList: List<AllMatchedResponse>? = emptyList()

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
        venueList = databaseHandler.matchInterface()?.getAllMatch()
        venueList?.let {
            if(it.isNotEmpty()){
                binding?.recyclerview?.visibility = View.VISIBLE
                binding?.tvNoMatch?.visibility = View.GONE
                //mAdapter.setData(it)
            }else{
                binding?.recyclerview?.visibility = View.GONE
                binding?.tvNoMatch?.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        mAdapter = AllMatchAdapter(venueListData,this)
        binding?.recyclerview?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerview?.adapter = mAdapter
    }

    private fun setUpDB() {
        databaseHandler = Room.databaseBuilder(requireContext(), DatabaseHandler::class.java, "MATCH_TABLE")
            .allowMainThreadQueries().build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun matchListener(venueList: Venue) {
        Toast.makeText(binding?.root?.context,""+id,Toast.LENGTH_SHORT).show()
    }
}