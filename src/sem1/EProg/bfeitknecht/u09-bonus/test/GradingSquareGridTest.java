import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GradingSquareGridTest {

    @Test
    public void testAnalyzeSquareGrid01() {
        // (0, 0)

        Node n00 = new Node();

        assertEquals(1, SquareGrid.analyzeSquareGrid(n00));
    }

    @Test
    public void testAnalyzeSquareGrid02() {
        // (0, 0) - (0, 1)
        //   |        |    
        // (1, 0) <- -

        Node n00 = new Node();
        Node n01 = new Node();
        Node n10 = new Node();

        n00.setRight(n01);
        n00.setDown(n10);

        n01.setDown(n10);

        assertEquals(1, SquareGrid.analyzeSquareGrid(n00));
    }

    @Test
    public void testAnalyzeSquareGrid03() {
        // (0, 0) - (0, 1) - (0, 2)
        //   |        |        |  
        // (1, 0) - (1, 1) - (1, 2)
        //   |        |        |
        // (2, 0) - (2, 1) - (2, 2)

        Node[][] grid = generateLinkedGrid(3);

        assertEquals(3, SquareGrid.analyzeSquareGrid(grid[0][0]));
        assertEquals(2, SquareGrid.analyzeSquareGrid(grid[1][1]));
    }

    @Test
    public void testAnalyzeSquareGrid04() {
        // (0, 0) - (0, 1) - (0, 2) - (0, 3) - (0, 4)
        //   |        |        |        |        |
        // (1, 0) - (1, 1) - (1, 2) - (1, 3) - (1, 4)
        //   |        |        |        |        |
        // (2, 0) - (2, 1) - (2, 2) - (2, 3) - (2, 4)
        //   |        |        |        |        |
        // (3, 0) - (3, 1) - (3, 2) - (3, 3) - (3, 4)
        //   |        |        |        |        |
        // (4, 0) - (4, 1) - (4, 2) - (4, 3) - (4, 4)

        Node[][] grid = generateLinkedGrid(5);

        assertEquals(5, SquareGrid.analyzeSquareGrid(grid[0][0]));
        assertEquals(4, SquareGrid.analyzeSquareGrid(grid[1][0]));
    }

    @Test
    public void testAnalyzeSquareGrid05() {
        // (0, 0) - (0, 1) - (0, 2)
        //   |        |        |  
        // (1, 0) - (1, 1) - (1, 2)
        //   |        |        |
        // (2, 0) - (2, 1)<- (2, 2)

        // Note that instead of an edge from (2, 1) to (2, 2), there is one from (2, 2) to (2, 1)

        Node[][] grid = generateLinkedGrid(3);

        grid[2][2].setRight(grid[2][1]);
        grid[2][1].setRight(null);

        assertEquals(2, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid06() {
        // (0, 0) - (0, 1) - (0, 2)
        //   |        |        |  
        // (1, 0) - (1, 1) - (1, 2)
        //   |                 |
        // (2, 0) - (2, 1) - (2, 2)

        Node[][] grid = generateLinkedGrid(3);
        grid[1][1].setDown(null);
        assertEquals(2, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid07() {
        // (0, 0) - (0, 1) - (0, 2)
        //   |        |         
        // (1, 0) - (1, 1)
        //   |        |        
        // (2, 0) - (2, 1)

        Node[][] grid = generateLinkedGrid(2);
        Node n20 = new Node();
        Node n21 = new Node();
        Node n02 = new Node();

        grid[0][1].setRight(n02);
        grid[1][0].setDown(n20);
        grid[1][1].setDown(n21);
        n20.setRight(n21);

        assertEquals(2, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid08() {
        // (0, 0) - (0, 1) - (0, 2) - (0, 3) - (0, 4)
        //   |        |        |        |        |
        // (1, 0) - (1, 1) - (1, 2) - (1, 3) - (1, 4)
        //   |        |                 |        |
        // (2, 0) - (2, 1)   (2, 2) - (2, 3) - (2, 4)
        //   |        |        |        |        |
        // (3, 0) - (3, 1) - (3, 2) - (3, 3) - (3, 4)
        //   |        |        |        |        |
        // (4, 0) - (4, 1) - (4, 2) - (4, 3) - (4, 4)

        // Note that the edges from (1, 2) to (2, 2) and from (2, 1) to (2, 2) are missing. 

        Node[][] grid = generateLinkedGrid(5);

        grid[1][2].setDown(null);
        grid[2][1].setRight(null);

        assertEquals(2, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid09() {
        // (0, 0) - (0, 1)
        //   |        |         
        //   |      (1, 1)
        //   |        |        
        // (2, 0) - (2, 1)

        Node n00 = new Node();
        Node n01 = new Node();
        Node n11 = new Node();
        Node n20 = new Node();
        Node n21 = new Node();

        n00.setRight(n01);
        n00.setDown(n20);
        n01.setDown(n11);
        n11.setDown(n21);
        n20.setRight(n21);

        assertEquals(1, SquareGrid.analyzeSquareGrid(n00));
    }

    @Test
    public void testAnalyzeSquareGrid10() {
        // (0, 0) - (0, 1) - (0, 2)
        //   |        |        |  
        // (1, 0) - (1, 1) - (1, 2)
        //   |        |        |
        //   |        |      (Node)
        //   |        |
        // (2, 0) - (2, 1) - (Node)

        Node[][] grid = generateLinkedGrid(3);
        Node extra = new Node();

        grid[1][2].setDown(extra);

        assertEquals(2, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid11() {
        // (0, 0) - (0, 1)
        //   |        |     
        // (1, 0) - (1, 1) - (1, 2) - (1, 3)
        //            |        |        |
        //          (2, 1) - (2, 2) - (2, 3)
        //            |        |        |    
        //          (3, 1) - (3, 2) - (3, 3)

        Node[][] grid = generateLinkedGrid(3);
        Node n00 = new Node();
        Node n01 = new Node();
        Node n10 = new Node();

        n00.setRight(n01);
        n00.setDown(n10);
        n01.setDown(grid[0][0]);
        n10.setRight(grid[0][0]);

        assertEquals(2, SquareGrid.analyzeSquareGrid(n00));
        assertEquals(3, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid12() {
        // (0, 0) - (0, 1)
        //   |              
        // (1, 0) - (1, 1)
        //   |        |        
        // (2, 0) - (2, 1)

        // Note that the edge from (0, 1) to (1, 1) is missing

        Node[][] grid = generateLinkedGrid(2);
        Node n00 = new Node();
        Node n01 = new Node();

        n00.setRight(n01);
        n00.setDown(grid[0][0]);

        assertEquals(1, SquareGrid.analyzeSquareGrid(n00));
        assertEquals(2, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid13() {
        // (0, 0) - (0, 1) - (0, 2)
        //   |        |        |  
        //   |      (1, 1) - (1, 2)
        //   |                 |
        // (1, 0) - (Node)     |
        //   |        |        |
        // (2, 0) - (2, 1) - (2, 2)

        // Note that there are also edges from (Node) to (1, 2) and from (1, 1) to (2, 1)

        Node[][] grid = generateLinkedGrid(3);
        Node k = new Node();

        grid[1][0].setRight(k);
        k.setRight(grid[1][2]);
        k.setDown(grid[2][1]);

        assertEquals(1, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid14() {
        // (0, 0) - (0, 1) - (0, 2) - (0, 3) - (0, 4)
        //   |        ^        |        ^        |
        // (1, 0) - (1, 1) - (1, 2) - (1, 3) - (1, 4)
        //   |        ^                 ^        |
        // (2, 0) - (2, 1)   (2, 2) - (2, 3) - (2, 4)
        //   |        ^        |        ^        |
        // (3, 0) - (3, 1) - (3, 2) - (3, 3) - (3, 4)
        //   |        ^        |        ^        |
        // (4, 0) - (4, 1) - (4, 2) - (4, 3) - (4, 4)

        Node[][] grid = generateLinkedGrid(5);

        grid[0][1].setDown(null);
        grid[1][1].setDown(grid[0][1]);
        grid[2][1].setDown(grid[1][1]);
        grid[3][1].setDown(grid[2][1]);
        grid[4][1].setDown(grid[3][1]);

        grid[0][3].setDown(null);
        grid[1][3].setDown(grid[0][3]);
        grid[2][3].setDown(grid[1][3]);
        grid[3][3].setDown(grid[2][3]);
        grid[4][3].setDown(grid[3][3]);

        assertEquals(1, SquareGrid.analyzeSquareGrid(grid[0][0]));
    }

    @Test
    public void testAnalyzeSquareGrid15() {
        // (0, 0) -  (0, 1)
        //   |         |      
        // (1, 0) <- (1, 1)

        Node n00 = new Node();
        Node n01 = new Node();
        Node n10 = new Node();
        Node n11 = new Node();

        n00.setRight(n01);
        n01.setDown(n11);
        n11.setRight(n10);
        n10.setDown(n00);

        assertEquals(1, SquareGrid.analyzeSquareGrid(n00));
    }

    @Test
    public void testAnalyzeSquareGrid16() {
        // (0, 0) = (0, 1) = (0, 2)

        Node n00 = new Node();
        Node n01 = new Node();
        Node n02 = new Node();

        n00.setRight(n01);
        n00.setDown(n01);

        n01.setRight(n02);
        n01.setDown(n02);

        assertEquals(1, SquareGrid.analyzeSquareGrid(n00));
    }

    @Test
    public void testAnalyzeSquareGrid17() {
        // (0, 0) - (0, 1) - (0, 2)
        //   |        |        |  
        // (1, 0) - (1, 1) - (1, 2)
        //   |        |        |
        // (2, 0) - (2, 1) - (2, 2)

        // Note that there are edges from (j, 2) to (j, 0) and from (2, j) to (0, j) for 0 <= j <= 2

        Node[][] grid = generateLinkedGrid(3);

        grid[0][2].setRight(grid[0][0]);
        grid[1][2].setRight(grid[1][0]);
        grid[2][2].setRight(grid[2][0]);

        grid[2][0].setDown(grid[0][0]);
        grid[2][1].setDown(grid[0][1]);
        grid[2][2].setDown(grid[0][2]);

        assertEquals(3, SquareGrid.analyzeSquareGrid(grid[0][0]));
        assertEquals(3, SquareGrid.analyzeSquareGrid(grid[1][1]));
        assertEquals(3, SquareGrid.analyzeSquareGrid(grid[2][2]));
    }

    @Test
    public void testAnalyzeSquareGrid18() {
        // (0, 0)

        // Note that (0, 0) contains two edges to itself

        Node n00 = new Node();

        n00.setRight(n00);
        n00.setDown(n00);

        assertEquals(1, SquareGrid.analyzeSquareGrid(n00));
    }

    @Test
    public void testAnalyzeSquareGrid19() {
        // (0, 0) = (0, 1)

        // Note that there are two edges from (0, 0) to (0, 1)

        Node n00 = new Node();
        Node n01 = new Node();

        n00.setRight(n01);
        n00.setDown(n01);

        assertEquals(1, SquareGrid.analyzeSquareGrid(n00));
    }

    private static Node[][] generateLinkedGrid(int size) {
        Node[][] grid = new Node[size][size];
        for (int i = size - 1; i >= 0; i--) {
            for (int j = size - 1; j >= 0; j--) {

                Node newNode = new Node();
                grid[i][j] = newNode;

                if (i != size - 1) {
                    newNode.setDown(grid[i + 1][j]);
                }

                if (j != size - 1) {
                    newNode.setRight(grid[i][j + 1]);
                }
            }
        }

        return grid;
    }
}