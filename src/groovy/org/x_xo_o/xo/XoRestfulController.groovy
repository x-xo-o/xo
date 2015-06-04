package org.x_xo_o.xo

import grails.rest.RestfulController

class XoRestfulController<T> extends RestfulController<T> {

    static final DEFAULT_LIST_LIMIT = 1000

    XoRestfulController(Class<T> domainClass) {
        this(domainClass, false)
    }

    XoRestfulController(Class<T> domainClass, boolean readOnly) {
        super(domainClass, readOnly)
    }

    @Override
    def index(Integer limit) {
        params.max = Math.min(limit ?: DEFAULT_LIST_LIMIT, DEFAULT_LIST_LIMIT)
        respond listAllResources(params)
    }
}
