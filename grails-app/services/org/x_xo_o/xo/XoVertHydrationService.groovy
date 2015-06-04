package org.x_xo_o.xo

import grails.transaction.Transactional

@Transactional
class XoVertHydrationService {

    static final NULL_MESSAGE = 'XoVertCommand cannot be null'
    static final MALFORMED_MESSAGE = 'XoVertCommand is malformed'
    static final INVALID_XOSKELETON_MESSAGE = 'XoVertCommand XoSkeletonId is invalid'
    static final INVALID_XOVERSE_MESSAGE = 'XoVertCommand XoVerseId is invalid'
    static final INVALID_XOSYSTEM_MESSAGE = 'XoVertCommand XoSystemId is invalid'
    static final INVALID_XOSKELETON_XOVERSE_MESSAGE = 'XoVertCommand xoSkeleton->xoVerse relation is not valid'
    static final INVALID_XOVERSE_XOSYSTEM_MESSAGE = 'XoVertCommand xoVerse->xoSystem relation is not valid'

    static transactional = false

    def hydrate(XoVertCommand xoVertCommand) {

        assert xoVertCommand, NULL_MESSAGE
        assert xoVertCommand.validate(), MALFORMED_MESSAGE

        // This is pretty naive; I am sure it can be optimized to enforce the validator
        def xoSkeleton = XoSkeleton.get(xoVertCommand.xoSkeletonId)
        assert xoSkeleton, INVALID_XOSKELETON_MESSAGE

        def xoVerse = XoVerse.get(xoVertCommand.xoVerseId)
        assert xoVerse, INVALID_XOVERSE_MESSAGE

        def xoSystem = XoSystem.get(xoVertCommand.xoSystemId)
        assert xoSystem, INVALID_XOSYSTEM_MESSAGE

        assert xoSkeleton.xoVerses?.contains(xoVerse), INVALID_XOSKELETON_XOVERSE_MESSAGE
        assert xoVerse?.xoSystem == xoSystem, INVALID_XOVERSE_XOSYSTEM_MESSAGE
        new XoVert(xoId: xoVertCommand.xoId, xoSkeleton: xoSkeleton, xoVerse: xoVerse, xoSystem: xoSystem)
    }
}
