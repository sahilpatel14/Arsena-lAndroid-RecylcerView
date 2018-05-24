package com.ninthsemester.arsenalandroidrecylcerview.data

import android.os.Handler
import android.util.Log
import com.mooveit.library.Fakeit
import com.ninthsemester.arsenalandroidrecylcerview.data.DataStreamProvider_DataGenerator.getContent
import com.ninthsemester.arsenalandroidrecylcerview.data.DataStreamProvider_DataGenerator.getNames
import com.ninthsemester.arsenalandroidrecylcerview.data.DataStreamProvider_DataGenerator.getPostStats
import java.util.*

/**
 * Created by sahil-mac on 24/05/18.
 */
object DataStreamProvider {

    private const val MAX_POSTS_IN_ONE_CALL = 4
    private const val MAX_LATENCY = 5000
    private const val MIN_LATENCY = 1000
    private val streamHandler: Handler = Handler()
    private lateinit var streamRunnable : Runnable

    fun getData(): List<Post> {
        Fakeit.init()
        val postCount = Fakeit.randomInteger(maxValue = MAX_POSTS_IN_ONE_CALL)
        return getData(postCount)
    }


    fun getRandomDelay() : Long{
        Fakeit.init()
        return Fakeit.randomInteger(minValue = MIN_LATENCY, maxValue = MAX_LATENCY).toLong()
    }

    fun joinTheDataStream(listener: (List<Post>)-> Unit) {
        setupStreamRunnable(listener)
        streamHandler.post(streamRunnable)
    }

    fun setupStreamRunnable(listener: (List<Post>) -> Unit) {
        streamRunnable = Runnable {
            while (true) {
                listener.invoke(getData())

                Thread.sleep(getRandomDelay())
            }
        }
    }

    fun leaveTheDataStream() {
        streamHandler.removeCallbacks(streamRunnable)
    }

    fun getData(count: Int): List<Post> {

        val posts = mutableListOf<(Post)>()
        val names = getNames(count)
        val postStats = getPostStats(count)
        val content = getContent(count)

        for (i in 0..count) {
            val (likes, comment, shares) = postStats[i]
            posts.add(
                    Post(names[i], content[i],
                    likes, comment, shares)
            )
        }
        return posts
    }

    fun Fakeit.Companion.randomInteger(
            minValue : Int = 1,
            maxValue: Int = 10000) : Int = minValue + Random().nextInt(maxValue)


    data class PostStats(val likes: Int, val comment: Int, val shares: Int)
}