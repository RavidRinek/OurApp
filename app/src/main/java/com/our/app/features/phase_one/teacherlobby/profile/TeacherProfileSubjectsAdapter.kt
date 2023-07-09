package com.our.app.features.phase_one.teacherlobby.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemTeacherSubjectBinding
import com.our.app.utilities.extensions.loadImage
import com.our.domain.features.phase_one.models.remote.Subject

class TeacherProfileSubjectsAdapter :
    ListAdapter<Subject, TeacherProfileSubjectsAdapter.TeacherProfileSubjectViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherProfileSubjectViewHolder = TeacherProfileSubjectViewHolder(
        binding = ItemTeacherSubjectBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_teacher_subject, parent, false)
        )
    )

    override fun onBindViewHolder(holder: TeacherProfileSubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TeacherProfileSubjectViewHolder(val binding: ItemTeacherSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subject: Subject) {
            binding.apply {
                ivSubject.loadImage(subject.imgUrl)
                tvSubject.text = subject.name
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject) =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Subject,
            newItem: Subject
        ) = oldItem.id == newItem.id

        override fun getChangePayload(
            oldItem: Subject,
            newItem: Subject
        ) = oldItem
    }
}