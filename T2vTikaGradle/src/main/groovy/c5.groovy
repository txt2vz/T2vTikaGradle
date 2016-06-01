import groovy.json.*


Object wordPairsToParentChild(Object c, String source, String target ) {
	//println "i am in map"
	//map.get( key ) ?: map.findResult { k, v -> if( v in Map ) v.findDeepKey( key ) }

	if (c in Map ){
		def res = c.get(source)
		if (res ){
			//should be a list
			res << target
		}
		else {
			println " not map c.values " + c.values() + " class c.values " + c.values().class
			def zz  = c.values().collectNested {
				println "****in c vlaues it is $it"
				"zzzzzzz"
			}
			println "zz $zz"
			zz
			
			//wordPairsToParentChild (c.values(), source, target)
		}
	} else
	if (c in List){
		println "c is $c"
		c=c.collectNested {
			if (it == source){
				println "setting source in list it $it target $target"
				//[it : target]
				"rrrrr"
				
			}
			else
				// not found in list
				if (it in Map){
					wordPairsToParentChild (it, source, target)
				}
		}
	}
}


// if dest already parent skip
// if findkey in tree add child
//else if find value at children[]
//
def findAndReplace(def listValue, def listIndex, def valueToCompare, def valueToReplace, def originalList) {
	if(listValue instanceof List) {
		listValue.eachWithIndex { insideListValue, insideListIndex ->
			findAndReplace(insideListValue, insideListIndex, valueToCompare, valueToReplace, listValue)
		}
	}else if(listValue == valueToCompare) {
		originalList[listIndex] = valueToReplace
	}
}


def wordpairs =[
	["a", "b"],
	["a", "c"],
	["c", "d"],
	["d", "a"],
	["c", "x"]
]

//wordPairList take 5: [WordPair(hous, content, 13.73428), WordPair(hous, tonight, 8.34772611), WordPair(content, tonight, 4.9051), WordPair(hous, zoo, 4.21814565), WordPair(hous, zoo2, 3.482520489)]
def tree = [:]
println "wordpairs $wordpairs"

def links2=
		[
			wordpairs.collect{ wordPair ->
				def src = wordPair[0]
				def target = wordPair[1]
				println "src $src target $target"

				if (tree.isEmpty()){
					tree << [ (src) : [target]]
				}
				else {
					println " tree at start $tree"

					wordPairsToParentChild(tree, src, target)

					println "tree after $tree"
				}
			}
		]


//println "links2 $links2"
println "tree at end $tree"
def jsonl = new JsonBuilder(tree)
println JsonOutput.prettyPrint(jsonl.toString())

