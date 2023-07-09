package com.our.app.features.phase_one.teacherlobby.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemTeacherReviewBinding
import com.our.domain.features.phase_one.models.remote.Review

class TeacherProfileReviewsAdapter :
    ListAdapter<Review, TeacherProfileReviewsAdapter.TeacherProfileReviewViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherProfileReviewViewHolder = TeacherProfileReviewViewHolder(
        binding = ItemTeacherReviewBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_teacher_review, parent, false)
        )
    )

    override fun onBindViewHolder(holder: TeacherProfileReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TeacherProfileReviewViewHolder(val binding: ItemTeacherReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.apply {
                tvRating.text = review.rating.toString()
                rbLessonRatingVal.numStars = review.rating
                tvReviewComment.text = review.description
                tvLikesAmount.text = review.like.toString()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review) =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Review,
            newItem: Review
        ) = oldItem.id == newItem.id

        override fun getChangePayload(
            oldItem: Review,
            newItem: Review
        ) = oldItem
    }
}