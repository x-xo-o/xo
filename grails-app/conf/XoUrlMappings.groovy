class XoUrlMappings {

    static mappings = {

        '/xosystems'(resources: 'xoSystem') {
            '/xoverses'(resources: 'xoVerse') {
                '/xoskeletons'(resources: 'xoSkeleton') {
                    '/xoverts' (controller: 'xoVert')
                }
            }
        }
        '/xoskeletons'(resources:'xoSkeleton')
    }
}
