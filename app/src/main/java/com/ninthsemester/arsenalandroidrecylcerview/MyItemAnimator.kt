package com.ninthsemester.arsenalandroidrecylcerview

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator

/**
 * Created by sahil-mac on 08/04/18.
 */
class MyItemAnimator(private val context : Context) : DefaultItemAnimator() {

//    http://frogermcs.github.io/InstaMaterial-concept-part-5-like_action_effects/



    private val animationListener = object : Animation.AnimationListener {


        override fun onAnimationRepeat(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {}

        override fun onAnimationStart(animation: Animation?) {}
    }


    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        val animator = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        animator.setAnimationListener(object : Animation.AnimationListener {


            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                dispatchAddFinished(holder)
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        holder?.itemView?.startAnimation(animator)
        return true
    }

    override fun runPendingAnimations() {
    }

    override fun animateMove(holder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        return true
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder?, newHolder: RecyclerView.ViewHolder?, fromLeft: Int, fromTop: Int, toLeft: Int, toTop: Int): Boolean {

        val animator = AnimationUtils.loadAnimation(context, R.anim.item_click_animation)
        val myView = (newHolder as PiratesAdapter.PirateViewHolder).ivBoat

        animator.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                myView.visibility = View.GONE
                dispatchChangeFinished(newHolder, false)
            }

            override fun onAnimationStart(animation: Animation?) {
                myView.visibility = View.VISIBLE
            }
        })

        myView.startAnimation(animator)

        return false
    }


    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        val animator = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
//        animator.fillAfter = true
        holder?.itemView?.startAnimation(animator)
        animator.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                dispatchRemoveFinished(holder)
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        return true
    }
}