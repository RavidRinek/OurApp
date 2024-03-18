package com.our.app.features.phase_one.teacher.teacherknowledge

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.our.app.R
import com.our.app.databinding.CvTeacherSubjectsKnowledgeBinding
import com.our.app.features.phase_one.teacher.personal_info.TeacherSubjectLevelsAdapter
import com.our.data.common.gfd
import com.our.domain.features.phase_one.models.remote.Subject

class TeacherSubjectKnowledgeCustomView(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_teacher_subjects_knowledge, this, true)
    private val viewBinding = CvTeacherSubjectsKnowledgeBinding.bind(view)

    private var idsList = ArrayList<Int>()

    private lateinit var listener: Listener

    interface Listener {
        fun updateSelectedState(idsListEmpty: Boolean)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    init {
        viewBinding.clSubjectsSpinner.setOnClickListener {
            it.isSelected = !it.isSelected
            viewBinding.rvSubjectsSpinner.isVisible = it.isSelected
            viewBinding.ivArrow.setImageResource(if (it.isSelected) R.drawable.ic_arrow_orange_up else R.drawable.ic_arrow_orange_down)
        }
    }

    private val tahat = Tahat()

    fun initViews(subject: List<Subject>) {
        viewBinding.apply {
            rvSubjectsSpinner.adapter = TeacherSubjectLevelsAdapter(subject,
                object : TeacherSubjectLevelsAdapter.OnTeacherSubjectLevelsAdapterListener {
                    override fun clickedItemLevel(ids: List<Int>) {
                        val a = ArrayList<Int>()
                        a.addAll(ids)
                        idsList = tahat.getIdsArray(a)
                        listener.updateSelectedState(idsList.isEmpty())
                    }
                })
        }
    }

    fun dismissRvSubjectsVisibility() {
        viewBinding.clSubjectsSpinner.isSelected = false
        viewBinding.ivArrow.setImageResource(R.drawable.ic_arrow_orange_down)
        viewBinding.rvSubjectsSpinner.isVisible = viewBinding.clSubjectsSpinner.isSelected
    }

    fun getSelectedItemLevels() = idsList
}