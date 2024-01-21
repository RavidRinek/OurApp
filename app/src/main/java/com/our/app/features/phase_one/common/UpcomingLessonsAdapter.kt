package com.our.app.features.phase_one.common

import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemTeacherClassInfoBinding
import com.our.app.utilities.extensions.loadImage
import com.our.domain.features.phase_one.models.remote.Oreder


class UpcomingLessonsAdapter(val orders: List<Oreder>) :
    RecyclerView.Adapter<UpcomingLessonsAdapter.ScheduledLessonViewHolder>() {

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
        fun bind(order: Oreder) {
            binding.apply {
                ivAvatar.loadImage(order.student.avatarUrl)
                tvSubject.text = order.lesson.subjectName
                tvStudentName.text = order.student.name
                tvTime.text = order.lesson.time

                tvDuration.apply {
                    movementMethod = LinkMovementMethod.getInstance()
                    text = order.videoUrl
                    setOnClickListener {
                        val url = order.videoUrl
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(itemView.context, i, null)
                    }
//                    text = order.lesson.durationInMin.toString()
                }
            }
        }
    }
}