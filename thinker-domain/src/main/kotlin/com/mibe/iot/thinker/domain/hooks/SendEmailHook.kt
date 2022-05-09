package com.mibe.iot.thinker.domain.hooks

import com.mibe.iot.thinker.domain.data.EmailAddress

class SendEmailHook(
    id: String?,
    name: String,
    description: String,
    val emailAddress: EmailAddress
) : Hook(id, name, description, SEND_EMAIL)