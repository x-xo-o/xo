package org.x_xo_o.xo

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import static ConstraintValidator.*

@Build(XoSkeleton)
@TestFor(XoSkeleton)
class XoSkeletonSpec extends Specification {

    def 'an xoSkeleton that satisfies all constraints is valid'() {

        expect:
        XoSkeleton.build().validate()
    }

    @Unroll('constraints #field')
    def 'validation constraints enforced'() {

        given:
        def xoSkeleton = XoSkeleton.build()

        when:
        xoSkeleton."$field" = value

        then:
        validateConstraints(xoSkeleton, field, error)

        where:
        field           | value                 | error
        'name'          | null                  | ConstraintValidator.NULLABLE
        'description'   | null                  | ConstraintValidator.NONE
        'path'          | null                  | ConstraintValidator.NONE
    }
}
