import runners.TestRunner;


public class Test {



    public static void main(String[] args){
        try {
            TestRunner.run("TestClass");
            System.out.println("************** repeat again ***************");
            TestRunner.run(TestClass.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

