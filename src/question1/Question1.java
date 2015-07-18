/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question1;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author Pubudu
 */
public class Question1 {
    static Grid grid;
    static Coordinate targetNode;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        grid = new Grid();
//        targetNode = new Coordinate(0, Grid.SIZE-1);
        targetNode = new Coordinate(25);
//        System.out.print("BFS: ");
//        bfs(new Coordinate(5));
//        System.out.println();
//        
//        grid = new Grid();
//        System.out.print("DFS: ");
//        dfs(new Coordinate(1));
//        System.out.println();
        AStar(new Coordinate(2));
    }
    
    public static void AStar(Coordinate start) {
        PriorityQueue<Coordinate> open = new PriorityQueue<Coordinate>();
        PriorityQueue<Coordinate> closed = new PriorityQueue<Coordinate>();
        start.computeGval(0, 0);
        start.computeFval(grid.getHeuristicValue(start, targetNode));
        open.offer(start);
        Coordinate current;
        
        while(!open.isEmpty()) {
            current = open.poll();
            closed.offer(current);
            Coordinate[] neighbours = getNeighbours(current);
            System.out.print(current);
            
            for (int i = 0; i < neighbours.length; i++) {
                int prevfval = neighbours[i].getFval();
                
                if(!(open.contains(neighbours[i]) || closed.contains(neighbours[i]))) {
                    neighbours[i].computeGval(current.getGval(), 1); // neighbours are always just one unit of distance away 
                    neighbours[i].computeFval(grid.getHeuristicValue(neighbours[i], targetNode));
                    open.offer(neighbours[i]);
                }
                
                if(open.contains(neighbours[i]) || closed.contains(neighbours[i])) {
                    neighbours[i].setGval(min(neighbours[i].getGval(), neighbours[i].computeGval(current.getGval(), 1)));
                    neighbours[i].computeFval(grid.getHeuristicValue(neighbours[i], targetNode));
                }
                
                if(neighbours[i].getFval() < prevfval && closed.contains(neighbours[i])) {
                    open.offer(neighbours[i]);
                    closed.remove(neighbours[i]);
                }
                
                neighbours[i].parent = current;
                System.out.print(", "+neighbours[i]);
            }
            System.out.println();
        }
        Coordinate temp = targetNode;
//        while(temp != null) {
//            System.out.println(temp);
//            temp = temp.parent;
//        }
    }
    
    public static int min(int n, int m) {
        return n > m ? m : n;
    }
    
    public static void dfs(Coordinate start) {
        Stack<Coordinate> stack = new Stack<Coordinate>();
        stack.push(start);
        grid.visitNode(start);
        
        while(!stack.isEmpty()){
            Coordinate temp = stack.peek();
            Coordinate unvisitedNode = getUnvisitedAdjacentNode(temp);
            
            if(unvisitedNode == null) {
                stack.pop();
                grid.visitNode(temp);
            }
            else {
                stack.push(unvisitedNode);
                grid.visitNode(unvisitedNode);
                if(unvisitedNode.equals(targetNode))
                    break;
            }
        }
        
        while(!stack.isEmpty())
            System.out.print(stack.pop() + " ");
        
        System.out.println();
    }
    
    public static void dfs2(Coordinate start) {
        if(targetNode.equals(start))
            System.out.println(start.vertexNo);
        else {
            grid.visitNode(start);
            Coordinate temp = getUnvisitedAdjacentNode(start);
            dfs2(temp);
            grid.unvisitNode(start);
            System.out.println(temp.vertexNo);
        }
    }
    
    public static void bfs(Coordinate start) {
        CQueue<Coordinate> queue = new CQueue<Coordinate>();
        int[] discovered = new int[Grid.SIZE * Grid.SIZE];
        queue.offer(start);
        int begin = grid.getVertexNo(start) - 1;
        int target = targetNode.vertexNo - 1;
        
        l1:while(!queue.isEmpty()) {
            Coordinate temp = queue.poll();
            grid.visitNode(temp);
            Coordinate[] unvisitedNodes = getUnvisitedAdjacentNodes(temp);
            
            if(unvisitedNodes == null)
                continue l1;

            for (int i = 0; i < unvisitedNodes.length; i++) {
                discovered[unvisitedNodes[i].vertexNo - 1] = temp.vertexNo - 1;
                queue.add(unvisitedNodes[i]);
                grid.visitNode(unvisitedNodes[i]);
                if(unvisitedNodes[i].equals(targetNode)){
//                    target = grid.getVertexNo(unvisitedNodes[i]) - 1;
                    break l1;
                }
            }
        }
        int x = target;

        while(x != begin) {
            System.out.print((x + 1) + " ");
            x = discovered[x];
        }
        
        System.out.println(x+1);
    }

    private static Coordinate getUnvisitedAdjacentNode(Coordinate origin) {
        Coordinate temp = null;
        
        if(grid.isWithinRange((temp = new Coordinate(origin.row - 1, origin.col))) && !grid.wasVisited(temp))
            ;
        else if(grid.isWithinRange((temp = new Coordinate(origin.row, origin.col + 1))) && !grid.wasVisited(temp))
            ;
        else if(grid.isWithinRange((temp = new Coordinate(origin.row + 1, origin.col))) && !grid.wasVisited(temp))
            ;
        else if(grid.isWithinRange((temp = new Coordinate(origin.row, origin.col - 1))) && !grid.wasVisited(temp))
            ;
        else 
            return null;
        
        return temp;
    }
    
    private static Coordinate[] getUnvisitedAdjacentNodes(Coordinate origin) {
        ArrayList<Coordinate> adj = new ArrayList<Coordinate>();
        Coordinate temp;
        
        if(grid.isWithinRange((temp = new Coordinate(origin.row - 1, origin.col))) && !grid.wasVisited(temp))
            adj.add(temp);
        if(grid.isWithinRange((temp = new Coordinate(origin.row, origin.col + 1))) && !grid.wasVisited(temp))
            adj.add(temp);
        if(grid.isWithinRange((temp = new Coordinate(origin.row + 1, origin.col))) && !grid.wasVisited(temp))
            adj.add(temp);
        if(grid.isWithinRange((temp = new Coordinate(origin.row, origin.col - 1))) && !grid.wasVisited(temp))
            adj.add(temp);
        
        return adj.size() > 0 ? adj.toArray(new Coordinate[1]) : null;
    }
    
    private static Coordinate[] getNeighbours(Coordinate origin) {
        ArrayList<Coordinate> adj = new ArrayList<Coordinate>();
        Coordinate temp;
        
        if(grid.isWithinRange((temp = new Coordinate(origin.row - 1, origin.col)))) {
            adj.add(temp);
//            temp.parent = origin;
        }
        if(grid.isWithinRange((temp = new Coordinate(origin.row, origin.col + 1)))) {
            adj.add(temp);
//            temp.parent = origin;
        }
        if(grid.isWithinRange((temp = new Coordinate(origin.row + 1, origin.col)))) {
            adj.add(temp);
//            temp.parent = origin;
        }
        if(grid.isWithinRange((temp = new Coordinate(origin.row, origin.col - 1)))) {
            adj.add(temp);
//            temp.parent = origin;
        }
        
        return adj.size() > 0 ? adj.toArray(new Coordinate[1]) : null;
    }
}
