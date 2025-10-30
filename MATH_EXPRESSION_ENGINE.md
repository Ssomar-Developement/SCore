# Math Expression Engine

## Overview

The Math Expression Engine is a powerful new feature in SCore that enables advanced mathematical calculations and conditional expressions within placeholders. This allows for dynamic, complex calculations without requiring custom code.

## Usage

Use the `%math:expression%` placeholder syntax to evaluate mathematical expressions anywhere in SCore.

### Basic Syntax

```
%math:expression%
```

Where `expression` can be any valid mathematical expression.

## Features

### 1. Basic Arithmetic

Supports standard arithmetic operators: `+`, `-`, `*`, `/`, `%`

**Examples:**
```
%math:2+3%              → 5
%math:10-4%             → 6
%math:3*4%              → 12
%math:20/5%             → 4
%math:10%3%             → 1 (modulo)
%math:2+3*4%            → 14 (respects operator precedence)
%math:(2+3)*4%          → 20 (parentheses for grouping)
```

### 2. Mathematical Functions

#### Basic Functions
- `abs(x)` - Absolute value
- `min(x, y)` - Minimum of two values
- `max(x, y)` - Maximum of two values
- `round(x)` - Round to nearest integer
- `floor(x)` - Round down to integer
- `ceil(x)` - Round up to integer

**Examples:**
```
%math:abs(-5)%          → 5
%math:min(10, 20)%      → 10
%math:max(10, 20)%      → 20
%math:round(3.7)%       → 4
%math:floor(3.7)%       → 3
%math:ceil(3.2)%        → 4
```

#### Trigonometric Functions (radians)
- `sin(x)` - Sine
- `cos(x)` - Cosine
- `tan(x)` - Tangent
- `asin(x)` - Arc sine
- `acos(x)` - Arc cosine
- `atan(x)` - Arc tangent

**Examples:**
```
%math:sin(0)%           → 0
%math:cos(0)%           → 1
%math:sin(1.5708)%      → 1 (approximately π/2)
```

#### Advanced Functions
- `sqrt(x)` - Square root
- `pow(x, y)` - Power (x^y)
- `log(x)` - Natural logarithm
- `log10(x)` - Base-10 logarithm
- `exp(x)` - Exponential (e^x)

**Examples:**
```
%math:sqrt(16)%         → 4
%math:pow(2, 3)%        → 8
%math:log(2.718281828)% → 1 (approximately)
%math:log10(100)%       → 2
%math:exp(1)%           → 2.718281828 (approximately)
```

### 3. Comparison Operators

Compare values: `==`, `!=`, `<`, `>`, `<=`, `>=`

Returns `1` for true, `0` for false.

**Examples:**
```
%math:5>3%              → 1 (true)
%math:2==2%             → 1 (true)
%math:10<5%             → 0 (false)
%math:7>=7%             → 1 (true)
%math:3!=3%             → 0 (false)
```

### 4. Logical Operators

Combine conditions: `&&` (AND), `||` (OR), `!` (NOT)

**Examples:**
```
%math:1 && 1%           → 1 (true AND true)
%math:1 && 0%           → 0 (true AND false)
%math:1 || 0%           → 1 (true OR false)
%math:!0%               → 1 (NOT false)
%math:!(5>10)%          → 1 (NOT false)
```

### 5. Conditional Expressions

Use `if(condition, trueValue, falseValue)` for conditional logic.

**Examples:**
```
%math:if(5>3, 10, 20)%           → 10
%math:if(2==3, 100, 200)%        → 200
%math:if(1, 999, 0)%             → 999 (1 is true)
```

### 6. Nested Expressions

Combine multiple functions and operations.

**Examples:**
```
%math:min(5, max(2, 8))%         → 5
%math:sqrt(pow(3,2) + pow(4,2))% → 5 (Pythagorean theorem)
%math:if(sqrt(16)==4, 1, 0)%     → 1
%math:round(sqrt(50))%           → 7
```

## Practical Use Cases

