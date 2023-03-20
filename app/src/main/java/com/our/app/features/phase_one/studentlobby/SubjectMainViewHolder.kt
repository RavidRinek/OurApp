package com.our.app.features.phase_one.studentlobby

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.our.app.databinding.ItemStudentSubjectMainSpinnerBinding
import com.our.app.databinding.ItemSubjectAndLevelChoiceBinding
import com.our.app.utilities.extensions.dpToPx
import com.our.app.utilities.extensions.loadBitmap
import com.our.domain.features.phase_one.models.remote.BaseSubject
import com.our.domain.features.phase_one.models.remote.Subject

class SubjectMainViewHolder(
    itemView: ItemSubjectAndLevelChoiceBinding,
    private val listener: SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener
) : RecyclerView.ViewHolder(itemView.root), SubjectViewHolderInter {
    private val tvSubject: TextView = itemView.tvSubject
    private val tvSubjectFirst: TextView = itemView.tvSubjectFirst
    private val tvSubjectSecond: TextView = itemView.tvSubjectSecond
    private val tvSubjectThird: TextView = itemView.tvSubjectThird

    override fun bind(baseSubject: BaseSubject) {
        (baseSubject as? Subject)?.apply {
            tvSubject.apply {
                text = name
                context.loadBitmap(
                    url = imgUrl,
                    resize = Pair(24.dpToPx(), 24.dpToPx())
                ) { bitmap ->
                    val img: Drawable = BitmapDrawable(context.resources, bitmap)
                    setCompoundDrawablesWithIntrinsicBounds(null, null, img, null)
                }
            }
            tvSubjectFirst.apply {
                text = subjectLevel.firstOrNull()?.name
                setOnClickListener {
                    text
                }
            }
            tvSubjectSecond.apply {
                text = subjectLevel.getOrNull(1)?.name
                setOnClickListener {
//                    it.
                }
            }
            tvSubjectThird.apply {
                text = subjectLevel.getOrNull(2)?.name
                setOnClickListener {
//                    it.
                }
            }
        }
//        itemView.setOnClickListener { listener.itemClicked(baseSubject) }
    }
}