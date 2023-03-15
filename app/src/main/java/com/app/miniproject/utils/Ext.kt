package com.app.miniproject.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import android.widget.TextView
import com.app.miniproject.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import java.text.NumberFormat
import java.util.*

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.expand() {
    val v = this
    val matchParentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = v.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.layoutParams.height = 1
    v.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            v.layoutParams.height =
                if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            v.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Expansion speed of 1dp/ms
    a.duration = ((targetHeight / v.context.resources.displayMetrics.density).toInt()).toLong()
    v.startAnimation(a)
}

fun View.collapse() {
    val v = this
    val initialHeight = v.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                v.visibility = View.GONE
            } else {
                v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Collapse speed of 1dp/ms
    a.duration = ((initialHeight / v.context.resources.displayMetrics.density).toInt()).toLong()
    v.startAnimation(a)
}

fun View.showSnackBar(layoutInflater: LayoutInflater, content: String) {
    val snackBar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
    val customSnackBarView = layoutInflater.inflate(R.layout.custom_snack_bar_view, null)
    snackBar.view.setBackgroundColor(Color.TRANSPARENT)
    val snackBarLayout = snackBar.view as SnackbarLayout
    snackBarLayout.setPadding(0, 0, 0, 0)
    val tvContent = customSnackBarView.findViewById<TextView>(R.id.tv_content)
    tvContent.text = content
    snackBarLayout.addView(customSnackBarView, 0)
    snackBar.show()
}

fun Int.formatRupiah(): String {
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(this)
}

fun Context.showAlertDialogInformation(
    layoutInflater: LayoutInflater,
    title: String,
    subTitle: String,
    onOkClickCallback: (AlertDialog, ProgressBar) -> Unit,
) {
    val builder = AlertDialog.Builder(this)
    val customLayout = layoutInflater.inflate(R.layout.custom_alert_dialog_information, null)
    builder.setView(customLayout)
    val tvTitle = customLayout.findViewById<TextView>(R.id.tv_title)
    val tvSubTitle = customLayout.findViewById<TextView>(R.id.tv_sub_title)
    val btnOk = customLayout.findViewById<TextView>(R.id.tv_ok)
    val btnCancel = customLayout.findViewById<TextView>(R.id.tv_cancel)
    val progressBar = customLayout.findViewById<ProgressBar>(R.id.progress_bar)
    val dialog = builder.create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCanceledOnTouchOutside(false)
    tvTitle.text = title
    tvSubTitle.text = subTitle
    btnOk.setOnClickListener {
        onOkClickCallback.invoke(dialog, progressBar)
    }
    btnCancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}