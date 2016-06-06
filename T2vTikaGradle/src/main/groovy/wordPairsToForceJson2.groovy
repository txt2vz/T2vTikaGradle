import java.util.Map;

import groovy.json.*

void addPairToMap (Map m, String source, String target){

	m.each {

		if (it.value in List ){
			it.value.each{
				assert it in Map
				println "In LIST it: $it source $source target $target"

				addPairToMap(it, source, target)
			}
		}else{
			println ""
			println "in ELSE source $source target $target it  $it itvalue " + it.value

			if (it.value == target){
				println "*******************************************skipping it.value ${it.value} source $source target $target"
			} else {

				if (it.value ==source) {

					println "ELSE FOUND it: $it  it.value " + it.value + " source $source target $target"
					
					if (m.children){
						m.children << ["name": target]
						println "children found m[name] " + m["name"]  + " m.name " + m.name + " m.children " +m.children
						println "m after adding children $m"
					}else{
						println "no children m.name " + m.name + " m $m"
						m  << ["name": it.value, "children": [["name" : target]]]
					}

					//println "m[name] " + m["name"]  + " m.name " + m.name + " m.children " +m.children
					//m.name = ["name": it.value, "children": [["name": m.name],["name" : target]]]

					//println "m[name] AFTER " + m["name"]  + " m.name " + m.name + " it $it m $m"
					//println "after add it: $it  it.class " + it.class
					println ""
				}
			}
			//println ""
			//println "not list it $it and it.value " + it.value
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
def jsonl = new JsonBuilder(tree)
println "json1 $jsonl"
println JsonOutput.prettyPrint(jsonl.toString())

