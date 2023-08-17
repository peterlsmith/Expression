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

If the -e option is omitted, the evaluator will enter interactive mode. You can then repeatedly type in an expression and press &lt;enter> to evaluate it. To exit interactive mode, press &lt;ctrl> d;

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
The Expression library can be extended with new functions and operators if required. To add a new function we use the <code>Parser.registerFunction</code> method. This takes a <code>Factory</code> object that will be used to create instances of the new function.

<pre>
    import pls.expression.Functions;
    import pls.expression.Parser;
    import pls.expression.Operators;

    /* Create a function to calculate the tax on an item */

    Parser.registerFunction(
        Functions.create(
            "tax",
            (context, v) -> Operators.toFloat(v) * 0.065));
</pre>
In this example we use a utility method <code>Functions.create</code> to create the factory object, providing it with a lambda method representing the function we are registering. However, you may create your own factory instances directly by extending the <code>Factory</code> class.

This utility method provides support for registering functions that take zero, one, or two arguments, e.g:

<pre>
    /* Function with zero arguments */
    
    Parser.registerFunction(
        Functions.create(
            "random", (context) -> Math.random()));

    /* Function with one argument */
    
    Parser.registerFunction(
        Functions.create(
            "toString", (context, value) -> Objects.toString(value)));


    /* Function with two arguments */
    
    Parser.registerFunction(
        Functions.create(
            "toStringOrDefault", (context, value, default) -> Objects.toString(value, default)));
</pre>

To register functions that take more than two arguments, you will need to provide a custom implementation of an expression factory. The following example implements a concatenation function which takes any number of arguments.
<pre>
    Parser.registerFunction(
        new Factory("concat") {
            @Override
            public Expression create(Expression ...input) throws ExpressionError {
                return new NamedExpression("concat") {
                    @Override
                    public Object eval(ExpressionContext context) throws ExpressionError {
                        String[] values = new String[input.length];
                        for (int i = 0; i < input.length; ++i) {
                            values[i] = input[i].eval(context).toString();
                        }
                        return String.join("", values);
                    }
                    @Override
                    public String toString() {
                        return "concat(" + 
                            String.join(",", Arrays.stream(input).map((e) -> e.toString()).toArray(String[]::new))
                            + ")";
                    }
                };
            }
        }
    );
</pre>
For more examples of registering functions, check the <code>Functions</code> source code.  




<p>Operators can similarly be created.

<pre>
    import pls.expression.Functions;
    import pls.expression.Parser;
    import pls.expression.Operators;

    /* Create a modulo operator */

    Parser.registerOperator(
        Operators.createArithmetic(
            "%",
            (l, r) -> l % r, (l, r) -> l % r), 12);
</pre>
As with registering functions, we use a utility method, in this case, <code>Operators.createArithmetic</code>, to create a factory object. Arithmetic operators require two functions - one for integer operations and another for floating point operations, as well as an operator precedence value. In most cases, the lambda functions for these will appear identical (though they will be discreet functions with different argument types). You may also use  <code>Operators.createUnary</code>, <code>Operators.createBinary</code>, and <code>Operators.createRelational</code> utility methods for registering unary, binary, and relational operators.  

Please note the differences between the operators as follows:  


- **Unary operators**  
Unary operators take a single, unevaluated expression. They are responsible for evaluating the expression with the expression context and performing the unary operations. Unary operators may take any type of argument - integer, floating point, or string. Unary operators also do not have a precedence value - they are all implicity higher than non unary operators.  
<pre>
    Parser.registerOperator(
        Operators.createUnary(
            "#",
            (c, e) -> MyOperators.do_somthing(e.eval(c))));
</pre>


- **Binary operators**  
Binary operators take two unevaluated expressions - a 'left' and a 'right' expression. They are responsible for evaluating the expressions as required, and performing the unary operations. Binary operators may take any type of argument - integer, floating point, or string.  
<pre>
    Parser.registerOperator(
        Operators.createBinary(
            "in", 
            (c, l, r) -> Operators.in(l.eval(c), r.eval(c))),
            9);
</pre>


- **Relational operators**  
Relational operators take two **evaluated** expressions - i.e. a 'left' and a 'right' **value**. If non-null, these two values must be of the same type (integer, floating point, or string).
<pre>
    Parser.registerOperator(
        Operators.createRelational(
            "==",
            (v1, v2) -> Objects.equals(v1, v2)),
            8);
</pre>


- **Arithmetic operators**  
Arithmetic operators take two **evaluated** expressions - i.e. a 'left' and a 'right' **value**. These two values must be non-null, and of the same type - either integer or floating point.
<pre>
    Parser.registerOperator(
        Operators.createArithmetic(
            "+",
            (l, r) -> l + r,  /* Integer lambda */
            (l, r) -> l + r), /* Floating point lambda */
            11);
</pre>


Please note that functions and operators cannot be overloaded using different numbers of arguments.  

