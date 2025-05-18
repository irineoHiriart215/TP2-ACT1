package com.example.tp2.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.collection.arraySetOf
import androidx.core.content.contentValuesOf

class GameDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
        companion object {
            private const val DATABASE_NAME = "game.db"
            private const val DATABASE_VERSION = 1

            private const val TABLE_USERS = "users"
            private const val COLUMN_NOMBRE = "nombre"
            private const val COLUMN_PUNTAJE_ACTUAL ="puntaje_actual"
            private const val COLUMN_PUNTAJE_MAYOR = "mayor_puntaje"
        }

        override fun onCreate(db: SQLiteDatabase){
            val createTable="""
                CREATE TABLE $TABLE_USERS (
                $COLUMN_NOMBRE TEXT PRIMARY KEY,
                $COLUMN_PUNTAJE_ACTUAL INTEGER,
                $COLUMN_PUNTAJE_MAYOR INTEGER
                )
            """.trimIndent()
            db.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
            onCreate(db)
        }

    fun addUser(name: String){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, name)
            put(COLUMN_PUNTAJE_ACTUAL,0)
            put(COLUMN_PUNTAJE_MAYOR,0)
        }
        db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun getUser(name: String): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_NOMBRE,COLUMN_PUNTAJE_ACTUAL,COLUMN_PUNTAJE_MAYOR),
            "$COLUMN_NOMBRE = ?",
            arrayOf(name),
            null,null,null
        )
        return if(cursor.moveToFirst()) {
            val user = User(
                nombre = cursor.getString(0),
                puntajeActual = cursor.getInt(1),
                mayorPuntaje = cursor.getInt(2)
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun updatePuntaje(name: String, puntajeActual: Int, mayorPuntaje: Int){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PUNTAJE_ACTUAL, puntajeActual)
            put(COLUMN_PUNTAJE_MAYOR, mayorPuntaje)
        }
        db.update(TABLE_USERS, values, "$COLUMN_NOMBRE = ?", arrayOf(name))
    }

    fun getMejorUser (): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_NOMBRE, COLUMN_PUNTAJE_ACTUAL, COLUMN_PUNTAJE_MAYOR),
            null,
            null,
            null,
            null,
            "COLUMN_PUNTAJE_MAYOR DESC",
            "1"
        )
        return if (cursor.moveToFirst()){
            val user = User(
                nombre =cursor.getString(0),
                puntajeActual = cursor.getInt(1),
                mayorPuntaje = cursor.getInt(2)
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    }
