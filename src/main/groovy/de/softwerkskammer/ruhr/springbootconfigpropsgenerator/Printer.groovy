package de.softwerkskammer.ruhr.springbootconfigpropsgenerator

import org.springframework.boot.configurationmetadata.ConfigurationMetadataGroup

class Printer {


    Collection generate(Collection metaDataProperties, String groupId = "") {
        return metaDataProperties.
                collect { prop ->
                    "| ${(groupId ? groupId + '.' : '') + prop.name} | $prop.type | ${prop.defaultValue?:''} | " +
                            "$prop.description" }
                .sort()
    }

    String generateTableForGroup(ConfigurationMetadataGroup metadataGroup) {
        def metaDataProperties  = metadataGroup.properties.values()
        def groupId = metadataGroup.id
        if (!metaDataProperties) {
            return "no properties found"
        }
        def dataRows = generate(metaDataProperties, groupId)

        List<String> rows = [
                '[options="header"]',
                '|=====',
                '| name | type | default value | description']
        rows.addAll(dataRows)
        rows.add('|=====')
        return rows.join('\n')
    }
}
