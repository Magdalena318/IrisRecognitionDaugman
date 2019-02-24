/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IrisRecognitionDaugman;

/**
 *
 * @author root
 */
public class Circle {
    
    //center of circle
    int x; 
    int y;    
    int r;
    
    Circle(){
        this.x = 0;
        this.y = 0;
        this.r = 0;

    }
    
    Circle(int x, int y, int r){
        this.x = x;
        this.y = y;
        this.r = r;

    }
    
    
    @Override
    public String toString(){
        return /*"Iris coordinates: ("*/ + this.x + ", " + this.y + "), radius: " + this.r;
    }
}
