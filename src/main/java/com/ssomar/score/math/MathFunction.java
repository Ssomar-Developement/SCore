package com.ssomar.score.math;

/**
 * Enum representing supported mathematical functions in the expression engine
 *
 * Supported functions:
 * - Basic: abs, min, max, round, floor, ceil
 * - Trigonometric: sin, cos, tan, asin, acos, atan
 * - Advanced: sqrt, pow, log, log10, exp
 * - Conditional: if (ternary operator)
 *
 * @author SCore Math Engine
 * @version 1.0
 */
public enum MathFunction {

    // Basic functions
    ABS("abs", 1),
    MIN("min", 2),
    MAX("max", 2),
    ROUND("round", 1),
    FLOOR("floor", 1),
    CEIL("ceil", 1),

    // Trigonometric functions (radians)
    SIN("sin", 1),
    COS("cos", 1),
    TAN("tan", 1),
    ASIN("asin", 1),
    ACOS("acos", 1),
    ATAN("atan", 1),

    // Advanced functions
    SQRT("sqrt", 1),
    POW("pow", 2),
    LOG("log", 1),    // Natural logarithm
    LOG10("log10", 1),
    EXP("exp", 1),

    // Conditional function
    IF("if", 3);  // if(condition, trueValue, falseValue)

    private final String name;
    private final int paramCount;

    MathFunction(String name, int paramCount) {
        this.name = name;
        this.paramCount = paramCount;
    }

    public String getName() {
        return name;
    }

    public int getParamCount() {
        return paramCount;
    }

    /**
     * Find a function by name
     * @param name The function name
     * @return The MathFunction or null if not found
     */
    public static MathFunction fromName(String name) {
        for (MathFunction func : values()) {
            if (func.name.equalsIgnoreCase(name)) {
                return func;
            }
        }
        return null;
    }

    /**
     * Execute the function with given parameters
     * @param params The parameters
     * @return The result
     * @throws IllegalArgumentException if parameter count is wrong
     */
    public double execute(double... params) {
        if (params.length != paramCount) {
            throw new IllegalArgumentException(
                String.format("Function %s expects %d parameters, got %d",
                    name, paramCount, params.length)
            );
        }

        switch (this) {
            // Basic functions
            case ABS:
                return Math.abs(params[0]);
            case MIN:
                return Math.min(params[0], params[1]);
            case MAX:
                return Math.max(params[0], params[1]);
            case ROUND:
                return Math.round(params[0]);
            case FLOOR:
                return Math.floor(params[0]);
            case CEIL:
                return Math.ceil(params[0]);

            // Trigonometric functions
            case SIN:
                return Math.sin(params[0]);
            case COS:
                return Math.cos(params[0]);
            case TAN:
                return Math.tan(params[0]);
            case ASIN:
                return Math.asin(params[0]);
            case ACOS:
                return Math.acos(params[0]);
            case ATAN:
                return Math.atan(params[0]);

            // Advanced functions
            case SQRT:
                return Math.sqrt(params[0]);
            case POW:
                return Math.pow(params[0], params[1]);
            case LOG:
                return Math.log(params[0]);
            case LOG10:
                return Math.log10(params[0]);
            case EXP:
                return Math.exp(params[0]);

            // Conditional function
            case IF:
                return params[0] != 0 ? params[1] : params[2];

            default:
                throw new UnsupportedOperationException("Function not implemented: " + name);
        }
    }
}
