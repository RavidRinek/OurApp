package com.our.app.features.phase_one.studentlobby

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemFindLessonResultBinding
import com.our.domain.features.phase_one.models.remote.Lesson

class StudentFindLessonResultAdapter(private val lessons: List<Lesson>) :
    RecyclerView.Adapter<StudentFindLessonResultAdapter.LessonResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonResultViewHolder =
        LessonResultViewHolder(
            ItemFindLessonResultBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_find_lesson_result, parent, false)
            )
        )

    override fun getItemCount(): Int = lessons.size

    override fun onBindViewHolder(holder: LessonResultViewHolder, position: Int) {
        holder.bind(lessons[position])
    }

    class LessonResultViewHolder(private val binding: ItemFindLessonResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lesson: Lesson) {
            lesson.apply {
                binding.apply {
                    tvSubjectName.text = subjectBranchName
                    tvTeacherNameVal.text = teacherName
                    tvTeacherPriceVal.text = price.toString()
                    tvLessonDateVal.text = time
                    rbLessonRatingVal.rating = ratingInPercentage.toFloat()
                }
            }
        }
    }
}