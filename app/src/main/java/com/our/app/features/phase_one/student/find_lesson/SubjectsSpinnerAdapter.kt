package com.our.app.features.phase_one.student.find_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.our.app.R
import com.our.app.databinding.ItemSubjectAndLevelChoiceBinding
import com.our.domain.features.phase_one.models.remote.BaseSubject

class SubjectsSpinnerAdapter(
    private val subjectMode: SubjectMode,
    private val subjects: List<BaseSubject>,
    private val listener: OnSubjectsSpinnerAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class SubjectMode {
        MAIN,
        BRANCH
    }

    interface OnSubjectsSpinnerAdapterListener {
        fun itemClicked(baseSubject: BaseSubject)
        fun itemClicked(lessonId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SubjectMode.MAIN.ordinal -> {
                SubjectMainViewHolder(
                    ItemSubjectAndLevelChoiceBinding.bind(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_subject_and_level_choice, parent, false)
                    ),
                    listener
                )
            }
            SubjectMode.BRANCH.ordinal -> {
                SubjectBranchViewHolder(
                    ItemSubjectAndLevelChoiceBinding.bind(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_subject_and_level_choice, parent, false)
                    ),
                    listener
                )
            }
            else -> throw ClassNotFoundException("Couldn't find viewHolder by viewType position")
        }
    }

    override fun getItemCount(): Int = subjects.size

    override fun getItemViewType(position: Int): Int = subjectMode.ordinal

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SubjectViewHolderInter) {
            holder.bind(subjects[position])
        } else {
            throw ClassCastException("viewHolder must implement ${SubjectViewHolderInter::class.java.name}")
        }
    }
}