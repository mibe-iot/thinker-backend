package com.mibe.iot.thinker.app.settings.exception

/**
 * @constructor templateMessages - list of pairs dataPath and error description
 */
class InvalidAppSettingsException(val errorDescriptions: List<Pair<String, String>>) :
    Exception(errorDescriptions.toString())