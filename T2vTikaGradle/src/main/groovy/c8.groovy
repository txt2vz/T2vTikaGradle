import groovy.json.*


Map wordPairsToParentChild(Map map, String source, String target ) {

	map.each {mapElement ->
		if (mapElement.key == target){
			//skip
			println "skipping it.key ${mapElement.key} source $source target $target"
		}else {
			if (mapElement.key == source){
				mapElement.value << target
			}else {
				//println "it.value " + it.value + " class " + it.value.class
				assert mapElement.value in List
				mapElement.value = mapElement.value.collect {listElement ->
					if (listElement in Map){
						println "map found &&&& le $listElement"
						wordPairsToParentChild (listElement, source, target)
					}
					if (source == listElement){
						println "** source $source le $listElement"
						[(listElement) : [target]]
					} 	else
						listElement
				}
			}
		}
	}
}

def wordpairs =[
	["a", "b"],
	["a", "c"],
	["c", "d"],
	["d", "a"],
	["c", "x"],
	["x", "p"],
	["x", "yes"],
	["c", "n2"],
	["x", "zed"],
	["a", "last"]
]

//wordPairList take 5: [WordPair(hous, content, 13.73428), WordPair(hous, tonight, 8.34772611), WordPair(content, tonight, 4.9051), WordPair(hous, zoo, 4.21814565), WordPair(hous, zoo2, 3.482520489)]
def tree = [:]
println "wordpairs $wordpairs"

def jdata = [
	json : wordpairs.collect{ wordPair ->

		def src = wordPair[0]
		def target = wordPair[1]
		println "src $src target $target"

		if (tree.isEmpty()){

			tree << [ (src) : [target]]
			[name: src,
				children: [target]]

		}
		else {
			println " tree at start $tree"

			wordPairsToParentChild(tree, src, target)

			println "tree after $tree"
		}
	}
]

println "tree at end $tree"
def jsonl = new JsonBuilder(jdata)
println JsonOutput.prettyPrint(jsonl.toString())

