public class Singleton {
    // Private static instance of the class
    private static Singleton instance = new Singleton();

    // Private constructor to prevent instantiation from outside
    private Singleton() {
        System.out.println("Singleton instance created");
    }

    // Public static method to provide access to the instance
    public static Singleton getInstance() {
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }

    public static void main(String[] args) {
        Singleton obj1 = Singleton.getInstance();
        Singleton obj2 = Singleton.getInstance();

        obj1.showMessage();

        // Check if both references point to the same object
        System.out.println("Same instance? " + (obj1 == obj2)); // true
    }
}
