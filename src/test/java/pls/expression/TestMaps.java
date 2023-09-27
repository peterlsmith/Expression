package pls.expression;

import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


public class TestMaps {


    static {
        Functions.registerStringFunctions();
    }


    /**
     * Test dot '.' operator can be parsed and output again
     */
    @Test public void testDotParse() throws ExpressionError {

        Map<String, String> map = Map.of("member_1", "hello");
        String expr = "thing.member_1";

        Parser parser = new Parser();
        Expression e = parser.parse(expr);


        // Check that the expression parsed

        assertNotNull("Parsed expression", e);


        // Check that the expression can be output to a string correctly 

        String expanded = e.toString().replaceAll("\\s", "");
        assertEquals("Expression toString is correct", expanded, expr);
    }



    /**
     * Test dot '.' operator
     */
    @Test public void testSimpleStringMapGet() throws ExpressionError {

        String value = "hello";
        Map<String, String> map = Map.of("member_1", value);

        ExpressionContext context = new ExpressionContext();
        context.set("thing", map);

        String expr = "thing.member_1";
        Parser parser = new Parser();
        Expression e = parser.parse(expr);


        // Check that the expression parsed

        assertNotNull("Parsed expression", e);


        // Evaluate the expression

        Object result = e.eval(context);


        // Check everything about the result

        assertNotNull("Result is not null", result);
        assertEquals("Result type is correct", result.getClass(), value.getClass());
        assertEquals("Result value is correct", result, value);
    }


    /**
     * Test dot '.' operator
     */
    @Test public void testSimpleNumberMapGet() throws ExpressionError {

        Integer value = Integer.valueOf(10245);
        Map<String, Object> map = Map.of("member_1", value);
        String expr = "thing.member_1";

        ExpressionContext context = new ExpressionContext();
        context.set("thing", map);

        Parser parser = new Parser();
        Expression e = parser.parse(expr);


        // Check that the expression parsed

        assertNotNull("Parsed expression", e);


        // Evaluate the expression

        Object result = e.eval(context);


        // Check everything about the result

        assertNotNull("Result is not null", result);
        assertEquals("Result type is correct", result.getClass(), value.getClass());
        assertEquals("Result value is correct", result, value);
    }


    /**
     * Test dot '.' operator on a nested map
     */
    @Test public void testRecursiveStringMapGet() throws ExpressionError {

        String value = "nested";
        Map<String, Object> map = Map.of("member_1", Map.of("member_2", value));

        ExpressionContext context = new ExpressionContext();
        context.set("thing", map);

        String expr = "thing.member_1.member_2";
        Parser parser = new Parser();
        Expression e = parser.parse(expr);


        // Check that the expression parsed

        assertNotNull("Parsed expression", e);


        // Check that the expression converts back to a string properly

        String expanded = e.toString().replaceAll("\\s", "");
        assertEquals("Expression toString is correct", expanded, expr);
 

        // Evaluate the expression

        Object result = e.eval(context);


        // Check everything about the result

        assertNotNull("Result is not null", result);
        assertEquals("Result type is correct", result.getClass(), value.getClass());
        assertEquals("Result value is correct", result, value);
    }


}