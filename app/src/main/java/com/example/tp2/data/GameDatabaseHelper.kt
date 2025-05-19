package com.example.tp2.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GameDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
        companion object {
            private const val DATABASE_NAME = "game.db"
            private const val DATABASE_VERSION = 2

            private const val TABLE_USERS = "users"
            private const val COLUMN_NOMBRE = "nombre"
            private const val COLUMN_PUNTAJE_ACTUAL ="puntaje_actual"
            private const val COLUMN_PUNTAJE_MAYOR = "mayor_puntaje"
            private const val  COLUMN_FALLOS = "fallos"
        }

        override fun onCreate(db: SQLiteDatabase){
            val createTable="""
                CREATE TABLE $TABLE_USERS (
                $COLUMN_NOMBRE TEXT PRIMARY KEY,
                $COLUMN_PUNTAJE_ACTUAL INTEGER,
                $COLUMN_PUNTAJE_MAYOR INTEGER,
                $COLUMN_FALLOS INTEGER
                )
            """.trimIndent()
            db.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
            onCreate(db)
        }

    fun addUser(nombre: String){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_PUNTAJE_ACTUAL,0)
            put(COLUMN_PUNTAJE_MAYOR,0)
            put(COLUMN_FALLOS,0)
        }
        db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun getUser(name: String): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_NOMBRE,COLUMN_PUNTAJE_ACTUAL,COLUMN_PUNTAJE_MAYOR, COLUMN_FALLOS),
            "$COLUMN_NOMBRE = ?",
            arrayOf(name),
            null,null,null
        )
        return if(cursor.moveToFirst()) {
            val user = User(
                nombre = cursor.getString(0),
                puntajeActual = cursor.getInt(1),
                mayorPuntaje = cursor.getInt(2),
                fallos = cursor.getInt(3)
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

    fun updateFallos(name: String, fallos: Int){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FALLOS, fallos)
        }
        db.update(TABLE_USERS, values, "$COLUMN_NOMBRE = ?", arrayOf(name))
    }
}