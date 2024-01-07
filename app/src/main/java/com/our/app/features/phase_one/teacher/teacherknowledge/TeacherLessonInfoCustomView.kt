package com.our.app.features.phase_one.teacher.teacherknowledge

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.our.app.R
import com.our.app.databinding.CvTeacherLessonInfoBinding

class TeacherLessonInfoCustomView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    data class LessonInfoData(
        val pricePer40m: Int = 0,
        val pricePer60m: Int = 0,
        val allowFreeFirstLesson: Boolean = false,
        val additionalInfo: String = ""
    )

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_teacher_lesson_info, this, true)
    private val viewBinding = CvTeacherLessonInfoBinding.bind(view)

    init {
        viewBinding.apply {
            clSubjectsSpinner.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutTeacherPriceInfo.llInfoContainer.isVisible = it.isSelected
                ivArrow.setImageResource(if (it.isSelected) R.drawable.ic_arrow_orange_up else R.drawable.ic_arrow_orange_down)
            }
        }
    }

    fun dismissContainer() {
        viewBinding.clSubjectsSpinner.isSelected = false
        viewBinding.ivArrow.setImageResource(R.drawable.ic_arrow_orange_down)
        viewBinding.layoutTeacherPriceInfo.llInfoContainer.isVisible =
            viewBinding.clSubjectsSpinner.isSelected
    }

    fun getLessonInfoData(): LessonInfoData =
        LessonInfoData(
            pricePer40m = 0,//viewBinding.layoutTeacherPriceInfo.etPricePer40m.text.toString().toInt(),
            pricePer60m = 0,//viewBinding.layoutTeacherPriceInfo.etPricePer60m.text.toString().toInt(),
            allowFreeFirstLesson = viewBinding.layoutTeacherPriceInfo.cbFreeFirstLesson.isChecked,
            additionalInfo = viewBinding.layoutTeacherPriceInfo.etAdditionalInfo.text.toString()
        )
}