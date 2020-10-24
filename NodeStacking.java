package lab6;

public class NodeStacking {
    // use this weight for calculating
    protected static double weight = 128.0;

    /**
     * Implement a recursive method that takes row index and column index of a node.
     * calculate the weight it supports.
     */
    public static double weightSupporting(int row, int col) {
        // Filling your code here
    	if (row < 0 || col < 0 || col > row) {
    		return -1;
    	} else if (row == 0 && col == 0) {
    		return weight;
    	} else if (col == 0) {
    		return 0.5 * weightSupporting(row - 1, 0);
    	} else if (col == row) {
    		return 0.5 * weightSupporting(row - 1, col - 1);
    	} else {
    		return 0.5 * (weightSupporting(row - 1, col - 1) + weightSupporting(row - 1, col));
    	}
        // End of your code
    }
    
    public static double weightSupporting(double topWeight, int row, int col) {
    	weight = topWeight;
    	
    	return weightSupporting(row, col);
    }
}
