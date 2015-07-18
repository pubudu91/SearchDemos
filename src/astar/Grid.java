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
public class Grid {
    public static final int SIZE = 5;
    public static final int SCALE = 1;
    private final Coordinate grid[][];
    
    public Grid() {
        grid = new Coordinate[5][5]; // size * (size - i) - (size-1-j)
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                System.out.printf("%2d ",SIZE * (SIZE - i) - (SIZE - 1 - j));
//            }
//            System.out.println();
//        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Coordinate(i, j);
            }
        }
    }
    
    public int getHeuristicValue(Coordinate current, Coordinate goal) {
        /* If either of the 2 coordinates are out of range of the grid, return -1 */
        if(!(isWithinRange(current) && isWithinRange(goal)))
            return -1;

        int drow = Math.abs(goal.row - current.row);
        int dcol = Math.abs(goal.col - current.col);
        
        return SCALE * (drow + dcol);
    }
    
    public boolean isWithinRange(Coordinate pos) {
        return (pos.row < SIZE) && (pos.row >= 0) && (pos.col >= 0) && (pos.col < SIZE);
    }
    
    public boolean isWithinRange(int row, int col) {
        return (row < SIZE) && (row >= 0) && (col >= 0) && (col < SIZE);
    }
    
    public int getVertexNo(Coordinate node) {
        return SIZE * (SIZE - node.row) - (SIZE - 1 - node.col);
    }
    
    public Coordinate getCoordinate(int vertexNo) {
        int row, col;
        
        if(vertexNo % SIZE != 0){
            row = SIZE - 1 - vertexNo / SIZE ;
            col = vertexNo % SIZE - 1;
        }
        else {
            row = SIZE - vertexNo / SIZE;
            col = SIZE - 1;
        }
        
        return isWithinRange(row, col) ? grid[row][col] : null;
    }
    
    public Coordinate getCoordinate(int row, int col) {
        return isWithinRange(row, col) ? grid[row][col] : null;
    }
}
