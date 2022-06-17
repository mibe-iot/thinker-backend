package com.mibe.iot.thinker.app.hooks.to.web.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minItems

class DeviceHooksAndReportTypesModel(
    val hookIds: List<String>,
    val reportTypes: List<String>
) {
    init {
        Validation<DeviceHooksAndReportTypesModel> {
            DeviceHooksAndReportTypesModel::hookIds required {
                minItems(1) hint "validation.deviceHooksActions.hooks.must.not.be.empty"
            }
            DeviceHooksAndReportTypesModel::reportTypes required {
                minItems(1) hint "validation.deviceHooksActions.report.types.must.not.be.empty"
            }
        }.validate(this)
    }
}