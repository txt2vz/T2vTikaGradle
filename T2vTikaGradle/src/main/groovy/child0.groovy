import groovy.json.*

class DeepFinder {
	static Object findDeepKey( Map map, Object keyin ) {
		//map.get( key ) ?: map.findResult { k, v -> if( v in Map ) v.findDeepKey( key ) }
		map.find{ it.key ==keyin } ?: map.findResult { k, v -> if( v in Map ) v.findDeepKey( keyin ) }
	}

	static Object findDeepVal( Map map, Object val ) {
		//	map.find {it.value ==val}?.key  ?: map.findResult { k, v -> if( v in Map ) v.findDeepVal( val ) }
		map.find {it.value ==val}  ?: map.findResult { k, v -> if( v in Map ) v.findDeepVal( val ) }
		//map.get( key ) ?: map.findResult { k, v -> if( v in Map ) v.findDeep( key ) }
	}
}

// if dest already parent skip
// if findkey in tree add child
//else if find value at children[]
//



def wordpairs =[["a", "b"], ["a", "c"], ["c", "d"], ["d", "a"]]

//wordPairList take 5: [WordPair(hous, content, 13.73428), WordPair(hous, tonight, 8.34772611), WordPair(content, tonight, 4.9051), WordPair(hous, zoo, 4.21814565), WordPair(hous, zoo2, 3.482520489)]
def tree = [:]
def p = [1, 3, 6]
println "wordpairs $wordpairs"

Map map = [a:"a",school:[id:'schoolID', table:'_school',
		children:[team:[id:'teamID',table:'_team',
				children:[player:[id7:'xx',table:'_roster']]
			]]
	]]


use( DeepFinder ) {
	println  "map before $map"
	println "33333 " + map.findDeepKey( 'id7' )
	println " vlsss " + map.findDeepVal('pp')
	println " jjjjj " + wordpairs[0][0]

	def a = "a"
	println " zzzzz  " + map.findDeepVal(a)   //(wordpairs[0][0]))

	//map.findDeepVal( 'xx' ).setValue("yos": "pos")

	def g = "xx"
	map.findDeepVal( g ).setValue("tt777")

	//map.findDeepVal( 'xx' ).setValue("tt777")
	println  "map aftter $map"

}

use (DeepFinder){
	def links2=
			[
				wordpairs.collect{
					if (tree.isEmpty()){

						//tree << [name: it[0]]
						//tree << [children : [(it[1])]]
						tree << [ (it[0]) : [(it[1])]]
					}

					else

					if (tree.findDeepKey (it[1])){
						println "skipping it1 " + it [1]
					}

					else
						//if (tree.findDeepKey (it[0]))
					{
						def x = tree.findDeepKey (it[0])
						println "searching for " + it[0]
						println " x $x"
						if (x!=null){

							println "x value " + x.value
							x.value << it[1]
						}
						//	else {
						def y = tree.findDeepVal(it[0])
						println "searching for y " + y  + " it0 is  " + it[0] + " tree is $tree"
						if (y!=null){
							println " y valeu " + y.value
						}
						//	}

						//x?.setValue(x?.key,x?.value << it[1])
						//x?.setValue(x?.value << it[1])

						//}
						//
						//}

					}
				}
			]
}

//println "links2 $links2"
println "tree $tree"
def jsonl = new JsonBuilder(tree)
println JsonOutput.prettyPrint(jsonl.toString())

