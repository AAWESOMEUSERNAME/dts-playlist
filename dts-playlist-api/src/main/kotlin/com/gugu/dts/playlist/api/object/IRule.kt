package com.gugu.dts.playlist.api.`object`

interface IRule {
    /**
     * Pair(how much songs should this group generate, this group)
     */
    val filterGroups: List<IFilterGroup>

    /**
     * total numbers generated playlist should contains
     */
    val total: Int
    val fairlyMod: Boolean
    fun generatePlayList(library: IMusicLibrary): IPlayList
}