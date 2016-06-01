import groovy.json.*

import static groovy.json.JsonParserType.LAX as RELAX

println "cnt"

def links=''' 
[ 
  {"source":"tintin","target":"herge","cooc":169.2},
  {"source":"herge","target":"adventures","cooc":151.6},
  {"source":"tintin","target":"snowy","cooc":153.7},
  {"source":"tintin","target":"haddock","cooc":138.9},
  {"source":"tintin","target":"adventures","cooc":138.3}
]
'''

def chld = '''
{
	"name": "tintin",
	"children": [{
		"name": "herge",
		"children": [{
			"name": "adventures"
		}]
	}, {
		"name" : "snowy"
	}, {
		"name" : "haddock"
	}]
}
'''

def json = new JsonSlurper().parseText(links)
println json[0]
println "linkds " +  json[2].target

def chldj = new JsonSlurper().parseText(chld)

//println chldj.collectNested {it}

def y = json.findAll{ it = "snowy"}
println "y $y"

json.each {pair ->   println pair.cooc}

def z =[:]


def json2 = new JsonBuilder()

json2.message {
	header {
		from('mrhaki')  // parenthesis are optional
		to 'Groovy Users', 'Java Users'
	}
	body "Check out Groovy's gr8 JSON support."
}

println JsonOutput.prettyPrint(json2.toString())
def p = [1, 3, 6]

def links2=
		[
	//		["source":"tintin","target":"herge","cooc":169.2],
		//	["source":"herge","target":"adventures","cooc":151.6],
	//		["source":"tintin","target":"snowy","cooc":153.7],
		//	["source":"tintin","target":"haddock","cooc":138.9],
	//		["source":"tintin","target":"adventures","cooc":138.3],
			p.collect{
				def rr
				//def rt=[:]
				// if rt.contains key it then rt[it.src] << [it.target]
				//else rt.add(it.src: [it.target]
				
				// if rt.isempty 
				
				 
				if (it < 4) rr=0 else rr = [first: "thez"]
				[dogs: rr]
			}
		]

println "links2 $links2"
def jsonl = new JsonBuilder(links2)
println JsonOutput.prettyPrint(jsonl.toString())

