import java.util.Map;

import groovy.json.*

void addPairToMap (Map m, String source, String target){

	assert source !=target
	m.each {

		if (it.value in List ){
			it.value.each{
				assert it in Map
			//	println "In LIST it: $it source $source target $target"

				addPairToMap(it, source, target)
			}
		}else{

			if (it.value == target){
				println "*******************************************skipping it.value ${it.value} source $source target $target"
			} else {

				if (it.value ==source) {

					if (m.children){
						m.children << ["name": target]
					}else{

						m  << ["name": it.value, "children": [["name" : target]]]
					}
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

wordpairs.each {wordPair ->
	def src = wordPair[0]
	def target = wordPair[1]

	if (tree.isEmpty()){
		tree <<
				[name: src,
					children: [[name: target]]]
		println "tree at start $tree"
	}
	else {
		addPairToMap(tree, src, target)
	}
}

println "tree at end $tree"
def jsonTree = new JsonBuilder(tree)
println "json1 $jsonTree"
println JsonOutput.prettyPrint(jsonTree.toString())

