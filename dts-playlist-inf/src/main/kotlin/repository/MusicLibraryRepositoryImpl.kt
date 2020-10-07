package repository

import com.gugu.dts.playlist.api.`object`.IMusicLibrary
import com.gugu.dts.playlist.api.`object`.IMusicLibraryDTO
import com.gugu.dts.playlist.api.`object`.ISongDTO
import com.gugu.dts.playlist.core.entity.MusicLibrary
import com.gugu.dts.playlist.core.entity.Song
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository
import com.gugu.dts.playlist.inf.mapper.MusicLibraryMapper
import com.gugu.dts.playlist.inf.mapper.SongMapper
import java.text.SimpleDateFormat

class MusicLibraryRepositoryImpl(
        private val libraryMapper: MusicLibraryMapper,
        private val songMapper: SongMapper
) : MusicLibraryRepository {

    override fun import(library: IMusicLibraryDTO): MusicLibrary {
        val entity = toEntity(library)
        libraryMapper.insertSelective(entity)
        library.songs.forEach {
            val song = toEntity(it)
            song.libraryId = entity.id
            songMapper.insertSelective(song)
        }

        return toModule(libraryMapper.selectByPrimaryKey(entity.id))!!
    }

    override fun deleteLibById(currentLibId: Long) {
        val id = currentLibId.toInt()
        songMapper.deleteByLibId(id)
        libraryMapper.deleteByPrimaryKey(id)
    }

    override fun find(id: Long): IMusicLibrary? {
        return toModule(libraryMapper.selectByPrimaryKey(id.toInt()))
    }

    override fun fetchLibraryByName(name: String): MusicLibrary? {
        return toModule(libraryMapper.selectByName(name))
    }

    override fun list(): List<MusicLibrary> {
        return libraryMapper.list().map { toModule(it)!! }
    }

    private fun toModule(entity: com.gugu.dts.playlist.inf.entity.MusicLibrary?): MusicLibrary? {
        if (entity == null) {
            return null
        }

        val songs = songMapper.selectByLibraryId(entity.id).map { toModule(it!!) }.toList()
        return MusicLibrary(
                entity.name,
                entity.path,
                songs,
                entity.id,
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(entity.createAt)
        )
    }

    private fun toModule(entity: com.gugu.dts.playlist.inf.entity.Song): Song {
        return Song(
                id = entity.id,
                name = entity.name,
                path = entity.path,
                bpm = entity.bpm
        )
    }

    private fun toEntity(dto: IMusicLibraryDTO): com.gugu.dts.playlist.inf.entity.MusicLibrary {
        val entity = com.gugu.dts.playlist.inf.entity.MusicLibrary()
        entity.name = dto.name
        entity.path = dto.path
        return entity
    }

    private fun toEntity(dto: ISongDTO): com.gugu.dts.playlist.inf.entity.Song {
        val song = com.gugu.dts.playlist.inf.entity.Song()
        song.bpm = dto.bpm
        song.name = dto.name
        song.path = dto.path
        return song
    }
}
