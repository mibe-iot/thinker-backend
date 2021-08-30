package com.mibe.iot.thinker.device.application.port.to.exception

import com.mibe.iot.thinker.web.error.InternationalizedException

/**
 * Invalid device id exception should be thrown when persisted device was retrieved without id
 *
 * @param messageKey the key of the message
 */
class InvalidDeviceIdException(messageKey: String = "device.id.invalid") : InternationalizedException(messageKey)
