/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

/**
 *
 * @author Pubudu
 */
public class Coordinate implements Comparable<Coordinate>{
    public int vertexNo;
    public int row;
    public int col;
    private int gval; // g(m) = g(n) + C(n,m)
    public int fval; // f(m) = g(m) + h(m)
    public Coordinate parent;
    
    public Coordinate(int x, int y) {
        this.row = x;
        this.col = y;
        vertexNo = Grid.SIZE * (Grid.SIZE - x) - (Grid.SIZE - 1 - y);
//        System.out.println(x+" "+y);
    }
    
    public Coordinate(int vertexNo) {
        this.vertexNo = vertexNo;
        
        if(vertexNo % Grid.SIZE != 0){
            this.row = Grid.SIZE - 1 - vertexNo / Grid.SIZE ;
            this.col = this.vertexNo % Grid.SIZE - 1;
        }
        else {
            this.row = Grid.SIZE - vertexNo / Grid.SIZE;
            this.col = Grid.SIZE - 1;
        }
        System.out.println(this.vertexNo+" "+row+" "+col);
    }
    
    public int computeGval(int gn, int cnm) {
        gval = gn + cnm;
        return gval;
    }
    
    public int computeFval(int hm) {
        fval = gval + hm;
        return fval;
    }
    
    @Override
    public boolean equals(Object obj) {
        return this.row == ((Coordinate)obj).row && this.col == ((Coordinate)obj).col;
    }
    
    @Override
    public String toString() {
        return "" + (Grid.SIZE * (Grid.SIZE - row) - (Grid.SIZE - 1 - col));
    }

    @Override
    public int compareTo(Coordinate o) {
        return this.fval > o.fval ? 1 : -1;
    }
    
    public void setGval(int gval) {
        this.gval = gval;
    }
    
    public int getGval() {
        return gval;
    }

    public int getFval() {
        return fval;
    }
}
