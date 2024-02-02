package algorithms

fun main() {
    val testData = """
        36 30 36 30
        15 15 15 15
        46 96 90 100
        86 86 86 86
        100 200 100 200
        -100 200 -100 200
    """.trimIndent()

    println(testData)
    println(polygon(testData))
}

fun polygon(input: String): String {
    var squares = 0
    var rectangles = 0
    var others = 0

    val lines = input.split("\n")

    lines.forEach { line ->
        val sides = line.split(" ").mapNotNull { it.toIntOrNull() }
        val shape = if (sides.size == 4) {
            getShapeType(sides[0], sides[1], sides[2], sides[3])
        } else {
            throw IllegalArgumentException("Unsupported shape")
        }

        when (shape) {
            ShapeType.SQUARE -> squares++
            ShapeType.RECTANGLE -> rectangles++
            ShapeType.OTHER -> others++
        }
    }

    return "$squares $rectangles $others"
}

enum class ShapeType {
    SQUARE, RECTANGLE, OTHER
}

fun getShapeType(a: Int, b: Int, c: Int, d: Int): ShapeType {
    return when {
        0 >= minOf(a, b, c, d) -> ShapeType.OTHER
        (a == b) && (b == c) && (c == d) -> ShapeType.SQUARE
        (a == c) && (b == d) -> ShapeType.RECTANGLE
        else -> ShapeType.OTHER
    }
}