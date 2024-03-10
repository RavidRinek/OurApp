package com.our.app.features.phase_one.common

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.our.app.R
import com.our.app.databinding.CvLogoHeaderUtilBinding

class LogoHeaderUtilCustomView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val view: View = LayoutInflater.from(context)
        .inflate(R.layout.cv_logo_header_util, this, true)
    private val viewBinding = CvLogoHeaderUtilBinding.bind(view)

    init {
        val typedArray: TypedArray? = context?.obtainStyledAttributes(
            attrs,
            R.styleable.LogoHeaderUtilCustomView,
            0,
            0
        )
        viewBinding.tvCaption.text =
            typedArray?.getString(R.styleable.LogoHeaderUtilCustomView_caption)
        if (viewBinding.tvCaption.text.isEmpty()){
            viewBinding.tvCaption.visibility = View.GONE
        }
        typedArray?.recycle()
    }
}