package com.virtualcode7ecuador.scokets.Poo;

public class cSocketServer
{
    private String name;
    private int port;
    private String ip_server;
    public cSocketServer()
    {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp_server() {
        return ip_server;
    }

    public void setIp_server(String ip_server) {
        this.ip_server = ip_server;
    }
}
