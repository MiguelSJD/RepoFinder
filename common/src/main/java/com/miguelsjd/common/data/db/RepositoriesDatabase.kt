package com.miguelsjd.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miguelsjd.common.data.models.RepositoryEntity

@Database(
    entities = [RepositoryEntity::class],
    version = DatabaseConstants.VERSION,
    exportSchema = false
)
internal abstract class RepositoriesDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoriesDao
}
