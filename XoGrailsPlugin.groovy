import grails.plugins.rest.client.RestBuilder
import org.x_xo_o.xo.XoResourceService
import org.x_xo_o.xo.XoSkeleton
import org.x_xo_o.xo.XoSystem
import org.x_xo_o.xo.XoVerse

import grails.converters.JSON
import org.bson.types.ObjectId

class XoGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.5 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "xo" // Headline display name of the plugin
    def author = "durp00"
    def authorEmail = "durp00+x_xo_o@gmail.com"
    def description = '''\
xo :: Connect to All the Things
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/x-xo-o/xo"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
        JSON.registerObjectMarshaller(ObjectId) {
            it as String
        }
        beans {
            xoResourceService(XoResourceService)
            xoRestBuilder(RestBuilder)
        }
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
        def xoSystem = XoSystem.findByName(XoSystem.XO)
        if (!xoSystem) {
            xoSystem = new XoSystem(
                name: XoSystem.XO,
                iri: ctx.grailsLinkGenerator.serverBaseURL,
                description: 'the x and the o'
            ).save(failOnError: true, flush: true)
        }

        if (!XoVerse.findByName(XoVerse.XOVERSE)) {

            def xoVerse = new XoVerse(
                name: XoVerse.XOVERSE,
                description: 'the here and now',
                xoSystem: xoSystem,
            ).save(failOnError: true, flush: true)

            new XoSkeleton(
                id: new ObjectId(),
                name: XoSystem.XO,
                description: 'xo (skeleton)'
            ).addToXoVerses(xoVerse).save(failOnError: true, flush: true)

            new XoSkeleton(
                id: new ObjectId(),
                name: XoVerse.XOVERSE,
                description: 'xoverse (skeleton)',
                path: '/xoverses'
            ).addToXoVerses(xoVerse).save(failOnError: true, flush: true)

            new XoSkeleton(
                id: new ObjectId(),
                name: XoSkeleton.XOSKELETON,
                description: 'xoskeleton (skeleton)',
                path: '/xoskeletons'
            ).addToXoVerses(xoVerse).save(failOnError: true, flush: true)

        }
        XoSystem.count()

    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
