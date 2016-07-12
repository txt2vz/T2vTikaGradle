import spock.lang.*

class GetPairsSpec extends spock.lang.Specification {
	def "word pairs produced correctly names"() {
		given:
		def gwl = new GenerateWordLinks()
		
		List<Integer> list = new ArrayList<>()
		when:
		list.add(1)
		gwl.getJSONnetwork("zzza ttttk ffffe")
		then:
		2 == list.get(0)
	}
}