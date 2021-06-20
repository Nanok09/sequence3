package com.example.sequence3.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sequence3.data.model.ProfilListeToDo

@Dao
interface ProfilListToDoDao {




    @Query("SELECT * FROM profilListeToDo_table WHERE login=:login AND pass=:pass")
    suspend fun authenticate(login: String, pass: String): ProfilListeToDo?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateProfilListToDo(profilListToDo: ProfilListeToDo)
}