package com.example.duksunggoodsplatform_2021_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.databinding.ActivityMainBinding
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import kotlinx.android.synthetic.main.activity_disguised_demand_posting_modify.*
import java.io.IOException

class DisguisedPostingModifyActivity : AppCompatActivity() {

    private val PICK_IMAGE1 = 1
    private val PICK_IMAGE2 = 2
    private val PICK_IMAGE3 = 3
    private val PICK_IMAGE4 = 4

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disguised_demand_posting_modify)

        val spinner: Spinner = findViewById(R.id.spinner_disguised_posting_category_modify)
        val items = resources.getStringArray(R.array.category_array)
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        var SET_IMAGE = false

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

        // 뒤로가기 버튼
        img_back_disguised_demand_posting_modify.setOnClickListener{
            finish()
        }

        btn_delete_disguised_posting.setOnClickListener {

            // Dialog만들기
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.popup_delete_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            val  mAlertDialog = mBuilder.show()

            val okButton = mDialogView.findViewById<Button>(R.id.btn_dialog_delete)
            okButton.setOnClickListener {
                // 삭제
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }

            val noButton = mDialogView.findViewById<Button>(R.id.btn_dialog_cancel)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        // 사진 추가하기
        btn_disguised_posting_img1_modify.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE1)
        }

        btn_disguised_posting_img2_modify.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE2)
        }

        btn_disguised_posting_img3_modify.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE3)
        }

        btn_disguised_posting_img4_modify.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE4)
        }

        // 수정하기 버튼
        btn_modify_disguised_posting.setOnClickListener {
            // 성공
            if (et_disguised_posting_title_modify.text.isNotEmpty() && et_disguised_posting_content_modify.text.isNotEmpty() && SET_IMAGE == true
                && et_disguised_posting_date_modify.text.isNotEmpty() && et_disguised_posting_price_modify.text.isNotEmpty() && cb_disguised_posting_modify.isChecked
            ) {
                val dialog = CustomDialog("수정되었습니다.")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener {
                    override fun onButtonClicked() {
                        // 서버에 데이터 전송
                        finish()
                        // 다른 화면 연결
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            } else {
                // 실패
                val dialog = CustomDialog("수정에 \n 실패하였습니다. \n 모든 내용을 입력해주세요. ")
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
                Glide.with(this).load(selectedPicUri1).into(btn_disguised_posting_img1_modify)
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
                Glide.with(this).load(selectedPicUri2).into(btn_disguised_posting_img2_modify)
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
                Glide.with(this).load(selectedPicUri3).into(btn_disguised_posting_img3_modify)
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
                Glide.with(this).load(selectedPicUri4).into(btn_disguised_posting_img4_modify)
            }
            try{
                //uploadImage()
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
    }
}