import kotlin.test.*

internal class TestParser {
    private val scanner: Tokenizer = Tokenizer()

    @Test
    fun testIntegerNumber() {
        scanner.scan("42")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(42.0, result)
        }
    }

    @Test
    fun testDoubleNumber() {
        scanner.scan("45.0")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(45.0, result)
        }
    }

    @Test
    fun testSimpleMultiply() {
        scanner.scan("2 * 7")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(14.0, result)
        }
    }

    @Test
    fun testSimpleMultiplyDoubles() {
        scanner.scan("2.5 * 3.5")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(8.75, result)
        }
    }

    @Test
    fun testSimpleDivide() {
        scanner.scan("10 / 2")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(5.0, result)
        }
    }

    @Test
    fun testDivideDoubleRes() {
        scanner.scan("5 / 2")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(2.5, result)
        }
    }

    @Test
    fun simpleAddition() {
        scanner.scan("16 + 26")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(42.0, result)
        }
    }

    @Test
    fun multipleAddition() {
        scanner.scan("16 + 25 + 1")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(42.0, result)
        }
    }

    @Test
    fun multipleMultiplication() {
        scanner.scan("7 * 3 * 2")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(42.0, result)
        }
    }

    @Test
    fun additionAndSubstraction() {
        scanner.scan("16 + 30 - 4")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(42.0, result)
        }
    }

    @Test
    fun additionAndMultiplication() {
        scanner.scan("5 + 5 * 3 + 5")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(25.0, result)
        }
    }

    @Test
    fun simpleAdditionWithParentheses() {
        scanner.scan("(1+2+3)")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(6.0, result)
        }
    }

    @Test
    fun parenthesesBasedOrder() {
        scanner.scan("(5 + 5) * (3 + 5)")?.let {
            val parser = Parser(it)
            val result = parser.parseExpression()
            assertEquals(80.0, result)
        }
    }
}