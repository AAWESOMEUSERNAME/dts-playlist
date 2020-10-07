package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.IFilter
import com.gugu.dts.playlist.api.`object`.IMusicLibrary
import com.gugu.dts.playlist.api.`object`.IPlayList
import com.gugu.dts.playlist.api.`object`.IRule

class Rule(
        override val filters: List<Pair<Int, IFilter>>,
        override val repeatable: Boolean,
        override val totalNeeded: Int
) : IRule {
    override fun generatePlayList(library: IMusicLibrary): IPlayList {
        val results = filters.map { it.first to it.second.filter(library.songs).toMutableList() }
        val playList = PlayList()

        var totalCount = 0
        var filterCursor = 0
        while (totalCount < totalNeeded) {
            val num = results[filterCursor].first
            val candidates = results[filterCursor].second

            if (candidates.size < num || num == 0) {
                break
            }

            repeat(num) {
                if (candidates.size > 0 && totalCount < totalNeeded) {
                    val song = candidates.random()
                    playList.add(song)
                    candidates.remove(song)
                    totalCount++
                }
            }
            filterCursor = if (filterCursor < results.size - 1) filterCursor + 1 else 0
        }
        return playList
    }

}