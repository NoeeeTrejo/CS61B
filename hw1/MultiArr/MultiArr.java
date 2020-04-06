/** Multidimensional array 
 *  @author Zoe Plaxco
 */

public class MultiArr {

    /**
    {{“hello”,"you",”world”} ,{“how”,”are”,”you”}} prints:
    Rows: 2
    Columns: 3
    
    {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
    Rows: 4
    Columns: 4
    */
    public static void printRowAndCol(int[][] arr) {
        System.out.println("Rows: " + arr.length);
        System.out.println("Columns: " + getColumns(arr));
    } 

    public static int getColumns(int [][] arr) {
        int columns = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length > columns) {
                columns = arr[i].length;
            }
        }
            return columns;
    }
    /**
    @param arr: 2d array
    @return maximal value present anywhere in the 2d array
    */
    public static int maxValue(int[][] arr) {
        int maxVal = 0;
        for (int[] list: arr)
            for (int i = 0; i < list.length; i++)
                if (list[i] > maxVal) {
                    maxVal = list[i];
                }
        return maxVal;
    }

    /**Return an array where each element is the sum of the
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        int[] sumArr = new int[arr.length];
        int i = 0;
        for (int[] list: arr){
            for (int num: list){
                sumArr[i] += num;
            }
            i++;
        }
        return sumArr;
    }
}