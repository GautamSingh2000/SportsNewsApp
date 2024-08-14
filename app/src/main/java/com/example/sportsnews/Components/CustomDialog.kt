package com.mindgeeks.sportsnews.Components

import android.animation.Animator
import android.content.Context
import android.transition.TransitionManager
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection
import com.mindgeeks.sportsnews.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomDialog(
    val context: Context,
    val Title1: String,
    val Title2: String = "",
    val Error: String = "",
    val animationID: Int,
    val cancelable: Boolean = true,
    val padding: PaddingValues,
    val repeat: Boolean
) {

    private lateinit var builder: MaterialAlertDialogBuilder
    private lateinit var alertDialog_layout: View
    private lateinit var layoutParam: ViewGroup.LayoutParams
    private lateinit var alertDialog: androidx.appcompat.app.AlertDialog

    init {
        builder = MaterialAlertDialogBuilder(context)
        alertDialog_layout = LayoutInflater.from(context).inflate(R.layout.no_data_found, null)
        layoutParam = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        ShowDialog()
    }

    fun ShowDialog() {

        val title1 = alertDialog_layout.findViewById<TextView>(R.id.text1)
        val title2 = alertDialog_layout.findViewById<TextView>(R.id.text2)
        val error = alertDialog_layout.findViewById<TextView>(R.id.error_text)
        val lotti_animation =
            alertDialog_layout.findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.anim)
        val cancel_btn = alertDialog_layout.findViewById<ImageButton>(R.id.cancel_btn)
        val first_card = alertDialog_layout.findViewById<CardView>(R.id.firstcard)
        val second_card = alertDialog_layout.findViewById<CardView>(R.id.secondCard)

        title1.text = Title1
        title2.text = Title2
        error.text = Error

        if(Title2.equals("")) title2.visibility = View.GONE
        if(error.equals("")) error.visibility = View.GONE

        val leftPadding = convertDpToPx(padding.calculateLeftPadding(LayoutDirection.Ltr).value)
        val topPadding = convertDpToPx(padding.calculateTopPadding().value)
        val rightPadding = convertDpToPx(padding.calculateRightPadding(LayoutDirection.Ltr).value)
        val bottomPadding = convertDpToPx(padding.calculateBottomPadding().value)
        lotti_animation.setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
        lotti_animation.setAnimation(animationID)
        lotti_animation.playAnimation()
        lotti_animation.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    if (repeat) {
                        lotti_animation.playAnimation()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })

        alertDialog_layout.setLayoutParams(layoutParam)
        builder.setView(alertDialog_layout)

        alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(cancelable)
        alertDialog.setCancelable(cancelable)
        alertDialog.window?.setGravity(Gravity.CENTER)


        if (!cancelable) {
            var show = false
            first_card.setOnClickListener {
                when (show) {
                    true -> {
                        TransitionManager.beginDelayedTransition(second_card)
                        second_card.visibility = View.GONE

                    }
                    else -> {
                        TransitionManager.beginDelayedTransition(second_card)
                        second_card.visibility = View.VISIBLE
                    }
                }
                show = !show
            }

            cancel_btn.setOnClickListener {
                alertDialog.dismiss()
            }
        } else {
            cancel_btn.visibility = View.GONE
        }

        alertDialog.show()
    }


    private fun convertDpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

}

