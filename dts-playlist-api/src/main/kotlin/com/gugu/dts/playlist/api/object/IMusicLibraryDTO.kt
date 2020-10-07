package com.gugu.dts.playlist.api.`object`

interface IMusicLibraryDTO {
    val path: String
    val name: String
    val songs: List<ISongDTO>
}
