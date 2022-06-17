package com.mibe.iot.thinker.app.hooks.executors

import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.SendEmailHook
import com.mibe.iot.thinker.service.hooks.HookExecutor
import com.mibe.iot.thinker.service.messaging.email.EmailUseCase
import mu.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class SendEmailHookExecutor(
    private val emailUseCase: EmailUseCase
) : HookExecutor {
    private val log = KotlinLogging.logger {}

    override val hookType: KClass<out Hook>
        get() = SendEmailHook::class

    override fun executeHook(hook: Hook, report: DeviceReport) {
        if (hook is SendEmailHook) {
            val emailBody = """We have got report with type ${report.reportType}. Report body:
                |${report.reportData}
            """.trimMargin()
            emailUseCase.sendEmail(hook.emailAddress, "Thinker report hook: " + report.reportType, emailBody)
        } else {
            log.error { "Unknown hook type: ${hook.type}" }
        }
    }
}