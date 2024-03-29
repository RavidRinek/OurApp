package com.our.app.features.phase_one.student.find_lesson_res

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemFindLessonResultBinding
import com.our.app.utilities.extensions.loadImage
import com.our.domain.features.phase_one.models.remote.StudentLessonOffers

class StudentFindLessonResultAdapter(
    private val lessonsRes: List<StudentLessonOffers>,
    private val alreadyRegistered: Boolean,
    private val listener: OnStudentFindLessonResultAdapterListener
) :
    RecyclerView.Adapter<StudentFindLessonResultAdapter.LessonResultViewHolder>() {

    interface OnStudentFindLessonResultAdapterListener {
        fun showTeacherProfilerBtnClicked(
            teacherId: Int,
            lessonId: Int
        )

        fun orderALessonBtnClicked(
            teacherId: Int,
            lessonId: Int
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonResultViewHolder =
        LessonResultViewHolder(
            ItemFindLessonResultBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_find_lesson_result, parent, false)
            )
        )

    override fun getItemCount(): Int = lessonsRes.size

    override fun onBindViewHolder(holder: LessonResultViewHolder, position: Int) {
        holder.bind(lessonsRes[position])
    }

    inner class LessonResultViewHolder(private val binding: ItemFindLessonResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lessonRes: StudentLessonOffers) {
            lessonRes.apply {
                binding.apply {
                    ivTeacherAvatar.loadImage(
                        url = lessonRes.teacherProfile.teacherAvatar,
                        rounded = true
                    )
                    tvTeacherNameVal.text = lessonRes.teacherProfile.teacherName
                    tvTeacherPriceVal.text = lessonRes.lessonInfo.pricePer60m.toString()
                    tvLessonDateVal.text = lessonRes.lesson.time
                    rbLessonRatingVal.rating = lessonRes.lesson.ratingInPercentage.toFloat()
                    if (layoutPosition % 3 == 0) {
                        tvDuringInMin.text = "60דק׳"
                    } else {
                        tvDuringInMin.text = "40דק׳"
                    }
                    tvTeacherProfile.setOnClickListener {
                        listener.showTeacherProfilerBtnClicked(
                            lesson.teacherId,
                            lesson.id
                        )
                    }
                    tvOrderLesson.apply {
                        if (alreadyRegistered) {
                            visibility = View.VISIBLE
                            setOnClickListener {
                                listener.orderALessonBtnClicked(
                                    lesson.teacherId,
                                    lesson.id
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}