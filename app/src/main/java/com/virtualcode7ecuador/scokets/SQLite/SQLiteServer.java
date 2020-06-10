package com.virtualcode7ecuador.scokets.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteServer extends SQLiteOpenHelper
{
    Context context;
    public SQLiteServer(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);//crea la base de datos
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            String sql_create = "CREATE TABLE IF NOT EXISTS server(ip_server TEXT primary key,port INTEGER ,name TEXT not null)";
            Log.i("SQLite","crearTable");
            db.execSQL(sql_create);
        }catch (Exception db_er)
        {
            Toast.makeText(getContext(), "Error Crear DataTable : "+db_er.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("SQLite","ErrorcrearTable");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //cuado se actualiza la base de datos a una nueva version
    }

    public void setContext(Context context_)
    {
        this.context = context_;
    }

    public Context getContext()
    {
        return this.context;
    }

}
