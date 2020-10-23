package com.gugu.dts.playlist.api.`object`

interface IRuleDTO {
    /**
     * Pair(how much songs should this group generate, this group)
     */
    val filterGroups: List<Pair<Int, IFilterGroupDTO>>

    /**
     * total numbers generated playlist should contains
     */
    val total: Int

    /**
     * in this mod, songs less used will have higher priority to be chosen
     */
    val fairlyMode: Boolean
}

interface IFilterGroupDTO{
    /**
     * filters
     */
    val filters: List<IFilter>

    /**
     * logic relation between filters
     * @see com.gugu.dts.playlist.api.constants.Logic
     */
    val logic: Boolean
}