### 1. Health-Based Damage Calculation
```yaml
damage: %math:if(%player_health%<5, 20, 10)%
```
Deal 20 damage if player health is below 5, otherwise deal 10.

### 2. Distance-Based Effects
```yaml
power: %math:max(1, 10-floor(%entity_distance%))%
```
Power decreases with distance, minimum of 1.

### 3. Level-Based Scaling
```yaml
reward: %math:round(%player_level% * 1.5 + 10)%
```
Calculate rewards that scale with player level.

### 4. Complex Conditions
```yaml
canUse: %math:(%player_health%>10) && (%player_level%>=5)%
```
Check if player meets multiple requirements.

### 5. Circular Motion Calculations
```yaml
x: %math:cos(%timestamp%/1000) * 5%
y: %math:sin(%timestamp%/1000) * 5%
```
Create circular motion patterns.

### 6. Random with Constraints
```yaml
value: %math:min(100, max(10, %rand:1|100%))%
```
Ensure random values stay within bounds.

## Integration with Other Placeholders

Math expressions can include other SCore placeholders:

```
%math:%player_health% + %target_health%%
%math:if(%player_level% >= 10, 100, 50)%
%math:sqrt(%entity_distance%)%
%math:round(%player_health% / 2)%
```

The placeholder system processes placeholders first, then evaluates the math expression.

## Error Handling

If a math expression is invalid:
- An error message is logged to the console
- The original placeholder is left unchanged
- Execution continues normally

**Example Error:**
```
[SCore] Error evaluating math expression: %math:sqrt(-1)% - Invalid expression
```

## Technical Details

### Order of Operations

1. Parentheses: `()`
2. Functions: `sin()`, `sqrt()`, etc.
3. Unary operators: `-`, `!`
4. Multiplication/Division: `*`, `/`, `%`
5. Addition/Subtraction: `+`, `-`
6. Comparison: `<`, `>`, `<=`, `>=`, `==`, `!=`
7. Logical AND: `&&`
8. Logical OR: `||`

### Precision

- All calculations use double-precision floating-point
- Integer results are automatically formatted without decimal points
- Very small differences in floating-point comparisons are handled

### Performance

- Expressions are evaluated on-demand
- No caching is performed (allows dynamic placeholder values)
- Complex expressions may impact performance if used extensively

## Compatibility

- Works with all SCore placeholders
- Compatible with PlaceholderAPI placeholders
- Can be used in any SCore feature that supports placeholders
- Works in ExecutableItems, ExecutableBlocks, and other dependent plugins

## Examples Gallery

### Game Mechanics

**Critical Hit System:**
```yaml
damage: %math:if(%rand:1|100% > 90, %damage% * 2, %damage%)%
```

**Experience Curve:**
```yaml
xp_needed: %math:pow(%player_level%, 2) * 100%
```

**Area Calculation:**
```yaml
area: %math:3.14159 * pow(%radius%, 2)%
```

### Visual Effects

**Sine Wave Height:**
```yaml
height: %math:sin(%timestamp%/500) * 3 + 5%
```

**Spiral Pattern:**
```yaml
radius: %math:%timestamp% / 100%
angle: %math:%timestamp% / 50%
x: %math:cos(%angle%) * %radius%%
y: %math:sin(%angle%) * %radius%%
```

### Utility

**Time Formatting:**
```yaml
hours: %math:floor(%timestamp% / 3600000)%
minutes: %math:floor((%timestamp% % 3600000) / 60000)%
```

**Percentage Calculation:**
```yaml
percentage: %math:round((%player_health% / %player_max_health%) * 100)%
```

**Clamping Values:**
```yaml
safe_value: %math:max(0, min(100, %value%))%
```

## Support

For issues or questions about the Math Expression Engine:
1. Check this documentation
2. Verify your expression syntax
3. Check console for error messages
4. Report bugs on GitHub

## Future Enhancements

Potential future additions:
- More mathematical functions (factorial, combinations, etc.)
- String manipulation functions
- Random number generation functions
- Custom function definitions

---

**Version:** 1.0
**Added in:** SCore [Next Version]
**Author:** SCore Development Team
