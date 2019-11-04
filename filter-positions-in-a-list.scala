def f(arr:List[Int]):List[Int] = {
    val oddIndexes = Range(0, arr.length).filter(_ % 2 == 1).toList
    oddIndexes.map(arr)
}