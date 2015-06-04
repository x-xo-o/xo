package org.x_xo_o.xo

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.commons.spring.GrailsWebApplicationContext
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

@Build([XoSkeleton, XoVerse, XoSystem])
@TestFor(XoVertService)
class XoVertServiceSpec extends Specification {

    def 'delegate gracefully rejects null xoVert input'() {

        when:
        service.delegate(null, null, null)

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertService.NULL_XOVERT_MESSAGE)
    }

    def 'delegate gracefully rejects null request input'() {

        when:
        service.delegate(new XoVert(), null, null)

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertService.NULL_REQUEST_MESSAGE)
    }

    def 'delegate will fail gracefully if resourceService bean cannot be found'() {

        given:
        // Is this an indication that we need a better mock for Mongo backed domains with dynamic properties?
        def resourceService = 'resourceService'
        XoSystem.metaClass.resourceService = 'resourceService'

        def xoVert = new XoVert(xoSystem: XoSystem.build())
        def request = new MockHttpServletRequest()

        when:
        service.delegate(xoVert, request, null)

        then:
        def noSuchBean = thrown(NoSuchBeanDefinitionException)
        noSuchBean.beanName == resourceService
    }

    def 'delegate will look up the resourceService and invoke the request.method'() {

        given:
        // Is this an indication that we need a better mock for Mongo backed domains with dynamic properties?
        def resourceService = 'resourceService'
        XoSystem.metaClass.resourceService = 'resourceService'


        def beanDefinition = new GenericBeanDefinition()
        beanDefinition.beanClass = FakeXoResourceService
        (grailsApplication.mainContext as GrailsWebApplicationContext).registerBeanDefinition(resourceService, beanDefinition)

        def xoVert = new XoVert(xoSystem: XoSystem.build())
        def request = new MockHttpServletRequest()
        request.method = 'GET'

        when:
        def response = service.delegate(xoVert, request, null)

        then:
        response
        response.status == 200
    }

}

class FakeXoResourceService {

    // I think this might be an indication we really need to be implementing an interface

    def get(xoVert, request, params) {
        def response = new MockHttpServletResponse()
        response.status = 200
        response
    }

}
