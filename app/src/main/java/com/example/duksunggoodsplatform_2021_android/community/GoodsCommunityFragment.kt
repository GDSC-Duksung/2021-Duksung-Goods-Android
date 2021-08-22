package com.example.duksunggoodsplatform_2021_android.community

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.duksunggoodsplatform_2021_android.R
import kotlinx.android.synthetic.main.fragment_goods_community.*

/**
 * A simple [Fragment] subclass.
 * Use the [GoodsCommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoodsCommunityFragment : Fragment() {
    private lateinit var viewModel: CommunityPostListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_goods_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val goodsCommunityAdapter = GoodsCommunityAdapter {communityPost -> adapterOnClick(communityPost) }

        recycler_view_community_posting.adapter = goodsCommunityAdapter

        viewModel = ViewModelProvider(this, CommunityPostViewModelFactory(requireActivity().applicationContext)).get(CommunityPostListViewModel::class.java)
        //viewModel = CommunityPostListViewModel(CommunityDataSource.getDataSource(resources))
        viewModel.communityPostsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Log.d("ummmm","umum")
                goodsCommunityAdapter.submitList(it as MutableList<CommunityPost>)
            }
        })
    }

    private fun adapterOnClick(communityPost: CommunityPost) {
        // TODO: 게시물 자세히 + 댓글뷰로 전환
    }
}