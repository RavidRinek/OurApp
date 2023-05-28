package com.our.app.features.phase_one.teacherlobby

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemTeacherClassInfoBinding

class TeacherScheduledLessonsAdapter(val lessons: List<String>) :
    RecyclerView.Adapter<TeacherScheduledLessonsAdapter.ScheduledLessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledLessonViewHolder =
        ScheduledLessonViewHolder(
            binding = ItemTeacherClassInfoBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_teacher_class_info, parent, false)
            )
        )

    override fun getItemCount(): Int = lessons.size

    override fun onBindViewHolder(holder: ScheduledLessonViewHolder, position: Int) {
        holder.bind(lessons[position])
    }

    class ScheduledLessonViewHolder(val binding: ItemTeacherClassInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson: String) {

        }
    }
}