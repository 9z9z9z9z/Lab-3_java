package model;


public class Linen 
{
    protected int tWashing;
    protected int tIroning;
    Linen()
    {
        tIroning = 40;
        tWashing = 60;
    }
    
    Linen(int tWashing, int tIroning){
        this.tWashing = tWashing;
        this.tIroning = tIroning;
    }

    public int gettWashing() {return this.tWashing; }
    public int gettIroning() { return this.tIroning; }
}
