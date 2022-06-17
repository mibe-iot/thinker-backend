package com.mibe.iot.thinker.unit.app.device

import com.mibe.iot.thinker.app.device.ControlDeviceActionService
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.service.device.GetDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceActionNotFoundException
import com.mibe.iot.thinker.service.device.port.ControlDeviceActionPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class ControlDeviceActionServiceTest : FreeSpec({
    "with mocked ports and use cases" - {
        val getDeviceUseCaseMock = mockk<GetDeviceUseCase>(relaxed = true)
        val controlDeviceActionPortMock = mockk<ControlDeviceActionPort>(relaxed = true)
        val service = ControlDeviceActionService(getDeviceUseCaseMock, controlDeviceActionPortMock)

        "invokeAction(deviceId, actionName)" - {
            val deviceId = "1"
            val actionName = "some action"
            val otherActionName = "other action"

            "fails" - {
                "and should throw DeviceActionNotFoundException" - {
                    "when action not found by name" - {
                        coEvery { getDeviceUseCaseMock.getDeviceActions(deviceId) } returns flowOf(
                            DeviceAction(otherActionName + "1"),
                            DeviceAction(otherActionName + "2")
                        )
                        val exception = shouldThrow<DeviceActionNotFoundException> {
                            service.invokeAction(deviceId, actionName)
                        }
                        exception.deviceId shouldBe deviceId
                        exception.actionName shouldBe actionName

                        coVerifyAll {
                            getDeviceUseCaseMock.getDeviceActions(deviceId)
                            controlDeviceActionPortMock wasNot Called
                        }
                    }
                }
            }
            "succeeds" - {
                "and should call controlDeviceActionPort once" - {
                    val deviceAction = DeviceAction(actionName)
                    "when action found by name" - {
                        coEvery { getDeviceUseCaseMock.getDeviceActions(deviceId) } returns flowOf(
                            deviceAction,
                            DeviceAction(otherActionName + "1"),
                            DeviceAction(otherActionName + "2")
                        )

                        service.invokeAction(deviceId, actionName)

                    }
                    coVerifyAll {
                        getDeviceUseCaseMock.getDeviceActions(deviceId)
                        controlDeviceActionPortMock.activateAction(deviceId, deviceAction)
                    }
                }
            }
        }
        confirmVerified(getDeviceUseCaseMock, controlDeviceActionPortMock)
    }
})