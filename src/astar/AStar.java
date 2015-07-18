/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author Pubudu
 */
public class AStar {
    static Grid grid;
    static Coordinate targetNode;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        grid = new Grid();

        targetNode = grid.getCoordinate(2);

        AStar(grid.getCoordinate(20));
    }
    
    public static void AStar(Coordinate start) {
        PriorityQueue<Coordinate> open = new PriorityQueue<Coordinate>();
        HashSet<Coordinate> closed = new HashSet<Coordinate>();
        start.computeGval(0, 0);
        start.computeFval(grid.getHeuristicValue(start, targetNode));
        open.offer(start);
        Coordinate current;
        
        while(!open.peek().equals(targetNode)) {
            current = open.poll();
            closed.add(current);
            Coordinate[] neighbours = getNeighbours(current);
            
            for (Coordinate neighbour : neighbours) {
                int prevfval = neighbour.getFval();
                if (!(open.contains(neighbour) || closed.contains(neighbour))) {
                    neighbour.computeGval(current.getGval(), 1); // neighbours are always just one unit of distance away
                    neighbour.computeFval(grid.getHeuristicValue(neighbour, targetNode));
                    open.offer(neighbour);
                }
                if (open.contains(neighbour) || closed.contains(neighbour)) {
                    neighbour.setGval(min(neighbour.getGval(), neighbour.computeGval(current.getGval(), 1)));
                    neighbour.computeFval(grid.getHeuristicValue(neighbour, targetNode));
                }
                if (neighbour.getFval() < prevfval && closed.contains(neighbour)) {
                    open.offer(neighbour);
                    closed.remove(neighbour);
                }
                if (neighbour.parent == null) {
                    neighbour.parent = current;
                }
            }
        }
        
        Coordinate temp = targetNode;
        while(!temp.equals(start)) {
            System.out.println(temp);
            temp = temp.parent;
        }
        System.out.println(start);
    }
    
    public static int min(int n, int m) {
        return n > m ? m : n;
    }
    
    private static Coordinate[] getNeighbours(Coordinate origin) {
        ArrayList<Coordinate> adj = new ArrayList<Coordinate>();
        Coordinate temp;
        
        if((temp = grid.getCoordinate(origin.row - 1, origin.col)) != null) 
            adj.add(temp);

        if((temp = grid.getCoordinate(origin.row, origin.col + 1)) != null) 
            adj.add(temp);

        if((temp = grid.getCoordinate(origin.row + 1, origin.col)) != null) 
            adj.add(temp);

        if((temp = grid.getCoordinate(origin.row, origin.col - 1)) != null) 
            adj.add(temp);
        
        return adj.size() > 0 ? adj.toArray(new Coordinate[1]) : null;
    }
}
