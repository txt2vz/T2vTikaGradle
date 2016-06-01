import groovy.json.*


Object wordPairsToParentChild(Object c, String source, String target ) {
	//println "i am in map"
	//map.get( key ) ?: map.findResult { k, v -> if( v in Map ) v.findDeepKey( key ) }

	//if (c in Map ){

	assert c in Map
	c.each {
		if (it.key == target){
			println "skipping it.key ${it.key} source $source target $target"
		}else
		{

			//skip if not
			if (it.key == source){
				it.value << target
			}else {
				println "it.value " + it.value + " class " + it.value.class
				def r = it.value.collect {le ->
					if (le in Map){
						println "map found &&&& le $le"
						def ff = wordPairsToParentChild (le, source, target)
						println "from recursive call ff $ff"
						ff
					}

					if (source == le){
						println "** source $source le $le"
						[(le) : [target]]
					} 	else le

				}
				println "after loop it.value " + it.value + " r $r"
				//}

				it.value = r

			}
		}
	}
}


// if dest already parent skip
// if findkey in tree add child
//else if find value at children[]
//



def wordpairs =[
	["a", "b"],
	["a", "c"],
	["c", "d"],
	["d", "a"],
	["c", "x"],
	["x", "p"],
	["x", "yes"],
	["x", "zed"]
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

