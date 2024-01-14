package com.our.app.features.phase_one.student.find_lesson

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.our.app.databinding.ItemSubjectAndLevelChoiceBinding
import com.our.app.utilities.extensions.dpToPx
import com.our.app.utilities.extensions.loadBitmap
import com.our.domain.features.phase_one.models.remote.BaseSubject
import com.our.domain.features.phase_one.models.remote.Subject

class SubjectMainViewHolder(
    val binding: ItemSubjectAndLevelChoiceBinding,
    private val listener: SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener
) : RecyclerView.ViewHolder(binding.root), SubjectViewHolderInter {

    override fun bind(baseSubject: BaseSubject) {
        (baseSubject as? Subject)?.let { subject ->
            binding.apply {
                tvSubject.apply {
                    text = subject.name
                    context.loadBitmap(
                        url = subject.imgUrl,
                        resize = Pair(24.dpToPx(), 24.dpToPx())
                    ) { bitmap ->
                        val img: Drawable = BitmapDrawable(context.resources, bitmap)
                        setCompoundDrawablesWithIntrinsicBounds(null, null, img, null)
                    }
                }

                for (i in 0 until llLevelContainer.childCount) {
                    (llLevelContainer.getChildAt(i) as TextView).apply {
                        val subjectLevel = subject.subjectLevel.getOrNull(i)
                        text = subjectLevel?.name
                        setOnClickListener {
                            it.isSelected = !it.isSelected
                            listener.itemClicked(ArrayList<Int>().apply {
                                add(
                                    subjectLevel?.id ?: 0
                                )
                            })
                        }
                    }
                }

                cbSelectAll.setOnCheckedChangeListener { _, isChecked ->
                    tvSubjectFirst.isSelected = isChecked
                    tvSubjectSecond.isSelected = isChecked
                    tvSubjectThird.isSelected = isChecked
                    listener.itemClicked(ArrayList<Int>().apply {
                        addAll(subject.subjectLevel.map { it.id })
                    })
                }
            }
        }
    }
}