package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.*

class Rule(
        override val filterGroups: List<Pair<Int, IFilterGroup>>,
        override val total: Int
) : IRule {
    override fun generatePlayList(library: IMusicLibrary): IPlayList {
        val results = filterGroups.map { it.first to it.second.filter(library.songs).toMutableList() }
        val playList = PlayList()

        var totalCount = 0
        var filterCursor = 0
        while (totalCount < total) {
            val num = results[filterCursor].first
            val candidates = results[filterCursor].second

            if (candidates.size < num || num == 0) {
                break
            }

            repeat(num) {
                if (candidates.size > 0 && totalCount < total) {
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