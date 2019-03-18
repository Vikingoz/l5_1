import listeners.MyAnnotationListener;


public class Test {



    public static void main(String[] args){
        try {
            MyAnnotationListener.run("TestClass");
            System.out.println("************** repeat again ***************");
            MyAnnotationListener.run(TestClass.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

