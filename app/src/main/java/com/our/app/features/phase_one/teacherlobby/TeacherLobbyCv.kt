package com.our.app.features.phase_one.teacherlobby

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.our.app.R
import com.our.app.databinding.CvTeacherLobbyBinding

class TeacherLobbyCv(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_teacher_lobby, this, true)
    private val viewBinding = CvTeacherLobbyBinding.bind(view)
    private var listener: OnTeacherLobbyEmptyStateCustomViewListener? = null

    interface OnTeacherLobbyEmptyStateCustomViewListener {
        fun updateAvailability(status: Boolean)
    }

    init {
        viewBinding.switchAvailableForLesson.setOnCheckedChangeListener { _, isChecked ->
            listener?.updateAvailability(isChecked)
        }
    }

    fun initViews(lessons: List<String>, listener: OnTeacherLobbyEmptyStateCustomViewListener) {
        this.listener = listener
        if (lessons.isNotEmpty()) {
            viewBinding.tvNoLessonsTitle.visibility = View.GONE
            viewBinding.rvTeacherLessons.adapter = TeacherScheduledLessonsAdapter(lessons)
        }
    }
}