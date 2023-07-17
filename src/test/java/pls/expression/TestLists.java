package pls.expression;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestLists {


    /**
     * Test list construction
     */
    @Test public void testListConstruction() throws ExpressionError {

        String expr = "[1,\"balloon\",3.14,false]";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", Object[].class, result.getClass());
        //assertEquals("Expression result value is correct", 5, result);
    }



    /**
     * Test 'in' operator
     */
    @Test public void testInList() throws ExpressionError {

        String expr = "false in [1,\"balloon\",3.14,false]";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", Boolean.class, result.getClass());
        assertEquals("Expression result value is correct", true, result);
    }



    /**
     * Test 'in' operator
     */
    @Test public void testNotInList() throws ExpressionError {

        String expr = "\"ball\" in [1,\"balloon\",3.14,false]";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", Boolean.class, result.getClass());
        assertEquals("Expression result value is correct", false, result);
    }


}