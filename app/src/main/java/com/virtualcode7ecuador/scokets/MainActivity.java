package com.virtualcode7ecuador.scokets;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.system.OsConstants;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.virtualcode7ecuador.scokets.Poo.*;
import com.virtualcode7ecuador.scokets.SQLite.*;
import com.virtualcode7ecuador.scokets.Adapter.*;
import com.virtualcode7ecuador.scokets.Scoket.cSocketCliente;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private AlertDialog alertDialog;
    private AlertDialog alertDialog_Mensaje;
    private Button button_add_server_java;
    private SQLiteServer osqLiteServer;
    private TextInputEditText textInputEditText_name;
    private TextInputEditText textInputEditText_ip;
    private TextInputEditText textInputEditText_port;
    private RecyclerView recyclerView;
    private cRecyclerViewSockets oRecyclerViewSockets;
    private SQLiteServerCrud OsqLiteServerCrud;
    private ArrayList<cSocketServer> socketServerArrayList;
    private cSocketServer oSoc;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        osqLiteServer = new SQLiteServer(MainActivity.this,"ipsockets.db",null,1);
        button_add_server_java =  findViewById(R.id.btn_add_scokets_server);
        recyclerView =  findViewById(R.id.recycler_view_scokets_server);
        comprobarFechaCad();
    }

    private void comprobarFechaCad()
    {
        DatePicker datePicker = new DatePicker(MainActivity.this);
        int  year = datePicker.getYear();
        int dia = datePicker.getDayOfMonth();
        int mes =  datePicker.getMonth();
        String finEvalua_feha = "2020/6/11";
        if (finEvalua_feha.equals(year+"/"+mes+"/"+dia))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Desarrollado por VirtualCode7");
            builder.setMessage("Prueba Gratuita Finalizada ... ");
            builder.setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else
            {
                button_add_server_java.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        generarAlertDialog();
                    }
                });
                OsqLiteServerCrud = new SQLiteServerCrud(MainActivity.this,osqLiteServer);
                llenarRecyclerView();
            }
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }
    private void llenarRecyclerView()
    {
        socketServerArrayList = new ArrayList<>();
        socketServerArrayList = OsqLiteServerCrud.leerDatos();
        oRecyclerViewSockets = new cRecyclerViewSockets(R.layout.view_recycler_sockets_server,MainActivity.this,socketServerArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(oRecyclerViewSockets);
        oRecyclerViewSockets.notifyDataSetChanged();
        oRecyclerViewSockets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                oSoc = socketServerArrayList.get(recyclerView.getChildAdapterPosition(v));
                //Toast.makeText(MainActivity.this, "Ip : "+oSoc.getIp_server(), Toast.LENGTH_SHORT).show();
                crearPopupMenu(v);
            }
        });
    }

    private void crearPopupMenu(View v)
    {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
        popupMenu.inflate(R.menu.menu_socket_serves);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.opc_conectar_socktes:
                        crearAlterEnvioMensaje();
                        break;
                    case R.id.opc_elimar_registro:

                        break;
                }
                return false;
            }
        });
    }

    private void crearAlterEnvioMensaje()
    {
        AlertDialog.Builder al =  new AlertDialog.Builder(MainActivity.this);
        al.setTitle("Envio Mensaje");
        final View viewMensaje = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_mensaje_envio_socket,null);
        al.setView(viewMensaje);
        al.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                alertDialog_Mensaje.dismiss();
            }
        });
        al.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                TextInputEditText editText = viewMensaje.findViewById(R.id.input_text_envio_mensaje_soket);
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .permitNetwork().build());
                cSocketCliente oSc = new cSocketCliente(oSoc.getIp_server(),oSoc.getPort(),MainActivity.this);
                oSc.ConnecSockets();
                if (oSc.getCs()!=null)
                {
                    oSc.setMensaje(editText.getText().toString());
                    oSc.EnviarMensaje();
                }else
                    {
                        Toast.makeText(MainActivity.this, "El ServerSocket esta Cerrado ....", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        alertDialog_Mensaje = al.create();
        alertDialog_Mensaje.show();
    }

    private void generarAlertDialog()
    {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.new_scoket_server,null);
        textInputEditText_ip = view.findViewById(R.id.text_input_ip_server);
        textInputEditText_name = view.findViewById(R.id.text_input_name_server);
        textInputEditText_port =  view.findViewById(R.id.text_input_port_server);
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setView(view);
        alert.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (!textInputEditText_ip.getText().toString().equals("")&& !textInputEditText_name.getText().toString().equals("")
                        && !textInputEditText_port.getText().toString().equals(""))
                {
                    if( OsqLiteServerCrud.registrarDatos(textInputEditText_name.getText().toString(),textInputEditText_ip.getText().toString(),
                            Integer.parseInt(textInputEditText_port.getText().toString())))
                    {
                        Toast.makeText(MainActivity.this, "Registro Guardado ....", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(MainActivity.this, "Error al Guardar ...", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Datos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (alertDialog!=null){alertDialog.dismiss();}
            }
        });
        alertDialog = alert.create();
        alertDialog.show();
    }
}