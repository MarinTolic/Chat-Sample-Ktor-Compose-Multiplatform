package hr.marintolic.chatsample.utils

import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Fetches a map of all local properties.
 *
 * @return A map containing all properties from 'rootDir/local.properties'.
 */
internal fun getLocalProperties(): Map<String, String> {
    val localProperties = Properties().apply {
        load(FileInputStream(File("./local.properties")))
    }

    return localProperties.stringPropertyNames().associateWith { propertyName ->
        localProperties.getProperty(propertyName)
    }
}