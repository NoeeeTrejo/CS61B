package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int[] arr = new int[A.length + B.length] ;
        for(int i = 0; i < A.length; i++) {
                arr[i] = A[i];
        }
        for (int i = 0; i < B.length; i++){
            arr[i + A.length] = B[i];
        }
        return arr;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        int[] returnArr = new int[A.length - len];
        int index = 0;
        for(int i = 0; i < A.length; i++){
            if ( i < start || i >= start + len){
                returnArr[index] = A[i];
                index++;
            }
        }
        return returnArr;
    }

    /* C3. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        if (A.length == 0){
            return new int[][]{{}};
        }
        if (A.length == 1){
            return new int[][]{{A[0]}};
        }
        int count = 1;
        for(int i = 1; i < A.length; i++){
            if (A[i - 1] >= A[i]){
                count++;
            }
        }
        int[][] returnArr = new int[count][];
        int rAIndex = 0;
        int startI = 0;
        for(int i = 1; i < A.length; i++){
           if (A[i - 1] >= A[i]){
                returnArr[rAIndex] = new int[i - startI];
                for (int j = startI; j < i; j++){
                    returnArr[rAIndex][j - startI] = A[j];
                }
               startI = i;
                rAIndex++;
           }

           if (rAIndex == count - 1){
               returnArr[rAIndex] = new int[A.length - startI];
               for (; i < A.length; i++){
                   returnArr[rAIndex][i - startI] = A[i];
               }
            }
        }
        return returnArr;
    }


}
