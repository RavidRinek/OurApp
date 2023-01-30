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

class TeacherCreateInfoCustomView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_crate_teacher_info, this, true)
    private val viewBinding = CvCrateTeacherInfoBinding.bind(view)
    private val teachInfoHashMap = HashMap<String, String>()

    init {
        viewBinding.etName.text
    }

    fun getTeachInfo(): HashMap<String, String> {
        for (i in 0 until viewBinding.llInfoContainer.childCount) {
            (viewBinding.llInfoContainer[i] as EditText).apply {
                teachInfoHashMap[tag as String] = "text.toString()"
            }
        }
        teachInfoHashMap["birthday"] = (504613830).toString()
        teachInfoHashMap["teacherPhone"] = System.currentTimeMillis().toString()
        return teachInfoHashMap
    }
}