package com.example.duksunggoodsplatform_2021_android.interest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.databinding.FragmentGoodsExBinding
import com.example.duksunggoodsplatform_2021_android.databinding.FragmentInterestAllBinding
import com.example.duksunggoodsplatform_2021_android.interest.data.InterestViewModel
import kotlinx.android.synthetic.main.activity_interest_list.*
import kotlinx.android.synthetic.main.fragment_fictitious_all.*
import kotlinx.android.synthetic.main.fragment_interest_all.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [interestAllFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InterestAllFragment : Fragment() {
    private var _binding: FragmentInterestAllBinding? = null
    private val binding get() = _binding!!

//    private lateinit var model: InterestViewModel
    private val model: InterestViewModel by viewModels()

    private lateinit var interestAdater: InterestRecyclerViewAdapter
    var datas = mutableListOf<ModelInterestItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentInterestAllBinding.inflate(inflater, container, false)
        val view = binding.root

        interestAdater = InterestRecyclerViewAdapter(requireActivity(), model)
        interestAdater.datas = datas
        binding.rvInterestAll.adapter = interestAdater

        model.datas.observe(requireActivity()) { liveData ->
            Log.e("jh", "observe interest notify")
//            for (item in liveData){
//                Log.d("jh", "liveData for문 item: ${item}")
//                datas.add(item)
//            }
            datas = liveData

            Log.d("jh", "observe put datas: ${datas}")
            interestAdater.notifyDataSetChanged()
        }

        model.getInterestList()

        return view
    }
/*
    companion object {
        *//**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment interestAllFragment.
         *//*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                interestAllFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }*/
}