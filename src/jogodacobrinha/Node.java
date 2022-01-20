/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogodacobrinha;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author FLAVIO
 */
public class Node {
    int x,y;
    public static final int blockSize = 20;
    Node(){}
    Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(x, y, 10, 10);
    }
    
}
