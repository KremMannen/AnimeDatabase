package com.example.eksamensapp.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room
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
            Log.d("UserIdeaDbRepository getUserIdeas", e.toString())
            return emptyList()
        } catch (e: SQLException) {
            Log.e("UserIdeaDbRepository getUserIdeas", e.toString())
            return emptyList()
        }
    }

    suspend fun getUserIdeaById(id: Int): UserIdeaEntity? {
         try {
             return _userIdeaDao.getIdeaById(id)
        } catch (e: Exception) {
            Log.d("UserIdeaDbRepository getUserIdeaById", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("UserIdeaDbRepository getUserIdeaById", e.toString())
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
            Log.d("UserIdeaDbRepository getUserIdeaByTitle", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("UserIdeaDbRepository getUserIdeaByTitle", e.toString())
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
            Log.d("UserIdeaDbRepository deleteUserIdeaById", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("UserIdeaDbRepository deleteUserIdeaById", e.toString())
            return null
        }
    }

    suspend fun updateUserIdea(userIdeaEntity: UserIdeaEntity) : Int? {
         try {
           return _userIdeaDao.updateIdea(userIdeaEntity)
        } catch (e: Exception) {
            Log.d("UserIdeaDbRepository updateUserIdea", e.toString())
            return -1
        } catch (e: SQLException) {
            Log.e("UserIdeaDbRepository updateUserIdea", e.toString())
            return -1
        }
    }

    suspend fun insertUserIdea(userIdeaEntity: UserIdeaEntity) : Long {
        try {
             return _userIdeaDao.insertIdea(userIdeaEntity)

        } catch (e: Exception) {
            Log.d("UserIdeaDbRepository insertUserIdea", e.toString())
            return -1L
        } catch (e: SQLException) {
            Log.e("UserIdeaDbRepository insertUserIdea", e.toString())
            return -1L
        }
    }
}