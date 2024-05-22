package algorithms

import utils.test

fun main() = test(
    testData = listOf(
        ("barfoothefoobarman" to arrayOf("foo", "bar")) to listOf(0, 9),
        ("wordgoodgoodgoodbestword" to arrayOf("word", "good", "best", "word")) to listOf(),
        ("barfoofoobarthefoobarman" to arrayOf("bar", "foo", "the")) to listOf(6, 9, 12),
        ("pjzkrkevzztxductzzxmxsvwjkxpvukmfjywwetvfnujhweiybwvvsrfequzkhossmootkmyxgjgfordrpapjuunmqnxxdrqrfgkrsjqbszgiqlcfnrpjlcwdrvbumtotzylshdvccdmsqoadfrpsvnwpizlwszrtyclhgilklydbmfhuywotjmktnwrfvizvnmfvvqfiokkdprznnnjycttprkxpuykhmpchiksyucbmtabiqkisgbhxngmhezrrqvayfsxauampdpxtafniiwfvdufhtwajrbkxtjzqjnfocdhekumttuqwovfjrgulhekcpjszyynadxhnttgmnxkduqmmyhzfnjhducesctufqbumxbamalqudeibljgbspeotkgvddcwgxidaiqcvgwykhbysjzlzfbupkqunuqtraxrlptivshhbihtsigtpipguhbhctcvubnhqipncyxfjebdnjyetnlnvmuxhzsdahkrscewabejifmxombiamxvauuitoltyymsarqcuuoezcbqpdaprxmsrickwpgwpsoplhugbikbkotzrtqkscekkgwjycfnvwfgdzogjzjvpcvixnsqsxacfwndzvrwrycwxrcismdhqapoojegggkocyrdtkzmiekhxoppctytvphjynrhtcvxcobxbcjjivtfjiwmduhzjokkbctweqtigwfhzorjlkpuuliaipbtfldinyetoybvugevwvhhhweejogrghllsouipabfafcxnhukcbtmxzshoyyufjhzadhrelweszbfgwpkzlwxkogyogutscvuhcllphshivnoteztpxsaoaacgxyaztuixhunrowzljqfqrahosheukhahhbiaxqzfmmwcjxountkevsvpbzjnilwpoermxrtlfroqoclexxisrdhvfsindffslyekrzwzqkpeocilatftymodgztjgybtyheqgcpwogdcjlnlesefgvimwbxcbzvaibspdjnrpqtyeilkcspknyylbwndvkffmzuriilxagyerjptbgeqgebiaqnvdubrtxibhvakcyotkfonmseszhczapxdlauexehhaireihxsplgdgmxfvaevrbadbwjbdrkfbbjjkgcztkcbwagtcnrtqryuqixtzhaakjlurnumzyovawrcjiwabuwretmdamfkxrgqgcdgbrdbnugzecbgyxxdqmisaqcyjkqrntxqmdrczxbebemcblftxplafnyoxqimkhcykwamvdsxjezkpgdpvopddptdfbprjustquhlazkjfluxrzopqdstulybnqvyknrchbphcarknnhhovweaqawdyxsqsqahkepluypwrzjegqtdoxfgzdkydeoxvrfhxusrujnmjzqrrlxglcmkiykldbiasnhrjbjekystzilrwkzhontwmehrfsrzfaqrbbxncphbzuuxeteshyrveamjsfiaharkcqxefghgceeixkdgkuboupxnwhnfigpkwnqdvzlydpidcljmflbccarbiegsmweklwngvygbqpescpeichmfidgsjmkvkofvkuehsmkkbocgejoiqcnafvuokelwuqsgkyoekaroptuvekfvmtxtqshcwsztkrzwrpabqrrhnlerxjojemcxel" to arrayOf("dhvf","sind","ffsl","yekr","zwzq","kpeo","cila","tfty","modg","ztjg","ybty","heqg","cpwo","gdcj","lnle","sefg","vimw","bxcb")) to listOf(935)
    ),
    inputToString = { (s, words) ->
        (s to words.toList()).toString()
    },
    testFunctionExecution = { (s, words) ->
        findSubstring(s, words)
    },
)

fun findSubstring(s: String, words: Array<String>): List<Int> {
    val totalLength = words.first().length * words.size
    val length = words[0].length

    var start = 0
    var end = totalLength - 1

    val result = mutableListOf<Int>()
    val map = hashMapOf<String, Int>()
    words.forEach { word ->
        map[word] = map.getOrDefault(word, 0) + 1
    }
    while (end <= s.lastIndex) {
        checkWindow(s, start, end, HashMap(map), length, result)
        start++
        end++
    }
    return result
}

private fun checkWindow(s: String, start: Int, end: Int, map: MutableMap<String, Int>, length: Int, result: MutableList<Int>) {
    var l = start
    var r = l + length - 1

    while (r <= end) {
        val current = s.substring(l, r + 1)
        if (map[current] != null && map[current]!! > 0) {
            map[current] = map[current]!! - 1
        } else {
            return
        }

        l += length
        r += length
    }

    result.add(start)
}

// too long operation
private fun getAllPermutations(words: Array<String>, mask: Int, dp: MutableMap<Int, Set<String>>): Set<String> {
    if (dp[mask] != null) {
        println("taken from cache, size: ${dp.size}")
        return dp[mask]!!
    }

    val result = mutableSetOf<String>()

    for (i in words.indices) {
        val itemMask = 0b1 shl i
        if (mask and itemMask == 0b0) {
            val current = words[i]
            val tails = getAllPermutations(words, itemMask or mask, dp)
            if (tails.isEmpty()) {
                result.add(current)
            } else {
                tails.forEach { tail ->
                    result.add(current + tail)
                }
            }

            words[i] = current
        }
    }

    dp[mask] = result
    return dp[mask]!!
}