package com.example.duksunggoodsplatform_2021_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.databinding.FragmentGoodsExBinding
import java.text.DecimalFormat

class GoodsExFragment : Fragment() {
    private var _binding: FragmentGoodsExBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGoodsExBinding.inflate(inflater, container, false)
        val view = binding.root

        //더미데이터
        val dummyImageUrl = "https://bit.ly/3yAt3za"

        var purchaseCount = 1

        // TODO : 서버에서 데이터 받아오기
        val mainImage = dummyImageUrl
        var fav = false
        val price = 12000

        // TODO : 서버에서 받아온 데이터 세팅
        setFav(fav, false)
        activity?.let { Glide.with(it).load(mainImage).into(binding.ivGoodsExMainImage) }
        val priceFormat = DecimalFormat("#,###")
        binding.tvGoodsExPrice.text = priceFormat.format(price)


        binding.ivGoodsExFavorite.setOnClickListener {
            fav = !fav
            setFav(fav, true)
        }
/*
        binding.tvGoodsExCountMinus.setOnClickListener {
            if(purchaseCount > 1){
                purchaseCount -= 1
                binding.tvGoodsExCount.text = "${purchaseCount}"
            }else if(purchaseCount <= 1){
                Toast.makeText(activity, "최소 구매 가능 수량입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGoodsExCountPlus.setOnClickListener {
            if(purchaseCount < 99999){
                purchaseCount += 1
                binding.tvGoodsExCount.text = "${purchaseCount}"
            }else if(purchaseCount >= 99999){
                Toast.makeText(activity, "최대 구매 가능 수량입니다.", Toast.LENGTH_SHORT).show()
            }
        }*/



        return view
    }

    // 관심 컨트롤 함수
    private fun setFav(fav: Boolean, statusChange: Boolean){
        if(fav){
            binding.ivGoodsExFavorite.setImageResource(R.drawable.star_clicked)
            if(statusChange){
                // TODO : 서버 정보 변경
                Toast.makeText(activity, "관심목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }else if(!fav){
            binding.ivGoodsExFavorite.setImageResource(R.drawable.star_nonclicked)
            if(statusChange){
                // TODO : 서버 정보 변경
                Toast.makeText(activity, "관심목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GoodsExFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                GoodsExFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
                }
    }
}