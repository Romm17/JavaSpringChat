package all;

/**
 * @author Roman Usik
 */

class A {

    protected static int i = 10;

    protected static void f() {
        i = 10;
    }

    protected static void g() {
        System.out.println(i);
    }

}

class B extends A {

    public static void f() {
        i = 20;
    }
}

class C extends A {

    public static void f() {
        i = 30;
    }
}

public class Test {
    public static void main(String[] args) {
        B.f();
        C.g();
    }
}
