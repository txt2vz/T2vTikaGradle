import groovy.json.*

class DeepFinder5 {
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

use (DeepFinder5){
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

						if (tree.findDeepKey (target)){
							println "skipping target $target"
						}
						else
						{
							println "tree is $tree"
							def found = tree.findDeepKey (src)
						
							if (found!=null){
								def newList = []
								println "searching map for src $src found:  $found found.value: " + found.value + " found.key " + found.key
								found.value << target
					/*			if (found.value in List ) {
									println "found: $found found.value is list " + found.value + " class x.value  " + found.value.class

									 newList = found.value.collect {
										if (it in Map){
											it.findResult { k, v ->
												println "recurse"
												if( v in Map ) v.findDeepKey( src )
											}
										}
										else  {
											println "src: $src it: $it "
											[it,  target]
										}
									}
									//println "newList: $newList"
								
								}	//newList
						*/		

							}
						}
					}
				}
			]
}

//println "links2 $links2"
println "tree $tree"
def jsonl = new JsonBuilder(tree)
println JsonOutput.prettyPrint(jsonl.toString())

