package com.gugu.dts.playlist.api

import com.gugu.dts.playlist.api.`object`.IMusicLibrary
import com.gugu.dts.playlist.api.`object`.IMusicLibraryDTO
import com.gugu.dts.playlist.api.`object`.IRule
import com.gugu.dts.playlist.api.`object`.IRuleDTO

interface ICommander {
    fun importLibray(library: IMusicLibraryDTO): IMusicLibrary
    fun deleteLibById(currentLibId: Long)
    fun createRule(ruleDto: IRuleDTO): IRule
}