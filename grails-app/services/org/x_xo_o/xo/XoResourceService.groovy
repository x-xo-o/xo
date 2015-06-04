package org.x_xo_o.xo

import grails.transaction.Transactional
import org.springframework.http.MediaType
import org.springframework.web.util.UriComponentsBuilder

@Transactional
class XoResourceService {

    static final JSON = MediaType.APPLICATION_JSON_VALUE

    def grailsApplication
    def xoRestBuilder

    /*
        This service can be used as the basis for other services, either through extension or injection
     */
    def get(XoVert xoVert, request, params) {

        // App name
        def appName = grailsApplication.metadata['app.name']

        // Points to api endpoint...
        def uriBuilder = UriComponentsBuilder.fromUriString([xoVert.xoSystem.iri,appName].findAll().join('/'))

        // Tip: using findAll eliminates nulls
        uriBuilder.path([xoVert.xoSkeleton.path, xoVert.xoId].findAll().join('/'))
        params.keySet().each { String key ->
            uriBuilder.queryParam(key, params[(key)] as String)
        }

        String uri = uriBuilder.build().encode().toUriString()
        def xoVertretung = xoRestBuilder.get(uri) {
            // auth xoVert.xoVerse.user.id, xoVert.xoVerse.user.password
            accept JSON
            contentType JSON
        }
        xoVertretung.json
    }


}
