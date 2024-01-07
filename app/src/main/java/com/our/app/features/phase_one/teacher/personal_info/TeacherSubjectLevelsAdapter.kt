package com.our.app.features.phase_one.teacher.personal_info

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemSubjectAndLevelChoiceBinding
import com.our.app.utilities.extensions.dpToPx
import com.our.app.utilities.extensions.loadBitmap
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.SubjectLevel

class TeacherSubjectLevelsAdapter(
    private val subjects: List<Subject>,
    val listener: OnTeacherSubjectLevelsAdapterListener
) : RecyclerView.Adapter<TeacherSubjectLevelsAdapter.SubjectLevelViewHolder>() {

    private val subjectLevel = ArrayList<SubjectLevel>()

    interface OnTeacherSubjectLevelsAdapterListener {
        fun clickedItemLevel(id: Int?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectLevelViewHolder =
        SubjectLevelViewHolder(
            binding = ItemSubjectAndLevelChoiceBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_subject_and_level_choice, parent, false)
            )
        )

    override fun getItemCount(): Int = subjects.size

    override fun onBindViewHolder(holder: SubjectLevelViewHolder, position: Int) {
        holder.bind(subjects[position])
    }

    inner class SubjectLevelViewHolder(private val binding: ItemSubjectAndLevelChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
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
//                        subject.subjectLevel.getOrNull(i)?.let {
//                            text = it.name
//                            subjectLevel.add(it)
//                        }
                        setOnClickListener {
                            it.isSelected = !it.isSelected
                            listener.clickedItemLevel(subjectLevel?.id)
                        }
                    }
                }

                cbSelectAll.setOnCheckedChangeListener { _, isChecked ->
                    tvSubjectFirst.isSelected = isChecked
                    tvSubjectSecond.isSelected = isChecked
                    tvSubjectThird.isSelected = isChecked
                }
            }
        }
    }
}