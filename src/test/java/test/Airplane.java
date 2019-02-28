package test;

import test.IFly;

public class Airplane implements IFly {
    @Override
    public void fly() {
        System.out.println("I am in the sky");
    }
}
