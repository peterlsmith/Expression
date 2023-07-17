# Expression
A light weight java expression parser and evaluator.  

This library provides a tool to evaluate arbitrary expressions. It uses a simplified parser (not a full BNF grammar) that handles almost all use cases - parenthesis, operator precedence, unary operators, etc, while remaining exceptionally fast. The parser produces an expression tree that can be repeatedly evaluated with different contexts (input variables) without the need to reparse. The library can easily be extended with additional functions and operators.

---

## Building
Build scripts are provided for Ant, Gradle and Maven. 

### Ant
To build using ant, at the command prompt in the root project directory (the one with the **build.xml** file), type:

> ant

The resulting jar file will be placed into the <code>dist</code> directory.  
To run the unit tests, use:

> ant test


### Gradle
To build using Gradle, at the command prompt in the root project directory (the one with the **build.gradle** file), type:

> gradle build

The resulting jar file will be placed into the <code>build/libs</code> directory.  
To run the unit tests, use:

> gradle test


### Maven
To build using Maven, at the command prompt in the root project directory (the one with the **pom.xml** file), type:

> mvn compile

The resulting jar file will be placed into the <code>target</code> directory.  
To run the unit tests, use:

> mvn test


---

## Testing
The expression library comes with a simple command line tool that can be used for manual testing. To run the expression evaluator with a given expression:


> java -cp dist/expression-1.0.jar pls.expression.Parser -e "3.2 * 6.8 / 2.2 * (4.1 + 1.4)"  
> 54.4  

This command line tool automatically registers a number of string and arithmetic functions:

> java -cp dist/expression-1.0.jar pls.expression.Parser -e "cos(rad(45)) ^ 2"  
> 0.5000000000000001  


> java -cp dist/expression-1.0.jar pls.expression.Parser -e 'concat("hello", " ",  "world")'  
> hello world  

> java -cp dist/expression-1.0.jar pls.expression.Parser -e '"Constantinople" matches "tin"'  
> true  


You can use the -i, -s, and -f option to set integer, string, and floating point context variables prior to expression evaluation:

> java -cp dist/expression-1.0.jar pls.expression.Parser -i delta=14 -e "2 * delta"  
> 28  

If the -e option is omitted, the evaluator will enter interactive mode. You can then repeatedly type in an expression and press &lt;enter> to evaluate it. To exit interactive mode, simple press &lt;ctrl> d;

---

## Embedding

The expression library is primarily designed to be embedded into other Java applications. This can be done with just a few lines of code:

<pre>
    import pls.expression.Parser;

    ...


    /* Create a parser instance, parse the expression and evaluate */

    String expression = "10 + 15 * 6 / 5";
    Number result = (Number) new Parser().parse(expression).eval(null);
</pre>

Note that you will need to add the Expression JAR file to your application classpath.
<p>If you need access to standard math functions, for example, trigonometric functions, you can configure most of these automatically with a single API call.

<pre>
    import pls.expression.Functions;
    import pls.expression.Parser;

    ...

    /* Register a range of mathematical functions */

    Functions.registerMathFunctions();

    ...

    /* Create a parser instance, parse the expression and evaluate */

    String expression = "2.5 * cos(rad(60.0))";
    Number result = (Number) new Parser().parse(expression).eval(null);
</pre>

Similarly, a number of standard string functions can included.

<pre>
    import pls.expression.Functions;
    import pls.expression.Parser;

    ...

    /* Register a range of string functions */

    Functions.registerStringFunctions();

    ...

    /* Create a parser instance, parse the expression and evaluate */

    String expression = "tolower(\"Hello\")";
    String result = new Parser().parse(expression).eval(null).toString();
</pre>

### Context values
If you wish to define some values to be used in the expression externally, you can use an expression context.

<pre>
    import pls.expression.ExpressionContext;
    import pls.expression.Functions;
    import pls.expression.Parser;

    ...

    Functions.registerStringFunctions();

    ...

    /* Set up the context */

    ExpressionContext context = new ExpressionContext();
    context.set("firstname", "John");
    context.set("lastname", "Smith");


    /* Create a parser instance, parse the expression and evaluate */

    String expression = "concat(firstname, \" \", lastname)";
    String result = new Parser().parse(expression).eval(context).toString();
</pre>

Note that once parsed, you can repeatedly evaluate the expression with different contexts.


<pre>
    import pls.expression.Expression;
    import pls.expression.ExpressionContext;
    import pls.expression.Functions;
    import pls.expression.Parser;

    ...

    /* Set up the context */

    ExpressionContext context = new ExpressionContext();


    /* Create a parser instance, parse the expression and evaluate */

    String expression = "cost * count";
    Expression expr = new Parser().parse(expression);

    context.set(cost, 10.00);
    context.set(count, 103);
    Number result = (Number) expr.eval(context); // 1030.0

    ...

    context.set(cost, 16.71);
    context.set(count, 81);
    Number result = (Number) expr.eval(context); // 1353.51
</pre>

Note that the context values do not need to be configured when the expression is parsed, only before it is evaluated.

### Extending
The Expression library can be extended with new functions and operators if required. To add a new function:

<pre>
    import pls.expression.Functions;
    import pls.expression.Parser;
    import pls.expression.Operators;

    /* Create a function to calculate the tax on an item */

    Parser.registerFunction(Functions.create("tax", (context, v) -> Operators.toFloat(v) * 0.065));
</pre>

For more examples of registering functions, check the {@link Functions} source code.
<p>Operators can similarly be created.

<pre>
    import pls.expression.Functions;
    import pls.expression.Parser;
    import pls.expression.Operators;

    /* Create a modulo operator */

    Parser.registerOperator(Operators.createArithmetic("%", (l, r) -> l % r, (l, r) -> l % r), 12);
</pre>

Note that in this example, we are creating an arithmetic type operator which requires two operator functions, the first one for integer operations, the second for floating point. In this case they are identical. The final argument is an integer value representing the operator precedence. For more examples of registering operators, check the {@link Operators} source code.


