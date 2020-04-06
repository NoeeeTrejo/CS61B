public class hw0 {
    public static void main (String[] args) {

    }
    public static int max (int[] numList){
        int maxNum = numList[0];
        boolean foundMax = false;
        while (! foundMax){
            for (int n: numList)
                if (maxNum < n) {
                    maxNum = n;
                }
            foundMax = true;
        }
        return maxNum;
    }
    public static boolean threeSum(int[] numList)
        if (numList.length < 3) {
            return false;
        }
        for (int f: numList)
            for (int g: numList)
                for (int h: numList)
                    if ((f + g + h) == 0) {
                        return true;}
        return false;
    }
    public static boolean threeSumDistinct(int[] numList){
        if (numList.length < 3) {
            return false;
        }
        for (int i = 0; i < numList.length; i++) {
            for (int j = 0; j < numList.length; j++){
                for (int z = 0; z < numList.length; z++)
                {
                    if ((numList[i] + numList[j] + numList[z] == 0) && (i != j && j != z && i != z)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}