package com.gugu.dts.playlist.core.repository

import com.gugu.dts.playlist.api.`object`.IFilterGroup

interface FilterGroupRepository {
    fun list(): List<IFilterGroup>
    fun find(id: Int): IFilterGroup?
}