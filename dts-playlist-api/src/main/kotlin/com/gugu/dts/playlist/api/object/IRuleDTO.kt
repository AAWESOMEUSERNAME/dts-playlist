package com.gugu.dts.playlist.api.`object`

interface IRuleDTO {
    val filters: List<Pair<Int, IFilterDTO>>
    val repeatable: Boolean
    val totalNeeded: Int
}

interface IFilterDTO{
    val startBpm: Double
    val endBpm: Double
}