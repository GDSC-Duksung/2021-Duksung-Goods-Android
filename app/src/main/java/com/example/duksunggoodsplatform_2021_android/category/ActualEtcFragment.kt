package com.example.duksunggoodsplatform_2021_android.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.category.data.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_actual_etc.*
import kotlinx.android.synthetic.main.fragment_actual_etc.recyclerView
import kotlinx.android.synthetic.main.fragment_fictitious_all.*

class ActualEtcFragment : Fragment() {

    private val model: CategoryViewModel by viewModels()

    lateinit var recyclerAdapter: RecyclerAdapter
    val datas = mutableListOf<CategoryItemData>()
    var pageNum = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fictitious_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = RecyclerAdapter(requireActivity())
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.datas = datas

        model.datas.observe(requireActivity()) { liveData ->
            Log.e("jh", "observe : pageNum ${pageNum}")
            for (item in liveData){
                datas.add(item)
            }
            recyclerAdapter.notifyDataSetChanged()
            pageNum += 1
        }

        model.getItems(DEMAND_SURVEY_TYPE, CATEGORY_ID, pageNum)


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount
                if(lastVisibleItemPosition + 1 == itemTotalCount) {
                    model.getItems(DEMAND_SURVEY_TYPE, CATEGORY_ID, pageNum)
                }
            }
        })

    }

    companion object {
        private const val DEMAND_SURVEY_TYPE = 0
        private const val CATEGORY_ID = 4
    }

}