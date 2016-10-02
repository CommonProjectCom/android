package com.project.common.citiesofua.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class Connection {

    public static final String NEW_GAME_URL = "http://mytomcatapp-dergachovda.rhcloud.com/NewGame";
    private URLConnection connection;

    public Connection(String url) {
        try {
            connection = new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parameter execute(String name) {
        request(name);
        return new Parameter(response());
    }

    private void request(String name) {
        try {
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(name);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String response() {
        String returnString = "Null";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            returnString = in.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return returnString;
        }
    }
}