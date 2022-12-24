package playground

class ReduceExample {
    fun getCountHashMapUsingMap(preferList: List<String>): MutableMap<String, Int> {
        var result = mutableMapOf<String, Int>()
        preferList.map { item ->
            var split = item.split(",")
            changeMapByPrefer(result, split[0], split[1])
        }
        return result
    }

    fun getCountHashMapUsingFold(preferList: List<String>): MutableMap<String, Int> {
        return preferList.fold(mutableMapOf()) { result, item ->
            val split = item.split(",")
            changeMapByPrefer(result, split[0], split[1])
        }
    }

    private fun changeMapByPrefer(
        map: MutableMap<String, Int>,
        key: String,
        prefer: String,
    ): MutableMap<String, Int> {
        return when (prefer) {
            "good" -> addOne(map, key)
            "bad" -> removeOne(map, key)
            else -> throw Exception("Unknown Prefer Type")
        }
    }

    private fun removeOne(
        result: MutableMap<String, Int>, key: String,
    ): MutableMap<String, Int> {
        result[key] = result[key]?.minus(1) ?: -1
        return result
    }

    private fun addOne(result: MutableMap<String, Int>, key: String): MutableMap<String, Int> {
        result[key] = result[key]?.plus(1) ?: 1
        return result
    }
}

fun main() {
    val preferList = listOf(
        "java,good",
        "kotlin,good",
        "c,bad",
        "c#,good",
        "c,bad",
        "c#,good",
        "kotlin,good",
        "java,bad",
        "python,good",
        "python,bad"
    )
    val sut = ReduceExample()

    val mapResult = sut.getCountHashMapUsingMap(preferList)
    println("mapResult = $mapResult")

    val foldResult = sut.getCountHashMapUsingFold(preferList)
    println("foldResult = $foldResult")
}
