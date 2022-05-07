package com.mibe.iot.thinker.persistence.entity

import com.mibe.iot.thinker.domain.settings.SettingsType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("settings")
data class AppSettingsEntity(
    @Id
    var id: String? = null,
    var ssid: CharArray = CharArray(0),
    var password: CharArray = CharArray(0),
    val type: SettingsType
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppSettingsEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}