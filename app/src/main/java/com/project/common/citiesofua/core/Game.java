package com.project.common.citiesofua.core;

public class Game {
    public final static String ID = "Game_ID";
    private static volatile Game instance;
    private int id;

    public static Game getInstance() {
        Game localInstance = instance;
        if (localInstance == null) {
            synchronized (Game.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Game();
                }
            }
        }
        return localInstance;
    }

    private Game() {
        this.id = -1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void erase() {
        id = -1;
        instance = null;
    }

    @Override
    public String toString() {
        return String.valueOf(ID + ":" + id);
    }
}

