package com.example.duksunggoodsplatform_2021_android.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.duksunggoodsplatform_2021_android.databinding.PopupDialogBinding

class CustomDialog(val text: String) : DialogFragment() {
    private var _binding: PopupDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = PopupDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.dialogText.text = text
        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogBtn.setOnClickListener {
            buttonClickListener.onButtonClicked()
            dismiss()    // 대화상자를 닫는 함수
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(700, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnButtonClickListener {
        fun onButtonClicked()
    }
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }
    private lateinit var buttonClickListener: OnButtonClickListener
}