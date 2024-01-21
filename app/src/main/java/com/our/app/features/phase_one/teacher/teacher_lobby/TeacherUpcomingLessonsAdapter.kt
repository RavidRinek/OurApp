package com.our.app.features.phase_one.teacher.teacher_lobby

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemTeacherClassInfoBinding
import com.our.app.utilities.extensions.loadImage
import com.our.domain.features.phase_one.models.remote.TeacherOrder

class TeacherUpcomingLessonsAdapter(val orders: List<TeacherOrder>) :
    RecyclerView.Adapter<TeacherUpcomingLessonsAdapter.ScheduledLessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledLessonViewHolder =
        ScheduledLessonViewHolder(
            binding = ItemTeacherClassInfoBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_teacher_class_info, parent, false)
            )
        )

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: ScheduledLessonViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    class ScheduledLessonViewHolder(val binding: ItemTeacherClassInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: TeacherOrder) {
            binding.apply {
                ivAvatar.loadImage(order.student.avatarUrl)
                tvSubject.text = order.lesson.subjectName
                tvStudentName.text = order.student.name
                tvTime.text = order.lesson.time
                tvDuration.apply {
                    movementMethod = LinkMovementMethod.getInstance()
                    text = order.videoUrl
//                    text = order.lesson.durationInMin.toString()
                }
            }
        }
    }
}