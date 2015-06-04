package org.x_xo_o.xo

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import static ConstraintValidator.*

@Build([XoVerse, XoSystem])
@TestFor(XoVerse)
class XoVerseSpec extends Specification {

    def 'an XoVerse that satisfies all constraints is valid'() {

        expect:
        XoVerse.build().validate()
    }

    @Unroll("constraints #field")
    def 'validation constraints enforced'() {

        given:
        def xoVerse = XoVerse.build()

        when:
        xoVerse."$field" = value

        then:
        validateConstraints(xoVerse, field, error)

        where:
        field           | value | error
        'name'          | null  | ConstraintValidator.NULLABLE
        'description'   | null  | ConstraintValidator.NONE
    }

    def 'uniqueness constraints enforced'() {

        given:
        def xoSystem = XoSystem.build()
        // Without this flush, the unique constraint will not fire
        def xoVerseA = XoVerse.build(name: 'xoVerseA', xoSystem: xoSystem).save(flush: true)

        when:
        def xoVerseB = XoVerse.build(xoSystem: xoSystem)
        xoVerseB.name = xoVerseA.name

        then:
        validateConstraints(xoVerseB, 'name', ConstraintValidator.UNIQUE)
    }

}
