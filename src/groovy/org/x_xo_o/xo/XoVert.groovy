package org.x_xo_o.xo

import grails.validation.Validateable

@Validateable
class XoVert {

    static final XOSKELETON_XOVERSES_NULL = 'xoSkeleton.xoVerses.nullable'
    static final XOVERSE_XOSKELETON_RELATION_INVALID = 'xoVerse<-xoSkeleton.xoVerses.invalid'
    static final XOSYSTEM_XOVERSE_RELATION_INVALID = 'xoSystem<-xoVerse.invalid'

    String xoId
    XoSystem xoSystem
    XoVerse xoVerse
    XoSkeleton xoSkeleton

    static constraints = {
        xoId nullable: true
        xoSkeleton nullable: false, validator: { val, obj ->
            if (!obj.xoSkeleton?.xoVerses) {
                return [XOSKELETON_XOVERSES_NULL]
            }
        }
        xoVerse nullable: false, validator: { val, obj ->
            if (obj.xoSkeleton?.xoVerses && !obj.xoSkeleton.xoVerses.contains(obj.xoVerse)) {
                return [XOVERSE_XOSKELETON_RELATION_INVALID]
            }
        }
        xoSystem nullable: false, validator: { val, obj ->
            if (obj.xoVerse?.xoSystem != obj.xoSystem) {
                return [XOSYSTEM_XOVERSE_RELATION_INVALID]
            }
        }
    }
}
