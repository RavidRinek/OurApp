package com.our.app.features.phase_one.student.find_lesson

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.our.app.R
import com.our.app.databinding.CvStudentSubjectsSpinnerBinding
import com.our.domain.features.phase_one.models.remote.BaseSubject

class StudentSubjectSpinnerCustomView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_student_subjects_spinner, this, true)
    private val viewBinding = CvStudentSubjectsSpinnerBinding.bind(view)
    var listOfPickedBranchLevels = ArrayList<Int>()
    private var listOfPickedBranches: ArrayList<BaseSubject> = ArrayList()

    init {
        val typedArray: TypedArray? = context?.obtainStyledAttributes(
            attrs,
            R.styleable.StudentSubjectSpinnerCustomView,
            0,
            0
        )

        setSubjectSpinnerHeader(
            icon = typedArray?.getDrawable(R.styleable.StudentSubjectSpinnerCustomView_icon),
            txt = typedArray?.getString(R.styleable.StudentSubjectSpinnerCustomView_text)
        )

        viewBinding.clSubjectsSpinner.setOnClickListener {
            it.isSelected = !it.isSelected
            viewBinding.rvSubjectsSpinner.isVisible = it.isSelected
            viewBinding.ivArrow.setImageResource(if (it.isSelected) R.drawable.ic_arrow_orange_up else R.drawable.ic_arrow_orange_down)
        }

        typedArray?.recycle()
    }

    private fun setSubjectSpinnerHeader(icon: Drawable?, txt: String?) {
        viewBinding.tvSubjectsSpinner.apply {
            setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
            text = txt
        }
    }

    fun dismissRvSubjectsVisibility() {
        viewBinding.clSubjectsSpinner.isSelected = false
        viewBinding.ivArrow.setImageResource(R.drawable.ic_arrow_orange_down)
        viewBinding.rvSubjectsSpinner.isVisible = viewBinding.clSubjectsSpinner.isSelected
    }

    fun setSubjectsItems(
        subjectMode: SubjectsSpinnerAdapter.SubjectMode,
        subjects: List<BaseSubject>,
        listener: SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener
    ) {
        val mListener = object : SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener {
            override fun itemClicked(baseSubject: BaseSubject) {
                when (subjectMode) {
                    SubjectsSpinnerAdapter.SubjectMode.MAIN -> {
                        handleItemMainClick(baseSubject.name)
                        listener.itemClicked(baseSubject)
                    }

                    SubjectsSpinnerAdapter.SubjectMode.BRANCH -> handleItemBranchClick(baseSubject)
                }
            }

            override fun itemClicked(lessonId: Int) {
                handleItemBranchLevelClick(lessonId)
            }
        }
        viewBinding.rvSubjectsSpinner.adapter =
            SubjectsSpinnerAdapter(subjectMode, subjects, mListener)
    }

    private fun handleItemMainClick(subjectName: String) {
        dismissRvSubjectsVisibility()
        viewBinding.tvSubjectsSpinnerPick.apply {
            text = subjectName
            isVisible = true
        }
    }

    private fun handleItemBranchLevelClick(id: Int) {
        if (listOfPickedBranchLevels.contains(id)) {
            listOfPickedBranchLevels.remove(id)
        } else {
            listOfPickedBranchLevels.add(id)
        }
    }

    private fun handleItemBranchClick(baseSubject: BaseSubject) {
        if (listOfPickedBranches.contains(baseSubject)) {
            listOfPickedBranches.remove(baseSubject)
        } else {
            listOfPickedBranches.add(baseSubject)
        }

        viewBinding.tvSubjectsSpinnerPick.apply {
            when {
                listOfPickedBranches.isEmpty() -> isVisible = false
                listOfPickedBranches.size == 1 -> {
                    text = listOfPickedBranches.first().name
                    isVisible = true
                }

                else -> {
                    text = "${listOfPickedBranches.size} נבחרו"
                    isVisible = true
                }
            }
        }
    }
}