package com.modernwebcourse.auction

class BidCommandTest extends spock.lang.Specification {

    def "first test"() {
        expect:
        name.size() == size

        where:
        name << ["Kirk", "Spock", "Scotty"]
        size << [4, 5, 6]

    }
}
