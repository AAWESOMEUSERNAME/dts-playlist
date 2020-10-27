package com.gugu.dts.playlist.api.`object`

interface IFilterGroup {
    val id: Int?
    /**
     * filters
     */
    val filters: List<IFilter>

    /**
     * logic relation between filters
     * @see com.gugu.dts.playlist.api.constants.Logic
     */
    val logic: Boolean

    val sum: Int

    val description: String

    val name: String?

    fun filter(songs: List<ISong>): List<ISong>
}