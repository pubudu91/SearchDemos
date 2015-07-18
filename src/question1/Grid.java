/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question1;

/**
 *
 * @author Pubudu
 */
public class Grid {
    public static final int SIZE = 5;
    public static final int SCALE = 1;
    private boolean grid[][];
    
    public Grid() {
        grid = new boolean[5][5]; // size * (size - i) - (size-1-j)
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                System.out.printf("%2d ",SIZE * (SIZE - i) - (SIZE - 1 - j));
//            }
//            System.out.println();
//        }
    }
    
    public int getHeuristicValue(Coordinate current, Coordinate goal) {
        /* If either of the 2 coordinates are out of range of the grid, return -1 */
        if(!(isWithinRange(current) && isWithinRange(goal)))
            return -1;
        
        int drow = Math.abs(goal.row - current.row);
        int dcol = Math.abs(goal.col - current.col);
        
        return SCALE * (drow + dcol);
    }
    
    public void visitNode(Coordinate node) {
        if(isWithinRange(node))
            grid[node.row][node.col] = true;
        else
            System.out.println("ERROR: Node not within the range of the grid!");
    }
    
    public void unvisitNode(Coordinate node) {
        if(isWithinRange(node))
            grid[node.row][node.col] = false;
        else
            System.out.println("ERROR: Node not within the range of the grid!");
    }
    
    public boolean wasVisited(Coordinate node) {
        return grid[node.row][node.col];
    }
    
    public boolean isWithinRange(Coordinate pos) {
        return (pos.row < SIZE) && (pos.row >= 0) && (pos.col >= 0) && (pos.col < SIZE);
    }
    
    public int getVertexNo(Coordinate node) {
        return SIZE * (SIZE - node.row) - (SIZE - 1 - node.col);
    }
}
