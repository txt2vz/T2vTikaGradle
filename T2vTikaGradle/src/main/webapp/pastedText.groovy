def s = params.get("s")
//System.out.println(" s in pasted text: $s");

def gjp = new GetJSONpairs();
out.print(gjp.getWordPairs(s))