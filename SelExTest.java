import java.util.ArrayList;
import java.util.HashMap;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
 
 
public class SelExTest {
 
    private static final String LOGIN_PAGE_URL = "http://adnan.appspot.com/testing-lab-login.html";
    private static final String CONVERSION_URL = "http://adnan.appspot.com/testing-lab-calculator.html";
    private static final String PARAMETER_URL = "http://adnan.appspot.com/testing-lab-conversion?";
    private static ArrayList<String> users;
    private static HashMap<String,String> userPasswords;
 
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        //WebDriver driver = new HtmlUnitDriver();
        initializeUserList();
        validUsersTest(driver);
        System.out.println("Wait 10 seconds to prevent frequent login error!");
        Thread.sleep(10000); //wait time to prevent frequent login error
        caseSensitiveAndWhitespaceCheck(driver);
        runTemperatureTests(driver);
        runExceptionTests(driver);
        runParameterTests(driver);
        driver.close();
    }
     
    private static void runParameterTests(WebDriver driver){
        runParameterTest(driver,"farenheitTemperature=-234");
        runParameterTest(driver,"fArenhEitTemperature=-234");
    }
     
    private static void runParameterTest(WebDriver driver,String input){
        System.out.println("Running the parameter test with parameter as " + input);
        driver.get(PARAMETER_URL + input);
        if(!driver.getPageSource().contains(input.split("=")[1])){
            System.out.println("Parameter seems to be case sensitive!");
        }
    }
     
    private static void runExceptionTests(WebDriver driver){
        runExceptionTest(driver,"2324bnj");
        runExceptionTest(driver,"9.73E2");
    }
 
    private static void runExceptionTest(WebDriver driver, String input){
        System.out.println("Running the Exception test with input as " + input);
        driver = redirectToConverterPage(driver);
        WebElement userNameField = driver.findElement(By.name("farenheitTemperature"));
        userNameField.clear();
        userNameField.sendKeys(input);
        userNameField.submit();
        if(!driver.getPageSource().contains("NumberFormatException")){
            System.out.println("Didn't get a NumberFormatException as expected!");
        }else{
            System.out.println("Received a NumberFormatException as expected");
        }
    }
     
    private static void runTemperatureTests(WebDriver driver){
        runTemperatureTest(driver,"3.14");
        runTemperatureTest(driver,"212");
        runTemperatureTest(driver,"122");
        runTemperatureTest(driver,"-200");
        runTemperatureTest(driver,"999");
        runTemperatureTest(driver,"786");
    }
 
    private static void runTemperatureTest(WebDriver driver,String input){
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
                        System.out.println("The given input is " + input + " and it produced an output which does not have one precision digit. " +
                                "The output is " + line.split("\\s+")[4]);
                        return;
                    }
                }else{
                    System.out.println("The given input is " + input + " and it produced an output which does not have and precision digits. " +
                            "The output is " + line.split("\\s+")[4]);
                    return;
                }
            }
        }
        System.out.println("The output is " + outputLine.split("\\s+")[4]);
    }
 
    private static void caseSensitiveAndWhitespaceCheck(WebDriver driver){
        System.out.println("Running the test to check if username is case-insensitive and whitespace can be included in both username and password.");
        users.add("Andy");
        users.add("boB  ");
        users.add("charLEy");
        userPasswords.put("Andy", "apple   ");
        userPasswords.put("boB  ", "    bathtub");
        userPasswords.put("charLEy", "CHINA");
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
            System.out.println("Submitting the page with username -> " + user + " password -> " + password);
            passwordField.submit();
            if(!driver.getTitle().contains("Online temperature conversion")){
                if(user.equals("charLEy")){
                    System.out.println("Expected failure! Reason: Passwords are case sensitive");
                }else{
                    System.out.println("problem with user-pass pair " + user + "," + password);
                }
            }
        }
        System.out.println("Verified the username case-insensitivity and white space no error case.");
    }
 
    private static void validUsersTest(WebDriver driver){
        System.out.println("Running the valid users test to check that the 3 users andy,bob,charley have the right passwords.");
        for(String user : users){
            driver = redirectToLoginPage(driver);
            String password = userPasswords.get(user);
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("Submitting the page with username -> " + user + " password -> " + password);
            passwordField.submit();
            if(!driver.getTitle().contains("Online temperature conversion")){
                System.out.println("problem with user-pass pair " + user + "," + password);
            }
        }
        System.out.println("Successfully verified that the 3 users have proper passwords.");
    }
 
    private static WebDriver redirectToLoginPage(WebDriver driver){
        System.out.println("Redirecting to the login page.");
        driver.get(LOGIN_PAGE_URL);
        return driver;
    }
 
    private static WebDriver redirectToConverterPage(WebDriver driver){
        System.out.println("Redirecting to the temperature converter page.");
        driver.get(CONVERSION_URL);
        return driver;
    }
 
    private static void initializeUserList(){
        users = new ArrayList<String>();
        userPasswords = new HashMap<String,String>();
        users.add("andy");
        users.add("bob");
        users.add("charley");
        userPasswords.put("andy", "apple");
        userPasswords.put("bob", "bathtub");
        userPasswords.put("charley", "china");
    }
 
 
}
