package com.gugu.dts.playlist.api.`object`

interface IFilterGroup {
    /**
     * filters
     */
    val filters: List<IFilter>

    /**
     * logic relation between filters
     * @see com.gugu.dts.playlist.api.constants.Logic
     */
    val logic: Boolean

    fun filter(songs: List<ISong>): List<ISong>
}