def s = params.get("s")
//System.out.println(" s in pasted text: $s");

//def gjp = new GetJSONpairs();
//out.print(gjp.getWordPairs(s))
//println "pasted text s $s"


def gwl = new GenerateWordLinks();

def r = gwl.getJSONnetwork(s)
//println "r $r"
//System.out.print("in pasted text r " + r)
//r
print r