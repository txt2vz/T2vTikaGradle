import groovy.json.*

class DeepFinder3 {
	static Object findDeepKey( Map map, Object keyin ) {
		//println "i am in map"
		//map.get( key ) ?: map.findResult { k, v -> if( v in Map ) v.findDeepKey( key ) }
		map.find{ it.key ==keyin } ?: map.findResult { k, v -> if( v in Map ) v.findDeepKey( keyin ) }
	}
}

// if dest already parent skip
// if findkey in tree add child
//else if find value at children[]
//



def wordpairs =[["a", "b"], ["a", "c"], ["c", "d"], ["d", "a"]]

//wordPairList take 5: [WordPair(hous, content, 13.73428), WordPair(hous, tonight, 8.34772611), WordPair(content, tonight, 4.9051), WordPair(hous, zoo, 4.21814565), WordPair(hous, zoo2, 3.482520489)]
def tree = [:]

println "wordpairs $wordpairs"

use (DeepFinder3){
	def links2=
			[
				wordpairs.collect{
					if (tree.isEmpty()){
						tree << [ (it[0]) : [(it[1])]]
					}

					else
					if (tree.findDeepKey (it[1])){
						println "skipping it1 " + it [1]
					}

					else
					{
						def x = tree.findDeepKey (it[0])
						println "searching map for key value " + it[0]  + " found:  $x"

						if (x!=null){
							if (x.value in List ) println "xv vlaue ix list $x"
							println "x value before " + x.value
							x.value << it[1]
							println "x value after " + x.value
						}
					}
				}
			]
}

//println "links2 $links2"
println "tree $tree"
def jsonl = new JsonBuilder(tree)
println JsonOutput.prettyPrint(jsonl.toString())

