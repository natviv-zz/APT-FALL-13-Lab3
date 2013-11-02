import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockHttpServletResponse;
 
import junit.framework.TestCase;
 
 
public class TestTemperatureConverterServlet extends TestCase {
     
    public void testforNoTempParameter() throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>No Temperature</title></head><body><h2>Need to enter a temperature!</h2></body></html>\n",
                     response.getOutputStreamContents());
      }
     
    public void testforParameterCaseInsensitive() throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("FArenHEitTemPeRAture", "foo");
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>Bad Temperature</title>"
                + "</head><body><h2>Need to enter a valid temperature!"
                + "Got a NumberFormatException on "
                + "foo"
                + "</h2></body></html>\n",
                     response.getOutputStreamContents());
    }
     
    public void testforBadParameter() throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("farenheitTemperature", "foo");
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>Bad Temperature</title>"
                + "</head><body><h2>Need to enter a valid temperature!"
                + "Got a NumberFormatException on "
                + "foo"
                + "</h2></body></html>\n",
                     response.getOutputStreamContents());
      }
     
    public void testforPrecisionfor2() throws Exception {
        precisionTestfor2("3.14");
        precisionTestfor2("100");
        //precisionTestfor2("212");
   
      }

     public void testforPrecisionfor1() throws Exception {
        precisionTestfor1("-666");
        precisionTestfor1("666");
      }



    //precision test for two places in range [0,212] 
    private static void precisionTestfor2(String param) throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("farenheitTemperature", param);
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        System.out.println("response length= "+response.getOutputStreamContents().split("=")[1].split("\\s")[1].split("\\.")[1].length());
        assertTrue(response.getOutputStreamContents().split("=")[1].split("\\s")[1].split("\\.")[1].length() == 2);
      }

    //precision test for one place of in range outside of [0,212]

      private static void precisionTestfor1(String param) throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("farenheitTemperature", param);
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        System.out.println(response.getOutputStreamContents());
        assertTrue(response.getOutputStreamContents().split("=")[1].split("\\s")[1].split("\\.")[1].length() == 1);
      }

     //test for non decimal input notation
    public void testforNonDecimalNotation() throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("farenheitTemperature", "9.73E2");
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>Bad Temperature</title>"
                + "</head><body><h2>Need to enter a valid temperature!"
                + "Got a NumberFormatException on "
                + "9.73E2"
                + "</h2></body></html>\n",
                     response.getOutputStreamContents());
      }
     
     
     public static void main(String args[]) {
          String[] testCaseName =  { TestTemperatureConverterServlet.class.getName() };
          junit.textui.TestRunner.main(testCaseName);
      }
 
}
