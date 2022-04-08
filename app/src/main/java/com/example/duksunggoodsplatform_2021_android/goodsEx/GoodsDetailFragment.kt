package com.example.duksunggoodsplatform_2021_android.goodsEx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.databinding.FragmentGoodsDetailBinding

class GoodsDetailFragment : Fragment() {
    private var _binding: FragmentGoodsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGoodsDetailBinding.inflate(inflater, container, false)

        val title = arguments?.getString("title")
        val imgList = arguments?.getStringArrayList("imgList") //TODO : 두번째 값부터 사용할 것 - 리스트 아니고 하난데?
        val description = arguments?.getString("description")

        binding.tvGoodsDetailTitle.text = title
        binding.tvGoodsDetailDescription.text = description
        activity?.let { Glide.with(it).load(imgList?.get(0)).into(binding.ivGoodsDetail) }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}