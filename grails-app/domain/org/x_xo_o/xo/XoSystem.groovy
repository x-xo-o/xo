package org.x_xo_o.xo

import grails.rest.Resource
import org.bson.types.ObjectId

@Resource(uri='/xosystems', formats=['json'], superClass = XoRestfulController)
class XoSystem {

    static final XO = 'xo'

    ObjectId id
    String name
    String iri
    String description

    static hasMany = [xoVerses: XoVerse]

    static constraints = {

        name nullable: false, unique: true
        iri nullable: false, unique: true
        description nullable: true
    }
}
