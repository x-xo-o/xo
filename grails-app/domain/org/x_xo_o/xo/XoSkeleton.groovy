package org.x_xo_o.xo

import grails.rest.Resource
import org.bson.types.ObjectId

@Resource(uri='/xoskeletons', formats=['json'], superClass = XoRestfulController)
class XoSkeleton {

    static final XOSKELETON = 'xoskeleton'

    ObjectId id
    String name
    String description
    String path

    static belongsTo = XoVerse
    static hasMany = [ xoVerses : XoVerse ]

    static constraints = {
        name nullable: false
        description nullable: true
        path nullable: true
    }

}
