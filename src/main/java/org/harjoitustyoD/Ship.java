package org.harjoitustyoD;

public class Ship {

    private int size;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int hp;
    private boolean destroyed;
    private int id;
    private boolean isHorizontal;

    public Ship(int size, int id){
        this.size = size;
        this.hp = size;
        this.destroyed = false;
        this.id = id;
        this.isHorizontal = true;
    }

    public int getId(){
        return id;
    }

    public void setSize(int size){
        this.size = size;
    }

    public int getSize(){
        return size;
    }

    public int getHp(){
        return hp;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public boolean getIsHorizontal(){
        return isHorizontal;
    }

    public void setIsHorizontal(boolean h){
        isHorizontal = h;
    }

    public boolean hit(){
        this.hp--;
        if(this.hp <= 0){
            this.destroyed = true;
        }
        return this.destroyed;
    }

}
