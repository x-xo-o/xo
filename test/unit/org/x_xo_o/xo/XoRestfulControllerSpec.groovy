package org.x_xo_o.xo

import grails.artefact.Artefact
import grails.buildtestdata.mixin.Build
import grails.persistence.Entity
import grails.rest.Resource
import grails.test.mixin.TestFor
import spock.lang.Specification

@Build(XoResource)
@TestFor(XoResourceController)
class XoRestfulControllerSpec extends Specification {

    def 'defaults the limit (as max) parameter'() {

        given:
        XoResource.build().save(failOnError: true, flush: true)

        when:
        controller.index()

        then:
        params.max == XoRestfulController.DEFAULT_LIST_LIMIT
    }

    def 'sets the limit (as max) parameter'() {

        when:
        controller.index(5)

        then:
        params.max == 5
    }

}

@Entity
@Resource
class XoResource {

    String name
    Integer numberOfUnits

    static constraints = {
        name nullable: false
        numberOfUnits nullable: true
    }
}

@Artefact("Controller")
class XoResourceController extends XoRestfulController<XoResource> {

    static responseFormats = ['json']

    XoResourceController() {
        super(XoResource)
    }

}
