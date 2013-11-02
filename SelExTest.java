import java.util.ArrayList;
import java.util.HashMap;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
 
 
public class SelExTest {
 
    private static final String LOGIN_PAGE_URL = "http://adnan.appspot.com/testing-lab-login.html";
    private static final String CALCULATOR_URL = "http://adnan.appspot.com/testing-lab-calculator.html";
    private static final String PARAMETER_URL = "http://adnan.appspot.com/testing-lab-conversion?";
    private static ArrayList<String> users;
    private static HashMap<String,String> userPasswords;
 
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        createUserList();
        checkforValidUsers(driver);
        System.out.println("Wait 10 seconds to prevent frequent login error!");
        //After three invalid logins to wait for 10 seconds check
        Thread.sleep(10000);
        testforWhitespaceAndCase(driver);
        testTemperature(driver);
        testFahrenheitParameterCase(driver);
        testException(driver);
        driver.close();
    }

     private static void createUserList(){
        users = new ArrayList<String>();
        userPasswords = new HashMap<String,String>();
        users.add("andy");
        users.add("bob");
        users.add("charley");
        userPasswords.put("andy", "apple");
        userPasswords.put("bob", "bathtub");
        userPasswords.put("charley", "china");
    }


       private static void checkforValidUsers(WebDriver driver){
        System.out.println("Validating passwords of Andy , Bob and Charley");
        for(String user : users){
            driver = redirectToLoginPage(driver);
            String password = userPasswords.get(user);
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("Entered username -> " + user + " and password -> " + password);
            passwordField.submit();
            if(!driver.getTitle().contains("Online temperature conversion")){
                System.out.println("Invalid user-password combination --- check " + user + "," + password);
            }
        }
        System.out.println("Validated passwords of Andy, Bob and Charley");
    }

     private static void testforWhitespaceAndCase(WebDriver driver){
        System.out.println("To check if username is case insensitive and username-password combination can have whitespaces.");
        users.add("ANDy ");
        users.add("bOB");
        users.add("CHARley");
        userPasswords.put("ANDy ", "apple   ");
        userPasswords.put("bOB", "    bathtub");
        userPasswords.put("CHARley", "CHINA");
        for(int i=3;i<6;i++){
            String user = users.get(i);
            driver = redirectToLoginPage(driver);
            String password = userPasswords.get(user);
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("Entered username -> " + user + " and password -> " + password);
            passwordField.submit();
            if(!driver.getTitle().contains("Online temperature conversion")){
                if(user.equals("charLEy")){
                    System.out.println("Expected failure! Reason: Passwords are case sensitive");
                }else{
                    System.out.println("Invalid user-password combination " + user + "," + password);
                }
            }
        }
        System.out.println("Verified that username is case insensitive");
    }



      private static void testTemperature(WebDriver driver){
        TemperatureTest(driver,"3.14");
        TemperatureTest(driver,"212");
        TemperatureTest(driver,"42");
        TemperatureTest(driver,"-500");
        TemperatureTest(driver,"666");
        TemperatureTest(driver,"789");
    }
 
    private static void TemperatureTest(WebDriver driver,String input){
        System.out.println("Running the temperature test with input as " + input);
        double inputNumber = Double.parseDouble(input);
        driver = redirectToConverterPage(driver);
        WebElement userNameField = driver.findElement(By.name("farenheitTemperature"));
        userNameField.clear();
        userNameField.sendKeys(input);
        userNameField.submit();
        String[] lines = driver.getPageSource().split("\n");
        String outputLine = "";
        for(String line: lines){
            if (line.contains(input)){
                outputLine = line;
                boolean isDouble = (line.split("\\s+")[4]).split("\\.").length > 1;
                if(isDouble){
                    if((inputNumber < 0 || inputNumber > 212) && (line.split("\\s+")[4]).split("\\.")[1].length() != 1){
                        System.out.println("The given input is " + input + " output without single precision digit. " +
                                "The output is " + line.split("\\s+")[4]);
                        return;
                    }
                }else{
                    System.out.println("The given input is " + input + " precision digits. " +
                            "The output is " + line.split("\\s+")[4]);
                    return;
                }
            }
        }
        System.out.println("The output is " + outputLine.split("\\s+")[4]);
    }
 
    private static void testException(WebDriver driver){
        ExceptionTest(driver,"2112vivek");
        ExceptionTest(driver,"9.73E2");
    }
 
    private static void ExceptionTest(WebDriver driver, String input){
        System.out.println("Testing for Exception with input as " + input);
        driver = redirectToConverterPage(driver);
        WebElement userNameField = driver.findElement(By.name("farenheitTemperature"));
        userNameField.clear();
        userNameField.sendKeys(input);
        userNameField.submit();
        if(!driver.getPageSource().contains("NumberFormatException")){
            System.out.println("NumberFormatException as expected");
        }else{
            System.out.println("No NumberFormatException");
        }
    }

    private static void testFahrenheitParameterCase(WebDriver driver){
        FahrenheitParameterCaseTest(driver,"farenheitTemperature=-42");
        FahrenheitParameterCaseTest(driver,"FARENHEitTemperature=-42");
    }
     
    private static void FahrenheitParameterCaseTest(WebDriver driver,String input){
        System.out.println("Testing for case insensitivity of parameter input " + input);
        driver.get(PARAMETER_URL + input);
        if(!driver.getPageSource().contains(input.split("=")[1])){
            System.out.println("Parameter check test failed as they are case sensitive!");
        }
    }
     
    private static WebDriver redirectToLoginPage(WebDriver driver){
        System.out.println("Redirecting to the login page.");
        driver.get(LOGIN_PAGE_URL);
        return driver;
    }
 
    private static WebDriver redirectToConverterPage(WebDriver driver){
        System.out.println("Redirecting to the temperature converter page.");
        driver.get(CALCULATOR_URL);
        return driver;
    }
 
   
 
 
}
