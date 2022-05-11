package com.mibe.iot.thinker.app.messaging.email

import org.springframework.context.ApplicationEvent

class EmailSettingsUpdatedEvent(source: Any): ApplicationEvent(source)