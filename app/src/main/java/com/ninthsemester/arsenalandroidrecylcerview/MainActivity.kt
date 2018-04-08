package com.ninthsemester.arsenalandroidrecylcerview

import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils


class MainActivity : AppCompatActivity() {

    private val spanCount = 2

    private var showAsList: Boolean = false

    private val pirates: Array<String> by lazy { resources.getStringArray(R.array.pirates) }
    private val ships: Array<String> by lazy { resources.getStringArray(R.array.ships) }

    private val listItemDecorator: DividerItemDecoration by lazy {
        DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
    }

    private val gridItemDecorator: GridSpacingItemDecoration by lazy {
        GridSpacingItemDecoration(spanCount, 25, true)
    }

    private lateinit var adapter: PiratesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupList()

        btn.setOnClickListener{
            adapter.addItem(Pair("Sahil", "Patel"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean =
            when (showAsList) {
                true -> {
                    val item = menu?.findItem(R.id.action_list_view)
                    item?.isVisible = false
                    true
                }
                false -> {
                    val item = menu?.findItem(R.id.action_grid_view)
                    item?.isVisible = false
                    true
                }
            }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
//            R.id.action_list_view -> {
//                showAsList = true
//                setupList()
//                invalidateOptionsMenu()
//                true
//            }
//            R.id.action_grid_view -> {
//                showAsList = false
//                setupList()
//                invalidateOptionsMenu()
//                true
//            }

            R.id.action_add -> {
                adapter.addItem(Pair("Armando Salazar", "Silent Mary"))
                true
            }

            R.id.action_remove -> {
                adapter.removeItem(3)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setupList() {

        adapter = PiratesAdapter(this, pirates.zip(ships).toMutableList(), showAsList)

        recyclerView.itemAnimator = MyItemAnimator(this)

        recyclerView.removeItemDecoration(gridItemDecorator)
        recyclerView.removeItemDecoration(listItemDecorator)
        if (showAsList) {
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView.addItemDecoration(listItemDecorator)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, spanCount)
            recyclerView.addItemDecoration(gridItemDecorator)
        }

        runLayoutAnimation(showAsList)
        recyclerView.adapter = adapter


    }


    private fun setupAnimationForIcon() {

        val animation = AnimationUtils.loadAnimation(this, R.anim.item_click_animation)
        animation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                ic_view.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {
                ic_view.visibility = View.VISIBLE
            }
        })
        ic_view.startAnimation(animation)

    }

    private fun runLayoutAnimation(showAsList: Boolean = true) {

        val animationController = if (showAsList)
            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        else
            AnimationUtils.loadLayoutAnimation(this, R.anim.grid_layout_animation_from_bottom)

        recyclerView.layoutAnimation = animationController
        recyclerView.scheduleLayoutAnimation()
    }
}
