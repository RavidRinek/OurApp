package com.our.app.features.phase_one.studentlobby

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.our.app.databinding.FragmentOrderLessonBinding
import kotlinx.parcelize.Parcelize


class OrderLessonFragment : Fragment() {

    private var _binding: FragmentOrderLessonBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderLessonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (arguments?.getParcelable(K_LESSON_INFO) as? OrderLessonUi)?.let {
            binding.apply {
                levelOfClass.text = it.level
                lessonPrice.text = it.price
                teachersName.text = it.name
                lessonDate.text = it.time
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val K_LESSON_INFO = "lesson_info"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderLessonFragment()
    }

}
    @Parcelize
    data class OrderLessonUi(
        val level: String,
        val price: String,
        val name: String,
        val time: String,
    ) : Parcelable
