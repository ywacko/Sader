package test;

public class Test {

    public static void print_1() {
        System.out.println("Print");
    }

    @Staticmethod
    public void print_2(int i) {
        System.out.println(i);
    }

    public static void main(String [] args) {
        Test.print_1();
        Test t = new Test();
        t.print_2(1);
    }
}
