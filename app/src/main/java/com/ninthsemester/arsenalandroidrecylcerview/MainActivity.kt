package com.ninthsemester.arsenalandroidrecylcerview

import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View


class MainActivity : AppCompatActivity() {

    private val spanCount = 2

    private val pirates : Array<String> by lazy { resources.getStringArray(R.array.pirates) }
    private val ships : Array<String> by lazy { resources.getStringArray(R.array.ships) }

    private val listItemDecorator : DividerItemDecoration by lazy {
        DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
    }

    private val gridItemDecorator : GridSpacingItemDecoration by lazy {
        GridSpacingItemDecoration(spanCount, 25, true)
    }

    private lateinit var adapter: PiratesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when(item?.itemId) {
                R.id.action_list_view -> {
                    setupList(true)
                    true
                }
                R.id.action_grid_view -> {
                    setupList(false)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun setupList(showAsList: Boolean = true) {

        adapter = PiratesAdapter(pirates.zip(ships).toMutableList(), showAsList)

        recyclerView.removeItemDecoration(gridItemDecorator)
        recyclerView.removeItemDecoration(listItemDecorator)
        if (showAsList){
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView.addItemDecoration(listItemDecorator)
        }
        else{
            recyclerView.layoutManager = GridLayoutManager(this, spanCount)
            recyclerView.addItemDecoration(gridItemDecorator)
        }

        recyclerView.adapter = adapter
    }



    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

}
