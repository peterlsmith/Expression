package pls.expression;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestContext {



    /**
     * Test default string functions: concat
     */
    @Test public void testContextString() throws ExpressionError {


        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", "hello");

        String expr = "a";
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
    @Test public void testContextInteger() throws ExpressionError {


        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", 3);

        String expr = "a";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a String", result.getClass(), Integer.class);
        assertEquals("Expression result value is correct", result, 3);
    }




    /**
     * Test default string functions: concat
     */
    @Test public void testContextFloat() throws ExpressionError {


        Parser parser = new Parser();

        ExpressionContext context = new ExpressionContext();
        context.set("a", 3.14);

        String expr = "a";
        Expression e = parser.parse(expr);

        assertNotEquals("Parsed expression", e, null);
        Object result = e.eval(context);

        assertEquals(e.toString(), expr);
        assertEquals("Expression result is a String", result.getClass(), Double.class);
        assertEquals("Expression result value is correct", result, 3.14);
    }
}


