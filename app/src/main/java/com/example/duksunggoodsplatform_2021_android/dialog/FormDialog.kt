package com.example.duksunggoodsplatform_2021_android.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.duksunggoodsplatform_2021_android.databinding.PopupFormDialogBinding
import kotlinx.android.synthetic.main.popup_form_dialog.view.*


class FormDialog(val text: String) : DialogFragment() {
    private var _binding: PopupFormDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = PopupFormDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.dialogText.text = text
        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*
        binding.dialogBtn.setOnClickListener {
            buttonClickListener.onButtonClicked()
            //dismiss()    // 대화상자를 닫는 함수
        }

         */

        view.dialogClose.setOnClickListener {
            dismiss()
        }

        view.btn_dialog_disguised.setOnClickListener {
            buttonClickListener.onButton1Clicked()
            dismiss()
        }
        view.btn_dialog_actual.setOnClickListener{
            buttonClickListener.onButton2Clicked()
            dismiss()
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
        fun onButton1Clicked()
        fun onButton2Clicked()
    }
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }
    private lateinit var buttonClickListener: OnButtonClickListener
}