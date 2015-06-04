package org.x_xo_o.xo

import grails.validation.Validateable
import org.springframework.http.HttpStatus

class XoVertController {

    static responseFormats = ['json']

    // This is a restful controller, really :)
    def xoVertService // Or is the is xoVertService or the xoRoutingService

    def xoverts(XoVertCommand xoVertCommand) {

        if (!xoVertCommand.validate()) {
            respond status: HttpStatus.NOT_FOUND
            return
        }

        def xoVert = xoVertService.hydrate(xoVertCommand)

        if (!xoVert.validate()) {
            respond status: HttpStatus.NOT_FOUND
            return
        }

        params.limit = params.limit ?: 10000
        def response = xoVertService.delegate(xoVert, request, params)
        respond response
    }
}

@Validateable
class XoVertCommand {

    String xoId
    String xoSystemId
    String xoVerseId
    String xoSkeletonId

    static constraints = {
        xoId nullable: true
        xoSystemId nullable: false
        xoVerseId nullable: false
        xoSkeletonId  nullable: false
    }

}
