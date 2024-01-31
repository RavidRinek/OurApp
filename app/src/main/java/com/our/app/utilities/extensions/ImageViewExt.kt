package com.our.app.utilities.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.our.app.R

/**
 * Load model into ImageView as a circle image with borderSize (optional) using Glide
 *
 * @param model - Any object supported by Glide (Uri, File, Bitmap, String, resource id as Int, ByteArray, and Drawable)
 * @param borderSize - The border size in pixel
 * @param borderColor - The border color
 */
fun <T> ImageView.loadCircularImage(
    model: T,
    borderSize: Float = 0F,
    borderColor: Int = Color.WHITE,
    placeholder: Int = R.drawable.ic_student_subjects_main
) {
    context?.isAvailable()?.apply {
        Glide.with(this.applicationContext)
            .asBitmap()
            .load(model)
            .override(600, 400)
            .placeholder(placeholder)
            .apply(RequestOptions.circleCropTransform())
            .into(object : BitmapImageViewTarget(this@loadCircularImage) {
                override fun setResource(resource: Bitmap?) {
                    context?.isAvailable()?.apply {
                        setImageDrawable(
                            resource?.run {
                                RoundedBitmapDrawableFactory.create(
                                    resources,
                                    if (borderSize > 0) {
                                        createBitmapWithBorder(borderSize, borderColor)
                                    } else {
                                        this
                                    }
                                ).apply {
                                    isCircular = true
                                }
                            }
                        )
                    }
                }
            })
    }
}

@JvmOverloads
fun ImageView.loadImage(
    url: String?,
    error: Int? = null,
    fitCenter: Boolean = false,
    centerCrop: Boolean = false,
    resize: Pair<Int, Int>? = null,
    onLoadStart: () -> Unit = {},
    onLoaded: () -> Unit = {},
    onError: ((Exception?) -> Unit)? = null,
    transformations: List<BitmapTransformation> = listOf(),
    placeholder: Int? = null,
    cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
    rounded: Boolean = false
) {
    context?.isAvailable()?.let {
        Glide.with(it.applicationContext)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(if (rounded) 25 else 1))
            .applyLoading(
                error,
                placeholder,
                fitCenter,
                centerCrop,
                onLoadStart,
                onLoaded,
                onError,
                transformations,
                resize,
                cacheStrategy,
                url
            )
            .into(this)
    }
}

@JvmOverloads
fun Context.loadBitmap(
    url: String?,
    error: Int? = null,
    fitCenter: Boolean = false,
    centerCrop: Boolean = false,
    resize: Pair<Int, Int>? = null,
    onLoadStart: () -> Unit = {},
    onError: ((Exception?) -> Unit)? = null,
    transformations: List<BitmapTransformation> = listOf(),
    placeholder: Int? = null,
    cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
    onBitmapReady: (Bitmap) -> Unit,
) {
    Glide.with(applicationContext)
        .asBitmap()
        .load(url)
        .applyLoading(
            error,
            placeholder,
            fitCenter,
            centerCrop,
            onLoadStart,
            {},
            onError,
            transformations,
            resize,
            cacheStrategy,
            url
        )
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                onBitmapReady(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) = Unit
        })
}

@JvmOverloads
fun ImageView.loadImage(
    @DrawableRes res: Int,
    error: Int? = null,
    fitCenter: Boolean = false,
    centerCrop: Boolean = false,
    onLoadStart: () -> Unit = {},
    onLoaded: () -> Unit = {},
    resize: Pair<Int, Int>? = null,
    onError: ((Exception?) -> Unit)? = null,
    transformations: List<BitmapTransformation> = listOf(),
    placeholder: Int? = null,
    cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL
) {
    context?.isAvailable()?.let {
        Glide.with(it.applicationContext)
            .load(res)
            .applyLoading(
                error,
                placeholder,
                fitCenter,
                centerCrop,
                onLoadStart,
                onLoaded,
                onError,
                transformations,
                resize,
                cacheStrategy,
                "Res: $res"
            )
            .into(this)
    }
}

@SuppressLint("CheckResult")
private fun <T> RequestBuilder<T>.applyLoading(
    error: Int? = null,
    placeholder: Int? = null,
    fitCenter: Boolean = false,
    centerCrop: Boolean = false,
    onLoadStart: () -> Unit,
    onLoaded: () -> Unit,
    onError: ((Exception?) -> Unit)? = null,
    transformations: List<BitmapTransformation> = listOf(),
    /*width to height*/ resize: Pair<Int, Int>? = null,
    cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
    url: String? = null
): RequestBuilder<T> =
    apply(RequestOptions.diskCacheStrategyOf(cacheStrategy).apply {
        if (centerCrop) centerCrop()
        if (fitCenter) fitCenter()
        error?.let {
            error(it)
        }
        placeholder?.let {
            placeholder(it)
        }
        resize?.let { (width, height) ->
            override(width, height)
            downsample(DownsampleStrategy.CENTER_INSIDE)
        }
        addListener(object : RequestListener<T> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<T>?,
                isFirstResource: Boolean
            ): Boolean {
                onError?.invoke(e)
                return false
            }

            override fun onResourceReady(
                resource: T,
                model: Any?,
                target: Target<T>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoaded()
                return false
            }
        })

        transformations.takeIf { it.isNotEmpty() }?.let {
            val transformation =
                when {
                    centerCrop -> listOf(CenterCrop()) + it
                    fitCenter -> listOf(FitCenter()) + it
                    else -> it
                }
            transform(*(transformation.toTypedArray()))
        }
        onLoadStart()
    })

/**
 * Create a new bordered bitmap with the specified borderSize and borderColor
 *
 * @param borderSize - The border size in pixel
 * @param borderColor - The border color
 * @return A new bordered bitmap with the specified borderSize and borderColor
 */
private fun Bitmap.createBitmapWithBorder(borderSize: Float, borderColor: Int): Bitmap {
    val borderOffset = (borderSize * 2).toInt()
    val halfWidth = width / 2
    val halfHeight = height / 2
    val circleRadius = Math.min(halfWidth, halfHeight).toFloat()
    val newBitmap = Bitmap.createBitmap(
        width + borderOffset,
        height + borderOffset,
        Bitmap.Config.ARGB_8888
    )

    // Center coordinates of the image
    val centerX = halfWidth + borderSize
    val centerY = halfHeight + borderSize

    val paint = Paint()
    val canvas = Canvas(newBitmap).apply {
        // Set transparent initial area
        drawARGB(0, 0, 0, 0)
    }

    // Draw the transparent initial area
    paint.isAntiAlias = true
    paint.style = Paint.Style.FILL
    canvas.drawCircle(centerX, centerY, circleRadius, paint)

    // Draw the image
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, borderSize, borderSize, paint)

    // Draw the createBitmapWithBorder
    paint.xfermode = null
    paint.style = Paint.Style.STROKE
    paint.color = borderColor
    paint.strokeWidth = borderSize
    canvas.drawCircle(centerX, centerY, circleRadius, paint)
    return newBitmap
}