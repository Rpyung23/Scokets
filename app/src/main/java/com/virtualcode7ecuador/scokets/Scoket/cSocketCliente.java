package com.virtualcode7ecuador.scokets.Scoket;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class cSocketCliente
{
    private String ip_;
    private int port_;
    private Socket cs;
    private String mensaje;
    private Context context_;
    private DataOutputStream dataOutputStreamEnvio;
    public cSocketCliente(String aux,int aux2,Context context)
    {
        this.ip_ = aux;
        this.port_ = aux2;
        this.context_ = context;
    }
    public void ConnecSockets()
    {
        try {
            cs = new Socket(ip_,port_);
            Log.i("SocketOk","OK");
        } catch (IOException e) {
            Log.i("Socket","Error : "+e.getMessage());
        }
    }

    public Socket getCs() {
        return cs;
    }

    public void setCs(Socket cs) {
        this.cs = cs;
    }

    public String getIp_() {
        return ip_;
    }

    public void setIp_(String ip_) {
        this.ip_ = ip_;
    }

    public int getPort_() {
        return port_;
    }

    public void setPort_(int port_) {
        this.port_ = port_;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void EnviarMensaje()
    {
        try {
            if (cs!=null)
            {
                dataOutputStreamEnvio = new DataOutputStream(cs.getOutputStream());
                dataOutputStreamEnvio.writeUTF(mensaje);
                Toast.makeText(context_, "Mensaje enviado", Toast.LENGTH_LONG).show();
            }else
                {
                    Toast.makeText(context_, "Error ClienteServer Null", Toast.LENGTH_LONG).show();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            cs.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
