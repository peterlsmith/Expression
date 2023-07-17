package pls.expression;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestFunctions {



    /**
     * Test registering a function which takes no arguments
     */
    @Test public void testFunction0() throws ExpressionError {

        double pi = 3.1415;
        Parser.registerFunction(Functions.create("pi", (context) -> pi));

        Parser parser = new Parser();

        String expr = "pi()";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a Float", result.getClass(), Double.class);
        assertEquals("Expression result value is correct", result, Double.valueOf(pi));
    }




    /**
     * Test registering a function which takes one argument
     */
    @Test public void testFunction1() throws ExpressionError {

        Parser.registerFunction(Functions.create("square", (context, arg) -> Operators.toFloat(arg) * Operators.toFloat(arg)));

        Parser parser = new Parser();

        String expr = "square(5.5)";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a Float", result.getClass(), Double.class);
        assertEquals("Expression result value is correct", result, Double.valueOf(30.25));
    }




    /**
     * Test registering a function which takes two arguments
     */
    @Test public void testFunction2() throws ExpressionError {

        Parser.registerFunction(Functions.create("mult", (context, arg1, arg2) -> Operators.toFloat(arg1) * Operators.toFloat(arg2)));

        Parser parser = new Parser();

        String expr = "mult(1.7,8.8)";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(null);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a Float", result.getClass(), Double.class);
        assertEquals("Expression result value is correct", result, Double.valueOf(14.96));
    }



    /**
     * Test default string functions: concat
     */
    @Test public void testConcat1() throws ExpressionError {

        Functions.registerStringFunctions();

        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", "hello");

        String expr = "concat(a)";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a String", result.getClass(), String.class);
        assertEquals("Expression result value is correct", result, "hello");
    }



    /**
     * Test default string functions: concat
     */
    @Test public void testConcat2() throws ExpressionError {

        Functions.registerStringFunctions();

        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", "hello");
        context.set("b", "world");

        String expr = "concat(a,b)";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a String", result.getClass(), String.class);
        assertEquals("Expression result value is correct", result, "helloworld");
    }



    /**
     * Test default string functions: concat
     */
    @Test public void testConcat3() throws ExpressionError {

        Functions.registerStringFunctions();

        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", "hello");
        context.set("b", " ");
        context.set("c", "world");

        String expr = "concat(a,b,c)";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a String", result.getClass(), String.class);
        assertEquals("Expression result value is correct", result, "hello world");
    }




    /**
     * Test default string functions: tolower
     */
    @Test public void testToLower() throws ExpressionError {

        Functions.registerStringFunctions();

        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", "HelLo");

        String expr = "tolower(a)";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a String", result.getClass(), String.class);
        assertEquals("Expression result value is correct", result, "hello");
    }




    /**
     * Test default string functions: toupper
     */
    @Test public void testToUpper() throws ExpressionError {

        Functions.registerStringFunctions();

        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", "HelLo");

        String expr = "toupper(a)";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a String", result.getClass(), String.class);
        assertEquals("Expression result value is correct", result, "HELLO");
    }





    /**
     * Test default string functions: length
     */
    @Test public void testLength() throws ExpressionError {

        Functions.registerStringFunctions();

        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", "What is the length of this string?");

        String expr = "length(a)";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is an integer", result.getClass(), Integer.class);
        assertEquals("Expression result value is correct", result, 34);
    }


    /**
     * Test default string functions: regex
     */
    @Test public void testRegex() throws ExpressionError {

        Functions.registerStringFunctions();

        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();

        String expr = "\"Casablanca\" =~ \"bla\"";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is boolean", result.getClass(), Boolean.class);
        assertEquals("Expression result value is correct", result, true);
    }
}