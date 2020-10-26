package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.*

class Rule(
        override val filterGroups: List<IFilterGroup>,
        override val total: Int,
        override val fairlyMod: Boolean = false
) : IRule {
    override fun generatePlayList(library: IMusicLibrary): IPlayList {
        val allSongs = library.songs

        val results = filterGroups.map { it.sum to it.filter(allSongs).toMutableList() }
        val playList = PlayList()

        var totalCount = 0
        var filterCursor = 0
        while (totalCount < total) {
            val num = results[filterCursor].first
            val candidates = results[filterCursor].second

            if (candidates.size == 0 || num == 0) {
                break
            }

            val usedTimesArray = candidates.map { it.usedTimes }.toSet().toList().sorted().toIntArray()
            repeat(if (candidates.size < num) candidates.size else num) {
                if (candidates.size > 0 && totalCount < total) {

                    var filteredCandidates = listOf<ISong>()

                    if (fairlyMod) {
                        for (i in usedTimesArray) {
                            filteredCandidates = candidates.filter { it.usedTimes == i }
                            if (filteredCandidates.isNotEmpty()) {
                                break
                            }
                        }
                    } else {
                        filteredCandidates = candidates
                    }

                    val song = filteredCandidates.random()
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