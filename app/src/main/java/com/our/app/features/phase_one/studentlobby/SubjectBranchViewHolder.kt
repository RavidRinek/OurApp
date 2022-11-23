package com.our.app.features.phase_one.studentlobby

import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.our.app.databinding.ItemStudentSubjectBranchSpinnerBinding
import com.our.domain.features.phase_one.models.remote.BaseSubject

class SubjectBranchViewHolder(
    itemView: ItemStudentSubjectBranchSpinnerBinding,
    private val listener: SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener
) : RecyclerView.ViewHolder(itemView.root), SubjectViewHolderInter {

    private val cbSpinnerSubBranchResult: CheckBox = itemView.cbSpinnerSubBranchResult

    override fun bind(baseSubject: BaseSubject) {
        cbSpinnerSubBranchResult.apply {
            text = baseSubject.name
            cbSpinnerSubBranchResult.setOnClickListener {
                listener.itemClicked(baseSubject)
            }
        }
    }
}