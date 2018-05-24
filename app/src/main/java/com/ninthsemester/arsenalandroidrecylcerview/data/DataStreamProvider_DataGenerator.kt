package com.ninthsemester.arsenalandroidrecylcerview.data

import com.mooveit.library.Fakeit
import java.util.*

/**
 * Created by sahil-mac on 24/05/18.
 */
object DataStreamProvider_DataGenerator {

    fun DataStreamProvider.getContent(count: Int): List<String> {
        Fakeit.init()
        val content = mutableListOf<String>()
        for (i in 0..count) {
            content.add(Fakeit.chuckNorris().fact())
        }
        return content
    }

    fun DataStreamProvider.getPostStats(count: Int): List<DataStreamProvider.PostStats> {
        Fakeit.init()
        val stats = mutableListOf<DataStreamProvider.PostStats>()
        for (i in 0..count) {
            stats.add(DataStreamProvider.PostStats(
                    Fakeit.randomInteger(),
                    Fakeit.randomInteger(),
                    Fakeit.randomInteger()
            ))
        }
        return stats
    }

    fun DataStreamProvider.getNames(count: Int): List<String> {
        Fakeit.init()
        val nameProvider = Fakeit.name()
        val names = mutableListOf<String>()
        for (i in 0..count) {
            names.add(
                    String.format("%s %s", nameProvider.firstName(), nameProvider.lastName())
            )
        }
        return names
    }
}
