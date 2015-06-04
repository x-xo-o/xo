package org.x_xo_o.xo

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import static ConstraintValidator.*

@Build(XoSystem)
@TestFor(XoSystem)
class XoSystemSpec extends Specification {

    def 'an XoSystem that satisfies all constraints is valid'() {

        expect:
        XoSystem.build().validate()
    }

    @Unroll('constraints #field')
    def 'validation constraints enforced'() {

        given:
        def xoSystem = XoSystem.build()

        when:
        xoSystem."$field" = value

        then:
        validateConstraints(xoSystem, field, error)

        where:
        field           | value                 | error
        'iri'           | null                  | ConstraintValidator.NULLABLE
        'name'          | null                  | ConstraintValidator.NULLABLE
        'description'   | null                  | ConstraintValidator.NONE
    }

    def 'uniqueness constraints enforced'() {

        given:
        // Without this flush, the unique constraint will not fire
        def xoSystemA = XoSystem.build(name: 'xoSystemA', iri: 'existingIri').save(flush: true)

        when:
        def xoSystemB = XoSystem.build()
        xoSystemB.name = xoSystemA.name
        xoSystemB.iri = xoSystemA.iri

        then:
        validateConstraints(xoSystemB, 'name', ConstraintValidator.UNIQUE)
        validateConstraints(xoSystemB, 'iri', ConstraintValidator.UNIQUE)
    }
}
