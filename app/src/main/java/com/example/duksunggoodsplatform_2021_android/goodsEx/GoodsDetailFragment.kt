package com.example.duksunggoodsplatform_2021_android.goodsEx

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.databinding.FragmentGoodsDetailBinding

class GoodsDetailFragment : Fragment() {
    private var _binding: FragmentGoodsDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var title: String
    private lateinit var imgList: ArrayList<String>
    private lateinit var description: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("goodsInfo") { requestKey, bundle ->
            title = bundle.getString("title") ?: "no data"
            imgList = bundle.getStringArrayList("imgList") ?: arrayListOf("no data")
            description = bundle.getString("description") ?: "no data"

            Log.d("로그detail---", "title: ${title}")

            binding.tvGoodsDetailTitle.text = title
            binding.tvGoodsDetailDescription.text = description

            val layout = binding.llGoodsDetailFragment
            for(url in imgList){
                var iv = ImageView(activity)
                activity?.let { Glide.with(it).load(url).into(iv) }
                layout.addView(iv)
                //iv.setImageResource()
            }

            // Do something with the result
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGoodsDetailBinding.inflate(inflater, container, false)

/*        val layout = binding.llGoodsDetailFragment
        for(url in imgList){
            var iv = ImageView(activity)
            activity?.let { Glide.with(it).load(url).into(iv) }
            layout.addView(iv)
            //iv.setImageResource()
        }*/

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}