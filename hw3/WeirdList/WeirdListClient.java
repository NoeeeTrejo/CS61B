import antlr.ASTFactory;

/** Functions to increment and sum the elements of a WeirdList. */
class WeirdListClient implements IntUnaryFunction {

    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n){
        AddFunc func = new AddFunc(n);
        return L.map(func);
    }

    /** Return the sum of all the elements in L. */
    static int sum(WeirdList L) {
        AFunction summer = new AFunction();
        L.map(summer);
        return summer.getCounter();
    }

    @Override
    public int apply(int x) {
        return 0;
    }
    public static class AddFunc implements IntUnaryFunction{
        private int num;

        public AddFunc(int n) {
            this.num = n;
        }

        @Override
        public int apply(int x) {
            return this.num + x;
        }

    }

    private static class AFunction implements IntUnaryFunction{
        private int counter = 0;

        @Override
        public int apply(int x) {
            return this.counter += x;
        }

        public int getCounter(){
            return this.counter;
        }
    }


    /* IMPORTANT: YOU ARE NOT ALLOWED TO USE RECURSION IN ADD AND SUM
     *
     * As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     *
     * HINT: Try checking out the IntUnaryFunction interface.
     *       Can we use it somehow?
     */
}
