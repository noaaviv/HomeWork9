import org.openqa.selenium.By;

public class WebCalculatorPage {
    public static void pressFive() {
        DriverSingleton.getDriverInstance().findElement(By.id("five")).click();
    }

    public static void pressPlus() {
        DriverSingleton.getDriverInstance().findElement(By.id("add")).click();
    }

    public static void pressEquals() {
        DriverSingleton.getDriverInstance().findElement(By.id("equal")).click();
    }

    public static String getResult() {
        return DriverSingleton.getDriverInstance().findElement(By.id("screen")).getText();
    }
}