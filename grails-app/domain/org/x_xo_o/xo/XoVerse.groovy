package org.x_xo_o.xo

import grails.rest.Resource
import org.bson.types.ObjectId

@Resource(uri='/xoverses', formats=['json'], superClass = XoRestfulController)
class XoVerse {

    static final XOVERSE = 'xoverse'

    ObjectId id
    String name
    String description

    // This back reference feels right
    static belongsTo = [xoSystem : XoSystem]

    static constraints = {
        name nullable: false, unique: true
        description nullable: true
    }
}
