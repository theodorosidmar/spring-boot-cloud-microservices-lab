package com.theodorosidmar.authservice.utils

import org.apache.commons.codec.binary.Base64
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.util.SerializationUtils

object SerializableObjectConverter {
    fun serialize(`object`: OAuth2Authentication?): String? =
            Base64.encodeBase64String(SerializationUtils.serialize(`object`))

    fun deserialize(encodedObject: String?): OAuth2Authentication? =
            SerializationUtils.deserialize(Base64.decodeBase64(encodedObject)) as OAuth2Authentication
}
