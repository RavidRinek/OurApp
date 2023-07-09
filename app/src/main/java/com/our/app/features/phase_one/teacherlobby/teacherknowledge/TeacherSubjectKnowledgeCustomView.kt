package com.our.app.features.phase_one.teacherlobby.teacherknowledge

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.our.app.R
import com.our.app.databinding.CvTeacherSubjectsKnowledgeBinding
import com.our.app.features.phase_one.teacherlobby.create.TeacherSubjectLevelsAdapter
import com.our.domain.features.phase_one.models.remote.Subject

class TeacherSubjectKnowledgeCustomView(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_teacher_subjects_knowledge, this, true)
    private val viewBinding = CvTeacherSubjectsKnowledgeBinding.bind(view)

    private val selectedItemLevelHashSet = HashSet<Int>()

    init {
        viewBinding.clSubjectsSpinner.setOnClickListener {
            it.isSelected = !it.isSelected
            viewBinding.rvSubjectsSpinner.isVisible = it.isSelected
            viewBinding.ivArrow.setImageResource(if (it.isSelected) R.drawable.ic_arrow_orange_up else R.drawable.ic_arrow_orange_down)
        }
    }

    fun initViews(subject: List<Subject>) {
        viewBinding.apply {
            rvSubjectsSpinner.adapter = TeacherSubjectLevelsAdapter(subject,
                object : TeacherSubjectLevelsAdapter.OnTeacherSubjectLevelsAdapterListener {
                    override fun clickedItemLevel(id: Int?) {
                        id?.let { selectedItemLevelHashSet.add(id) }
                    }
                })
        }
    }

    fun dismissRvSubjectsVisibility() {
        viewBinding.clSubjectsSpinner.isSelected = false
        viewBinding.ivArrow.setImageResource(R.drawable.ic_arrow_orange_down)
        viewBinding.rvSubjectsSpinner.isVisible = viewBinding.clSubjectsSpinner.isSelected
    }

    fun getSelectedItemLevels() = selectedItemLevelHashSet
}