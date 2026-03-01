package com.gnexus.app.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.gnexus.app.ui.screens.library.SortOrder


@Dao
interface GameDao {

	@Upsert
	suspend fun upsertAll(games: List<GameEntity>)

	fun pagingSource(sortOrder: SortOrder): PagingSource<Int, GameEntity> {
		return when (sortOrder) {
			SortOrder.RECENT -> pagingSourceOrderByRecent()
			SortOrder.A_TO_Z -> pagingSourceOrderByNameAsc()
			SortOrder.Z_TO_A -> pagingSourceOrderByNameDesc()
		}
	}

	//WHERE platform = :platform
	@Query("SELECT * FROM games ORDER BY lastPlayedDateTime DESC, name ASC")
	fun pagingSourceOrderByRecent(): PagingSource<Int, GameEntity>

	@Query("SELECT * FROM games ORDER BY name ASC")
	fun pagingSourceOrderByNameAsc(): PagingSource<Int, GameEntity>

	@Query("SELECT * FROM games ORDER BY name DESC")
	fun pagingSourceOrderByNameDesc(): PagingSource<Int, GameEntity>


	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAll(games: List<GameEntity>)

	@Query("DELETE FROM games")
	suspend fun clearAll()
}

