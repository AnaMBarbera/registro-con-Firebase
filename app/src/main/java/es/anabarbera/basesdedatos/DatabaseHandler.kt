package es.anabarbera.basesdedatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MyDatabase"
        private const val TABLE_NAME = "Contacts"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT, $KEY_EMAIL TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addContact(name:String, email:String): Long{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME,name)
        values.put(KEY_EMAIL,email)
        val sucess =db.insert(TABLE_NAME,null,values)
        db.close()
        return (sucess)
    }

fun getAllContacts():List<Contact> {
    val contactList = mutableListOf<Contact>()
    val db = this.readableDatabase
    val selectQuery = "SELECT * FROM $TABLE_NAME"
    val cursor = db.rawQuery(selectQuery, null)

    cursor.use{
        if(it.moveToFirst()) { //si no hay registro devuelve falso
            do {
                //primero sacamos el valor del id del primer registro de la query
                val id = it.getInt(it.getColumnIndex(KEY_ID))
                val name = it.getString(it.getColumnIndex(KEY_NAME))
                val email = it.getString(it.getColumnIndex(KEY_EMAIL))

                //Guardamos estos valores en una variable de la clase Contact
                val contact = Contact(id,name,email)
                //Y la añadimos a una lista de la clase contact (ya creada)
                contactList.add(contact)

            } while(it.moveToNext()) //hasta que el siguiente sea falso
        }

    }

    return contactList
    }

}

