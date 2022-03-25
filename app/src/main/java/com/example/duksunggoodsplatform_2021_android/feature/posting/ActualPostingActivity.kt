package com.example.duksunggoodsplatform_2021_android.feature.posting
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import kotlinx.android.synthetic.main.activity_actual_demand_posting.*
import java.io.IOException

class ActualPostingActivity : AppCompatActivity() {

    private val PICK_IMAGE1 = 1
    private val PICK_IMAGE2 = 2
    private val PICK_IMAGE3 = 3
    private val PICK_IMAGE4 = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual_demand_posting)

        val spinner: Spinner = findViewById(R.id.spinner_actual_posting_category)
        val items = resources.getStringArray(R.array.category_array)
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        // Spinner
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = myAdapter
        spinner.setSelection(3)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when(position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("SpinnerTag", "onNothingSelected")
            }
        }

        // 뒤로가기 버튼, 취소하기 버튼
        img_back_actual_demand_posting.setOnClickListener{
            finish()
        }

        btn_cancel_actual_posting.setOnClickListener {
            finish()
        }

        // 사진 추가하기
        btn_actual_posting_img1.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE1)
        }

        btn_actual_posting_img2.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE2)
        }

        btn_actual_posting_img3.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE3)
        }

        btn_actual_posting_img4.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE4)
        }

        // 등록하기 버튼
        btn_register_actual_posting.setOnClickListener {
            // 성공
            if(et_actual_posting_title.text.isNotEmpty() && et_actual_posting_content.text.isNotEmpty() && selectedPicUri1!=null && et_actual_posting_date.text.isNotEmpty()
                && et_actual_posting_price.text.isNotEmpty() && cb_actual_posting.isChecked) {
                val dialog = CustomDialog("실수요조사 포스팅에 \n 성공하였습니다.")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener {
                    override fun onButtonClicked() {
                        // 서버에 데이터 전송
                        finish()
                        // 다른 화면 연결
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            } else{
                // 실패
                val dialog = CustomDialog("실수요조사 포스팅에 \n 실패하였습니다. \n 모든 내용을 입력해주세요. ")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener {
                    override fun onButtonClicked() {
                        dialog.dismiss()
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        }
    }

    var selectedPicUri1 : Uri? = null
    var selectedPicUri2 : Uri? = null
    var selectedPicUri3 : Uri? = null
    var selectedPicUri4 : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==PICK_IMAGE1){
            data?.let{
                selectedPicUri1 = it.data!!
                Glide.with(this).load(selectedPicUri1).into(btn_actual_posting_img1)
            }
            try{
               //uploadImage()
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
        if(requestCode==PICK_IMAGE2){
            data?.let{
                selectedPicUri2 = it.data!!
                Glide.with(this).load(selectedPicUri2).into(btn_actual_posting_img2)
            }
            try{
                //uploadImage()
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
        if(requestCode==PICK_IMAGE3){
            data?.let{
                selectedPicUri3 = it.data!!
                Glide.with(this).load(selectedPicUri3).into(btn_actual_posting_img3)
            }
            try{
                //uploadImage()
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
        if(requestCode==PICK_IMAGE4){
            data?.let{
                selectedPicUri4 = it.data!!
                Glide.with(this).load(selectedPicUri4).into(btn_actual_posting_img4)
            }
            try{
                //uploadImage()
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
    }
}

