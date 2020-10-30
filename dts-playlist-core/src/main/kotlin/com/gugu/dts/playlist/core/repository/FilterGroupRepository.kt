package com.gugu.dts.playlist.core.repository

import com.gugu.dts.playlist.api.`object`.IFilterGroupDTO
import com.gugu.dts.playlist.core.entity.FilterGroup

interface FilterGroupRepository {
    fun list(): List<FilterGroup>
    fun find(id: Int): FilterGroup?
    fun updateFilterGroup(group: FilterGroup)
    fun deleteFilterGroupById(id: Int)
    fun insert(dto: IFilterGroupDTO)
}