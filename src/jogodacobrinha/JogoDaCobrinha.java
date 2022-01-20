/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogodacobrinha;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;


/**
 *
 * @author FLAVIO
 */
public class JogoDaCobrinha extends Canvas implements Runnable,KeyListener{
    
    public int tam = 20;    
    public Node[] nodeCobra = new Node[230400];
    public Node[] inicioCobra = new Node[tam];
    int rabo = 230380;
    public boolean left, right, up, down;
    public String ultTec = "";
    public int score = 0;
    public int macaX = 0, macaY = 0;
    public int spd = 2;
    public int frameSpd = 130;
    
    public JogoDaCobrinha(){
        this.setPreferredSize(new Dimension(480,480));
        
        for(int i = 0; i < nodeCobra.length; i++){
            nodeCobra[i] = new Node(0,0);
        }
        this.addKeyListener(this);
    }
    public void tick(){
        
        for(int i = nodeCobra.length - 1; i> 0;i--){
            nodeCobra[i].x = nodeCobra[i-1].x;
            nodeCobra[i].y = nodeCobra[i-1].y;
        }
        if(nodeCobra[0].x + 10 < 0 ){
            nodeCobra[0].x = 479;
        }
        else if(nodeCobra[0].x >= 480){
            nodeCobra[0].x = -11;
        }
        else if(nodeCobra[0].y + 10 < 0 ){
            nodeCobra[0].y = 479;
        }
        else if(nodeCobra[0].y >= 480){
            nodeCobra[0].y = -11;
        }
        if(right){
            nodeCobra[0].x+=spd;
            colision();
        }else if(left){
            nodeCobra[0].x-=spd;
            colision();
        }else if(up){
            nodeCobra[0].y-=spd;
            colision();
        }else if(down){
            nodeCobra[0].y+=spd;
            colision();
        }
        if(new Rectangle(nodeCobra[0].x,nodeCobra[0].y,10,10).intersects(new Rectangle(macaX,macaY,10,10))){
            macaX = new Random().nextInt(480-10);
            macaY = new Random().nextInt(480-10);
            rabo = rabo - 10;                                        
            score++;           
            frameSpd++;
            
            
        }      
    }
    public void colision(){
        for(int i = 0; i < nodeCobra.length - rabo;i++){
            if(i == 0) continue;            
            //Rectangle box1 = new Rectangle(nodeCobra[0].x,nodeCobra[0].y,10,10);
            //Rectangle box2 = new Rectangle(nodeCobra[i].x,nodeCobra[i].y,10,10);
            if(nodeCobra[0].x == nodeCobra[i].x && nodeCobra[0].y == nodeCobra[i].y){
                JOptionPane.showMessageDialog(null, "Pontos: "+score);
                System.exit(0);                                
            }           
        }
    }
    public void recomeco(){
        frameSpd = 200;
        score = 0;
        right = false;
        left = false;
        up = false;
        down = false;
        nodeCobra[0].x = 0;
        nodeCobra[0].y = 0;
        for(int i = 0; i < nodeCobra.length; i++){                   
                   nodeCobra[i] = new Node(0,0);
                }
    }
    
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, 480, 480);
        for(int i = 0;i < nodeCobra.length - rabo; i++){
            g.setColor(Color.blue);
            g.fillRect(nodeCobra[i].x, nodeCobra[i].y, 10, 10);
        }
        g.setColor(Color.red);
        g.fillRect(macaX, macaY, 10, 10);
        g.dispose();
        bs.show();
    }
    public static void main(String[] args) {
        JogoDaCobrinha jogo = new JogoDaCobrinha();
        JFrame frame = new JFrame("Game");
        frame.add(jogo);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(jogo).start();
    }

    @Override
    public void run() {
        while(true){   
           tick();
           render();           
           try {
              Thread.sleep(1000/frameSpd);
            } catch (InterruptedException ex) {
            Logger.getLogger(JogoDaCobrinha.class.getName()).log(Level.SEVERE, null, ex);
        }}
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_RIGHT){
           if(ultTec.equals("left")){
               right = false;               
           }else{
           right = true;
           left = false;
           up = false;
           down = false;
           ultTec = "right";
           }
       }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
           if(ultTec.equals("right")){
               left = false;               
           }else{
               right = false;
               left = true;
               up = false;
               down = false;
               ultTec = "left";
           }
       }else if(e.getKeyCode() == KeyEvent.VK_UP){
           if(ultTec.equals("down")){
               up = false;
           }else{
               right = false;
               left = false;
               up = true;
               down = false;
               ultTec = "up";
           }
       }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
           if(ultTec.equals("up")){
               down = false;
           }else{
               right = false;
               left = false;
               up = false;
               down = true;
               ultTec = "down";
           }
       }
    }
    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    
}
