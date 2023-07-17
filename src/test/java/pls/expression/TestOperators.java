package pls.expression;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestOperators {


    /**
     * Test integer addition
     */
    @Test public void testAddInteger() throws ExpressionError {

        String expr = "3 + 2";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", result.getClass(), Integer.class);
        assertEquals("Expression result value is correct", 5, result);
    }


    /**
     * Test floating point addition
     */
    @Test public void testAddFloat() throws ExpressionError {

        String expr = "1.612 + 14.839";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a float", result.getClass(), Double.class);
        assertEquals("Expression result value is correct", 16.451, result);
    }


    /**
     * Test integer subtraction
     */
    @Test public void testSubtractInteger() throws ExpressionError {

        String expr = "81 - 92";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", result.getClass(), Integer.class);
        assertEquals("Expression result value is correct", -11, result);
    }


    /**
     * Test floating point subtraction
     */
    @Test public void testSubtractFloat() throws ExpressionError {

        String expr = "15.93 - 11.28";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a float", result.getClass(), Double.class);
        assertEquals("Expression result value is correct", 4.65, result);
    }


    /**
     * Test integer multiplication
     */
    @Test public void testMultiplyInteger() throws ExpressionError {

        String expr = "104 * 69";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", result.getClass(), Integer.class);
        assertEquals("Expression result value is correct", 7176, result);
    }


    /**
     * Test floating point multiplication
     */
    @Test public void testMultiplyFloat() throws ExpressionError {

        String expr = "17.71 * 64.23";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a float", result.getClass(), Double.class);
        assertEquals("Expression result value is correct", 1137.5133, result);
    }


    /**
     * Test integer division
     */
    @Test public void testDivideInteger() throws ExpressionError {

        String expr = "1472 / 23";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", result.getClass(), Integer.class);
        assertEquals("Expression result value is correct", 64, result);
    }


    /**
     * Test floating point division
     */
    @Test public void testDivideFloat() throws ExpressionError {

        String expr = "489.84 / 15.7";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a float", result.getClass(), Double.class);
        assertEquals("Expression result value is correct", 31.2, result);
    }



    /**
     * Test operator precedence
     */
    @Test public void testPrecedence1() throws ExpressionError {

        String expr = "3 + 5 * 21 - 27 / 9";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", result.getClass(), Integer.class);
        assertEquals("Expression result value is correct", 105, result);
    }



    /**
     * Test operator precedence
     */
    @Test public void testPrecedence2() throws ExpressionError {

        String expr = "(3 + 5 * 21 - 27) / 9";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", result.getClass(), Integer.class);
        assertEquals("Expression result value is correct", 9, result);
    }

}