package org.x_xo_o.xo

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification

@Build([XoSkeleton, XoVerse, XoSystem])
@TestFor(XoVertHydrationService)
class XoVertHydrationServiceSpec extends Specification {

    def 'hydrate rejects null input gracefully'() {

        when:
        service.hydrate(null)

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertHydrationService.NULL_MESSAGE)
    }

    def 'hydrate rejects invalid input gracefully'() {

        when:
        service.hydrate(new XoVertCommand())

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertHydrationService.MALFORMED_MESSAGE)
    }

    def 'hydrate rejects an XoVertCommand that does not reference a valid XoSkeleton'() {

        given:
        def xoVertCommand = new XoVertCommand(xoSkeletonId: 'xosk', xoVerseId: 'xov', xoSystemId: 'xos')

        when:
        service.hydrate(xoVertCommand)

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertHydrationService.INVALID_XOSKELETON_MESSAGE)
    }

    def 'hydrate rejects an XoVertCommand that does not reference a valid XoVerse'() {

        given:
        def xosk = XoSkeleton.build()
        def xoVertCommand = new XoVertCommand(xoSkeletonId: xosk.id, xoVerseId: 'xov', xoSystemId: 'xos')

        when:
        service.hydrate(xoVertCommand)

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertHydrationService.INVALID_XOVERSE_MESSAGE)
    }

    def 'hydrate rejects an XoVertCommand that does not reference a valid XoSystem'() {

        given:
        def xosk = XoSkeleton.build()
        def xov = XoVerse.build()
        def xoVertCommand = new XoVertCommand(xoSkeletonId: xosk.id, xoVerseId: xov.id, xoSystemId: 'xos')

        when:
        service.hydrate(xoVertCommand)

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertHydrationService.INVALID_XOSYSTEM_MESSAGE)
    }

    def 'hydrate rejects a structurally valid XoVertCommand that has invalid xoSkeleton->xoVerse references'() {

        given:
        def xos = XoSystem.build()
        def xov = XoVerse.build()
        def xosk = XoSkeleton.build()
        def xoVertCommand = new XoVertCommand(xoSystemId: xos.id, xoVerseId: xov.id, xoSkeletonId: xosk.id)

        when:
        service.hydrate(xoVertCommand)

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertHydrationService.INVALID_XOSKELETON_XOVERSE_MESSAGE)
    }

    def 'hydrate rejects a structurally valid XoVertCommand that has invalid xoVerse->xoSystem references'() {

        given:
        def xos = XoSystem.build()
        def xov = XoVerse.build()
        def xosk = XoSkeleton.build()
        xosk.addToXoVerses(xov)
        def xoVertCommand = new XoVertCommand(xoSystemId: xos.id, xoVerseId: xov.id, xoSkeletonId: xosk.id)

        when:
        service.hydrate(xoVertCommand)

        then:
        def assertionError = thrown(AssertionError)
        assertionError.message.contains(XoVertHydrationService.INVALID_XOVERSE_XOSYSTEM_MESSAGE)
    }

    def 'hydrate creates an XoVert from a valid and consistent XoVertCommand'() {

        given:
        def xos = XoSystem.build()
        def xov = XoVerse.build()
        xov.xoSystem = xos
        def xosk = XoSkeleton.build()
        xosk.addToXoVerses(xov)
        def xoVertCommand = new XoVertCommand(xoSystemId: xos.id, xoVerseId: xov.id, xoSkeletonId: xosk.id)

        when:
        def xoVert = service.hydrate(xoVertCommand)

        then:
        xoVert
        xoVert.xoSystem == xos
        xoVert.xoVerse == xov
        xoVert.xoSkeleton == xosk
    }

}
