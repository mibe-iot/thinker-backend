package com.mibe.iot.thinker.validation.application

import com.mibe.iot.thinker.discovery.domain.validation.validateAddress
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid
import io.kotest.core.spec.style.FreeSpec

class ValidationsTest : FreeSpec({
    "validateAddress" - {
        "when address is correct" - {
            "should be valid" - {
                validateAddress shouldBeValid "AA:BB:CC:DD:EE:FF"
            }
        }
        "when incorrect address is" - {
            "too short" - {
                validateAddress shouldBeInvalid "A"
                validateAddress shouldBeInvalid "AA"
                validateAddress shouldBeInvalid "AA:A"
                validateAddress shouldBeInvalid "AA:AB"
                validateAddress shouldBeInvalid "AA:AB:"
                validateAddress shouldBeInvalid "AA:AB:B"
                validateAddress shouldBeInvalid "AA:AB:BB"
                validateAddress shouldBeInvalid "AA:AB:BB:"
                validateAddress shouldBeInvalid "AA:AB:BB:C"
                validateAddress shouldBeInvalid "AA:AB:BB:CC"
                validateAddress shouldBeInvalid "AA:AB:BB:CC:D"
                validateAddress shouldBeInvalid "AA:AB:BB:CC:DD"
                validateAddress shouldBeInvalid "AA:AB:BB:CC:DD:"
                validateAddress shouldBeInvalid "AA:AB:BB:CC:DD:E"
            }
            "too long" - {
                validateAddress shouldBeInvalid "AA:AB:BB:CC:DD:EEE"
                validateAddress shouldBeInvalid "AA:AB:BB:CC:DD:EE:"
                validateAddress shouldBeInvalid ":AA:AB:BB:CC:DD:EE"
            }
            "illegal start" - {
                validateAddress shouldBeInvalid ":AA:AB:BB:CC:DD:E"
                validateAddress shouldBeInvalid "aAA:AB:BB:CC:DD:E"
                validateAddress shouldBeInvalid "1AA:AB:BB:CC:DD:E"
                validateAddress shouldBeInvalid ":A:AB:BB:CC:DD:EE"
            }
            "illegal end" - {
                validateAddress shouldBeInvalid "AA:AB:BB:CC:DD:E:"
                validateAddress shouldBeInvalid "AA:AB:BB:CC:DD:EEa"
                validateAddress shouldBeInvalid "AA:AB:BB:CC:DD:EE1"
            }
            "illegal characters" - {
                validateAddress shouldBeInvalid "1A:AB:BB:CC:DD:E:"
                validateAddress shouldBeInvalid "AA:AB:BB:33:DD:EEa"
                validateAddress shouldBeInvalid "AA:A2:BB:CC:DD:EE1"
            }
            "wrong colons" - {
                validateAddress shouldBeInvalid "AABBCCDDEEFF"
                validateAddress shouldBeInvalid "AA:BBCCDDEEFF"
                validateAddress shouldBeInvalid "AA:BB:CCDDEEFF"
                validateAddress shouldBeInvalid "AA:BB:CC:DDEEFF"
                validateAddress shouldBeInvalid "AA:BB:CC:DD:EEFF"
                validateAddress shouldBeInvalid "AA:BB:CC:DD::EEFF"
            }
            "lowercase" - {
                validateAddress shouldBeValid "aa:bb:cc:dd:ee:ff"
            }
        }
    }
})
