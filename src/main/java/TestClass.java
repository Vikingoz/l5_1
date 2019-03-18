import annotation.After;
import annotation.Before;
import annotation.Test;

public class TestClass {

    Integer intVar;
    String  strVar;

    public TestClass() {
        intVar = 0;
        strVar = "zero";
    }

    @Before
    public void TestBefore() {
        System.out.println("[test] before");
        this.intVar = 0;
        this.strVar = "zero";
    }

    @Test
    public void TestOne() {
        System.out.println("[test] one: +1");
        this.intVar += 1;
        this.strVar = "one";
    }

    @Test
    public void TestTwo() {
        System.out.println("[test] two +2");
        this.intVar += 2;
        this.strVar = "two";
    }

    @Test
    public void TestThree() {
        System.out.println("[test] three +3");
        this.intVar += 3;
        this.strVar = "three";
    }

    @After
    public void TestAfter() {
        System.out.println("[test] after: result intVar=" + this.intVar + ", strVar=" + this.strVar);

    }

}
