package com.gugu.dts.playlist.api.`object`

interface IRule {
    /**
     * Pair(how much songs should this group generate, this group)
     */
    val filterGroups: List<Pair<Int, IFilterGroup>>

    /**
     * total numbers generated playlist should contains
     */
    val total: Int
    fun generatePlayList(library: IMusicLibrary): IPlayList
}