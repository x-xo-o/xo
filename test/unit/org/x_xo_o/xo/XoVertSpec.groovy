package org.x_xo_o.xo

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll

import static ConstraintValidator.*

@Build([XoSkeleton,XoVerse,XoSystem])
@TestMixin(GrailsUnitTestMixin)
class XoVertSpec extends Specification {

    def 'an empty XoVert is invalid'() {

        given:
        def xoVert = new XoVert()

        expect:
        !xoVert.validate()
    }

    @Unroll("constraints #field")
    def 'validation constraints enforced'() {

        given:
        def xoSystem = XoSystem.build()
        def xoVerse = XoVerse.build(xoSystem: xoSystem)
        def xoSkeleton = XoSkeleton.build(xoVerses: [xoVerse])
        def xoVert = new XoVert(xoId: 'xoId', xoSkeleton: xoSkeleton, xoVerse: xoVerse, xoSystem: xoSystem)

        when:
        xoVert."$field" = value

        then:
        validateConstraints(xoVert, field, error)

        where:
        field           | value                 | error
        'xoId'          | null                  | ConstraintValidator.NONE
        'xoSkeleton'    | null                  | ConstraintValidator.NULLABLE
        'xoSkeleton'    | XoSkeleton.build()    | XoVert.XOSKELETON_XOVERSES_NULL
        'xoVerse'       | null                  | ConstraintValidator.NULLABLE
        'xoVerse'       | XoVerse.build()       | XoVert.XOVERSE_XOSKELETON_RELATION_INVALID
        'xoSystem'      | null                  | ConstraintValidator.NULLABLE
        'xoSystem'      | XoSystem.build()      | XoVert.XOSYSTEM_XOVERSE_RELATION_INVALID
    }

}
