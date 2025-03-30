package com.memento.tech.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.memento.tech.deserializer.BoardSlotDeserializer;

@JsonDeserialize(using = BoardSlotDeserializer.class)
public class BoardSlot {

    private int x;

    private int y;

    public BoardSlot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
