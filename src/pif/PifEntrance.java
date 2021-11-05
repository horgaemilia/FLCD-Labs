package pif;

import hashTable.PositionTuple;

public class PifEntrance {
    private String token;
    private PositionTuple position;

    public PifEntrance(String token, PositionTuple position)
    {
        this.position = position;
        this.token = token;
    }

    @Override
    public String toString() {
        return token + " " + position.toString();
    }
}
