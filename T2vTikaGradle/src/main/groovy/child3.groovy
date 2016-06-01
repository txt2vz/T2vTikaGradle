import groovy.json.*

class DeepFinder4 {
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

Object f2( Map map, Collection pairIn ) {
	//println "i am in map"
	//map.get( key ) ?: map.findResult { k, v -> if( v in Map ) v.findDeepKey( key ) }

	//if dest already parent skip
	if (map.containsKey(pairIn[1] ))
	{
		println "skipping parin 1 " + pairIn[1]
		null;
	}

	else{
		def src =  map.find{
			it.key == pairIn[0]
		}

		if ( src) {
			println "src  $src srcvalue "+ src.value
			if (src.value in List)  //check the list
			{
				src.value.each{ listele ->
					if( listele in Map ) f2 (listele, pairIn )
					else if (listele instanceof String ) listele= [ (pairIn[0]) : pairIn[1] ]
				}
			}
		}

		// df.value   <<  pairIn[1]
		//else if (df.value in Map )
		//	else if (src.value instanceof String) src.value = "a strubg "// [ (pairIn[0]) : pairIn[1] ]
		else
			map.findResult { k, v ->
				if( v in Map ) f2 (map, v )

			}
		//}
	}
}

def links2=
		[
			wordpairs.collect{
				if (tree.isEmpty()){
					tree << [ (it[0]) : [(it[1])]]
				}

				else
					if (f2 (tree, it)){
					//println "skipping it1 " + it [1]
					tree = f2 (tree,it)
					}
			}

			//}
		]


//println "links2 $links2"
println "tree $tree"
def jsonl = new JsonBuilder(tree)
println JsonOutput.prettyPrint(jsonl.toString())

