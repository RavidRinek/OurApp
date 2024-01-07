package com.our.app.features.phase_one.student.find_lesson

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.slider.Slider
import com.our.app.R
import com.our.app.databinding.CvStudentLessonPriceBinding

class StudentLessonPriceCustomView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_student_lesson_price, this, true)
    private val viewBinding = CvStudentLessonPriceBinding.bind(view)

    init {
        viewBinding.sliderStudentLessonPrice.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            viewBinding.tvStudentLessonPrice.text = "${value.toInt()}₪"
        })
    }
}