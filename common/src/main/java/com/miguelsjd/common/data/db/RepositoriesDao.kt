package com.miguelsjd.common.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miguelsjd.common.data.models.RepositoryEntity

@Dao
internal interface RepositoriesDao {
    @Query(
        """
    SELECT * FROM repositories
    WHERE language = :language
    ORDER BY stars DESC, id DESC
    LIMIT :limit OFFSET :offset
    """
    )
    suspend fun getRepositoriesByLanguage(
        language: String,
        offset: Int,
        limit: Int
    ): List<RepositoryEntity>

    @Query("SELECT * FROM repositories WHERE id = :id LIMIT 1")
    suspend fun getRepositoryById(id: Int): RepositoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepository(repository: RepositoryEntity)
}
