package com.our.app.features.phase_one.teacherlobby

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.view.get
import com.our.app.R
import com.our.app.databinding.CvCrateTeacherInfoBinding
import com.our.domain.features.phase_one.models.remote.TeacherProfile

class TeacherCreateInfoCustomView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_crate_teacher_info, this, true)
    private val viewBinding = CvCrateTeacherInfoBinding.bind(view)
    private val teachInfoHashMap = HashMap<String, String>()

    init {
        viewBinding.etName.text
    }

    fun initViews(teacherProfile: TeacherProfile) {
        viewBinding.apply {
            teacherProfile.apply {
                etName.setText(teacherName)
                etLastName.setText(teacherLastName)
                etPhone.setText(teacherPhone)
                etMail.setText(teacherMail)
                etBd.setText(teacherPhone)
//                etAddress.setText(teacherAddress)
            }
        }
        visibility = View.VISIBLE
    }

    fun getTeachInfo(): HashMap<String, String> {
        for (i in 0 until viewBinding.llInfoContainer.childCount) {
            (viewBinding.llInfoContainer[i] as EditText).apply {
                var txt = "fdsfsd"
                if (tag == "teacherPhone"){
                    txt = System.currentTimeMillis().toString().take(10)
                }
                if (tag == "teacherMail"){
                    txt = "$txt@gmail.com"
                }
                if (tag == "teacherBirthday"){
                    txt = (2000..3000).random().toString()
                }
                teachInfoHashMap[tag as String] = txt
            }
        }
        return teachInfoHashMap
    }
}