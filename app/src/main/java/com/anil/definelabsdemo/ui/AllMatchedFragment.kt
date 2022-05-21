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
import com.anil.definelabsdemo.models.AllMatchedResponse
import com.anil.definelabsdemo.models.MatchResponse
import com.anil.definelabsdemo.models.Venue
import com.anil.definelabsdemo.utils.DatabaseHandler
import com.anil.definelabsdemo.utils.MatchListner
import com.anil.definelabsdemo.viewModels.AllMatchedViewModel

class AllMatchedFragment : Fragment() , MatchListner {

    private var binding: FragmentAllMatchedBinding? = null
    private lateinit var mainViewModel: AllMatchedViewModel
    private lateinit var mAdapter: AllMatchAdapter
    var allMatchedDataList: AllMatchedResponse? = null
    private lateinit var databaseHandler: DatabaseHandler
    private var venueListData:MutableList<AllMatchedResponse> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(AllMatchedViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentAllMatchedBinding.inflate(inflater, container, false)

        setupRecyclerView()
        requestApiData()
        setUpDB()
        return binding?.root
    }

    private fun setUpDB() {
        databaseHandler =
            Room.databaseBuilder(requireContext(), DatabaseHandler::class.java, "MATCH_TABLE")
                .allowMainThreadQueries().build()
    }

    private fun setupRecyclerView(){
        mAdapter = AllMatchAdapter(venueListData,this)
        binding?.recyclerview?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerview?.adapter = mAdapter

    }

     fun requestApiData() {
        mainViewModel.callApiForGetAllMatchedData()
        mainViewModel.getAllMatchedData().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                allMatchedDataList = response
                allMatchedDataList?.let {
                    mAdapter.setData(it)
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun matchListener(venueList: AllMatchedResponse) {
        Toast.makeText(binding?.root?.context,""+venueList.id,Toast.LENGTH_SHORT).show()
        databaseHandler.matchInterface()?.addAllMatched(venueList)
    }
}