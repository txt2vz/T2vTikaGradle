def s = params.get("s")

def gwl = new processText.GenerateWordLinks();
def json = gwl.getJSONnetwork(s)
print json