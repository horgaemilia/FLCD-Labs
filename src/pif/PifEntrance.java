package pif;

public class PifEntrance {
    private String token;
    private int position;

    public PifEntrance(String token,int position)
    {
        this.position = position;
        this.token = token;
    }

    @Override
    public String toString() {
        return token + ":" + position;
    }
}
