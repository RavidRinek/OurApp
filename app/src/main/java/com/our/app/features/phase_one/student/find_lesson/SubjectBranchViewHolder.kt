package com.our.app.features.phase_one.student.find_lesson

import androidx.recyclerview.widget.RecyclerView
import com.our.app.databinding.ItemSubjectAndLevelChoiceBinding
import com.our.domain.features.phase_one.models.remote.BaseSubject

class SubjectBranchViewHolder(
    itemView: ItemSubjectAndLevelChoiceBinding,
    private val listener: SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener
) : RecyclerView.ViewHolder(itemView.root), SubjectViewHolderInter {

//    private val cbSpinnerSubBranchResult: CheckBox = itemView.cbSpinnerSubBranchResult

    override fun bind(baseSubject: BaseSubject) {
/*
        cbSpinnerSubBranchResult.apply {
            text = baseSubject.name
            cbSpinnerSubBranchResult.setOnClickListener {
                listener.itemClicked(baseSubject)
            }
        }
*/
    }
}