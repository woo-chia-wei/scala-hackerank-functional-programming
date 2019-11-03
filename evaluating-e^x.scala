object Solution {

    def main(args: Array[String]) {
        val stdin = scala.io.StdIn

        val n = stdin.readLine.trim.toInt

        for (nItr <- 1 to n) {
            val x = stdin.readLine.trim.toDouble

            val terms = Range(0, 10).map(math.pow(x, _))
            val coefficients = Range(1, 10).scan(1)(_ * _)
            val result = (terms, coefficients).zipped.map((_ / _)).sum

            println(f"$result%1.4f")
        }
    }
}
