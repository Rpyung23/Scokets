package com.virtualcode7ecuador.scokets.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.virtualcode7ecuador.scokets.Poo.cSocketServer;

import java.util.ArrayList;


public class SQLiteServerCrud
{
    private SQLiteServer sqLiteServer;
    private Context context;
    public SQLiteServerCrud(Context context,SQLiteServer sqLiteServer)
    {
        this.context = context;
        this.sqLiteServer =  sqLiteServer;
    }
    public boolean registrarDatos(String name_ ,String ip_ ,int port)
    {
        try {
            String sql_ = "INSERT INTO server VALUES ('"+ip_+"',"+port+",'"+name_+"')";
            SQLiteDatabase sqLiteDatabase_ = sqLiteServer.getWritableDatabase();
            sqLiteDatabase_.execSQL(sql_);
            return true;
        }catch (Exception ee)
        {
            Log.i("SQLite",ee.getMessage());
            return false;
        }
    }
    public ArrayList<cSocketServer> leerDatos()
    {
        ArrayList<cSocketServer>oSocketServerArrayList = new ArrayList<>();
        try {

            SQLiteDatabase sqLiteDatabase = sqLiteServer.getReadableDatabase();
            String  sql_read  = "SELECT * FROM server";
            Cursor cursor = sqLiteDatabase.rawQuery(sql_read,null);
            while (cursor.moveToNext())
            {
                cSocketServer oS = new cSocketServer();
                oS.setIp_server(cursor.getString(0));
                oS.setName(cursor.getString(2));
                oS.setPort(cursor.getInt(1));
                oSocketServerArrayList.add(oS);
            }
            Log.i("SQLite","lecturaBN");
            return oSocketServerArrayList;
        }catch (Exception ex)
        {
            Log.i("SQLite",ex.getMessage());
            return oSocketServerArrayList;
        }
    }
}
