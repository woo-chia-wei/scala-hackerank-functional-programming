object Solution {

    def main(args: Array[String]) {
        val stdin = scala.io.StdIn

        val countA = stdin.readLine.trim.toInt
        val listA = stdin.readLine.trim.split(" ").map(_.toInt)
        val countB = stdin.readLine.trim.toInt
        val listB = stdin.readLine.trim.split(" ").map(_.toInt)

        val mapA = listA.groupBy(l => l).transform((k, v) => v.length)
        val mapB = listB.groupBy(l => l).transform((k, v) => v.length)

        val scanNumbers = (listA ++ listB).toSet.toSeq.sorted

        val missingNumbers = scanNumbers.filter(n => {
            val countA = mapA.get(n).getOrElse(0)
            val countB = mapB.get(n).getOrElse(0)
            countA < countB
        }).mkString(" ")

        print(missingNumbers)
    }
}

