package com.our.app.features.phase_one.teacherlobby.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemImageviewBinding
import com.our.app.utilities.extensions.loadImage
import com.our.domain.features.phase_one.models.remote.TeacherGallery

class TeacherProfileGalleryAdapter :
    ListAdapter<TeacherGallery, TeacherProfileGalleryAdapter.TeacherProfileGalleryViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherProfileGalleryViewHolder = TeacherProfileGalleryViewHolder(
        binding = ItemImageviewBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_imageview, parent, false)
        )
    )

    override fun onBindViewHolder(holder: TeacherProfileGalleryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TeacherProfileGalleryViewHolder(val binding: ItemImageviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profileGallery: TeacherGallery) {
            binding.apply {
                ivTemplate.loadImage(profileGallery.imgUrl)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TeacherGallery>() {
        override fun areItemsTheSame(oldItem: TeacherGallery, newItem: TeacherGallery) =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: TeacherGallery,
            newItem: TeacherGallery
        ) = oldItem.id == newItem.id

        override fun getChangePayload(
            oldItem: TeacherGallery,
            newItem: TeacherGallery
        ) = oldItem
    }
}