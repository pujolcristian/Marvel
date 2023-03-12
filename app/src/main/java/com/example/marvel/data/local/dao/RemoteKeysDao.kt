package com.example.marvel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvel.data.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys_table WHERE comic_id = :comicId")
    suspend fun remoteKeysComicId(comicId: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}