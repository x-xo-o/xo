package org.x_xo_o.xo

import javax.servlet.http.HttpServletRequest

class XoVertService {

    static final NULL_XOVERT_MESSAGE = 'XoVert must not be null'
    static final NULL_REQUEST_MESSAGE = 'request must not be null'

    static transactional = false

    def grailsApplication

    def delegate(xoVert, HttpServletRequest request, params) {

        // We are assuming pre-flighting of xoVert...
        assert xoVert, NULL_XOVERT_MESSAGE
        assert request, NULL_REQUEST_MESSAGE

        // This is negotiable, right now we are putting the resourceService on the xoSystem
        def resourceService = grailsApplication.mainContext.getBean(xoVert.xoSystem.resourceService as String)

        // In addition, we have already limited ourselves to the HTTP verbs at this time...
        // I thought about asserting for support via respondsTo, but there is no need
        // I never intended to supply caller with more info than a 405
        def method = request.method.toLowerCase()
        resourceService."${method}"(xoVert, request, params)
    }

}
