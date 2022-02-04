package com.mibe.iot.thinker.device.adapter.to.web.model.assembler.exception

import com.mibe.iot.thinker.web.error.InternationalizedException

/**
 * Invalid device report id exception should be thrown when persisted device report was retrieved without id
 *
 * @param messageKey the key of the message
 */
class DeviceReportIdNotPresentException(messageKey: String = "device.report.id.invalid") :
    InternationalizedException(messageKey)
