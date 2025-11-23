package com.example.eksamensapp.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import java.lang.Exception
import java.sql.SQLException

object UserIdeaDbRepository {

    private lateinit var _appDatabase: AppDatabase
    private val _userIdeaDao by lazy { _appDatabase.userIdeaDao() }


    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "anime-database"
        )
            .build()
    }

    suspend fun getUserIdeas(): List<UserIdeaEntity> {
         try {
             return _userIdeaDao.getIdeas()
        } catch (e: java.lang.Exception) {
            Log.d("getUserIdeasCatch", e.toString())
            return emptyList()
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return emptyList()
        }
    }

    suspend fun getUserIdeaById(id: Int): UserIdeaEntity? {
         try {
             return _userIdeaDao.getIdeaById(id)
        } catch (e: Exception) {
            Log.d("getUserIdeaByIdCatch", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return null
        }
    }

    suspend fun getUserIdeaByTitle(title: String) : UserIdeaEntity? {
         try {
             var userIdeaEntityList = _userIdeaDao.getIdeaByTitle(title)

             if (userIdeaEntityList != null) {
                 Log.i("getAnimeByTitle", "Fant anime i DB")
                 return userIdeaEntityList
             }
             return null
        } catch (e: Exception) {
            Log.d("getAnimeByTitleCatch", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return null
        }
    }

    suspend fun deleteUserIdeaById(id: Int): UserIdeaEntity? {
        try {
            val idea = _userIdeaDao.getIdeaById(id)

            if (idea != null) {
                _userIdeaDao.deleteIdeaById(id)
                return idea
            }
            return null
        } catch (e: Exception) {
            Log.d("deleteUserIdeaByIdCatch", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved sletting av data ${e.message}")
            return null
        }
    }

    suspend fun updateUserIdea(userIdeaEntity: UserIdeaEntity) : Int? {
         try {
           return _userIdeaDao.updateIdea(userIdeaEntity)
        } catch (e: Exception) {
            Log.d("updateUserIdeaCatch", e.toString())
            return -1
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppdatering av data ${e.message}")
            return -1
        }
    }

    suspend fun insertUserIdea(userIdeaEntity: UserIdeaEntity) : Long {
        try {
             return _userIdeaDao.insertIdea(userIdeaEntity)

        } catch (e: Exception) {
            Log.d("insertUserIdeaCatch", e.toString())
            return -1L
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
            return -1L
        }
    }
}