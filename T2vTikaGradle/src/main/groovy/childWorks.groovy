import groovy.json.*


Object findDeepKey( Map map, String keyin ) {
	//println "i am in map"
	//map.get( key ) ?: map.findResult { k, v -> if( v in Map ) v.findDeepKey( key ) }
	map.find{ it.key ==keyin } ?: map.findResult { k, v ->
		if( v in Map ) {
			println "found a MAPPP"
			findDeepKey(map, keyin )
		}
		else if (v in List){
			println "looking for list v: $v"
			v.each{
				if (it in Map){
					println "relly MAPPP2"
				//	findDeepKey (map, keyin)
				}
			}
			if (v.find{it==keyin}) v
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


def wordpairs =[["a", "b"], ["a", "c"], ["c", "d"], ["d", "a"], ["c", "x"]]

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

					if (findDeepKey (tree, target)){
						println "skipping target $target"
					}
					else
					{
						println "tree is $tree"
						def found = findDeepKey (tree, src)

						if (found!=null){

							println "found: $found class " + found.class
							if (found in LinkedHashMap$Entry){
								println "found is linkhsash $found"
								found.value << target
							} else
							if (found in List){
								println "in xxxxx found $found"
								
								
								found.eachWithIndex { listValue, listIndex ->
									//if (listValue in Map) findDeepKey(listValue, src, target) else
									findAndReplace(listValue, listIndex, src, [(listValue): target], found)
								}

								/* def m = found.collect{
									println "in collect src: $src it: $it"
									if (it==src){
										println "it is src hhrr"
										[(it): target]
										//["yyyy": 3444]
									}
									else {
										it
										}					
									
								}
								 
								 println " at end found $found m is $m"
								 found = m
								 println "found is $found"
								*/
								//found << [(found) : target]
							}
							println " tree $tree"
							//if (found.value.contains(src))

							//def newList = []
							//println "searching map for src $src found:  $found found.value: " + found.value + " found.key " + found.key
							//found.value << target
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


//println "links2 $links2"
println "tree $tree"
def jsonl = new JsonBuilder(tree)
println JsonOutput.prettyPrint(jsonl.toString())

