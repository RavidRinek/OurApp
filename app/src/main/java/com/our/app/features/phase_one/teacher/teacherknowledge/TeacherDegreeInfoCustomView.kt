package com.our.app.features.phase_one.teacher.teacherknowledge

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.our.app.R
import com.our.app.databinding.CvTeacherDegreeInfoBinding

class TeacherDegreeInfoCustomView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_teacher_degree_info, this, true)
    private val viewBinding = CvTeacherDegreeInfoBinding.bind(view)

    data class TeacherDegreeDataInfo(val schoolName: String, val degreeName: String)

    init {
        viewBinding.apply {
            clSubjectsSpinner.setOnClickListener {
                it.isSelected = !it.isSelected
                llInfoContainer.isVisible = it.isSelected
                ivArrow.setImageResource(if (it.isSelected) R.drawable.ic_arrow_orange_up else R.drawable.ic_arrow_orange_down)
            }
        }
    }

    fun dismissContainer() {
        viewBinding.clSubjectsSpinner.isSelected = false
        viewBinding.ivArrow.setImageResource(R.drawable.ic_arrow_orange_down)
        viewBinding.llInfoContainer.isVisible = viewBinding.clSubjectsSpinner.isSelected
    }

    fun getTeacherDegreeDataInfo(): TeacherDegreeDataInfo =
        TeacherDegreeDataInfo(
            schoolName = viewBinding.etSchoolName.text.toString(),
            degreeName = viewBinding.etDegreeName.text.toString()
        )
}