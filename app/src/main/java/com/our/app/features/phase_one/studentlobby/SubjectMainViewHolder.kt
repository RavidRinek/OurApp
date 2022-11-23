package com.our.app.features.phase_one.studentlobby

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.our.app.databinding.ItemStudentSubjectMainSpinnerBinding
import com.our.app.utilities.extensions.dpToPx
import com.our.app.utilities.extensions.loadBitmap
import com.our.domain.features.phase_one.models.remote.BaseSubject
import com.our.domain.features.phase_one.models.remote.Subject

class SubjectMainViewHolder(
    itemView: ItemStudentSubjectMainSpinnerBinding,
    private val listener: SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener
) : RecyclerView.ViewHolder(itemView.root), SubjectViewHolderInter {
    private val tvSpinnerSubResult: TextView = itemView.tvSpinnerSubMainResult

    override fun bind(baseSubject: BaseSubject) {
        (baseSubject as? Subject)?.apply {
            tvSpinnerSubResult.apply {
                text = name
                context.loadBitmap(
                    url = imgUrl,
                    resize = Pair(24.dpToPx(), 24.dpToPx())
                ) { bitmap ->
                    val img: Drawable = BitmapDrawable(context.resources, bitmap)
                    setCompoundDrawablesWithIntrinsicBounds(null, null, img, null)
                }
            }
        }
        itemView.setOnClickListener { listener.itemClicked(baseSubject) }
    }
}