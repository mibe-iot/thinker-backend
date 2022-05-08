package com.mibe.iot.thinker.unit.app.hooks

import com.mibe.iot.thinker.app.hooks.HookExecutorService
import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.service.hooks.HookExecutor
import com.mibe.iot.thinker.service.hooks.exception.MatchingExecutorNotFoundException
import com.mibe.iot.thinker.service.hooks.exception.HookNotFoundException
import com.mibe.iot.thinker.service.hooks.port.HookPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class HookExecutorServiceTest : FreeSpec ({

    "with mocked hook executors list" - {
        val executor1 = mockk<HookExecutor>(relaxed = true)
        val executor2 = mockk<HookExecutor>(relaxed = true)
        val executors = listOf(executor1, executor2)
        val hookPort = mockk<HookPort>(relaxed = true)
        val service = HookExecutorService(executors, hookPort)
        afterTest { clearAllMocks() }
        "executeHookById" - {
            val hookId = "1"
            "fails" - {
                "and should throw HookNotFoundException" - {
                    "when hook not found by id" - {
                        every { hookPort.getHookById(any()) } returns null
                        val exception = shouldThrow<HookNotFoundException> {
                            service.executeHookById(hookId)
                        }

                        exception.hookId shouldBe hookId

                        verify(exactly = 1) { hookPort.getHookById(hookId) }
                    }
                }
                "and should throw MatchingExecutorNotFoundException" - {
                    "when hook executor not found for hook type" - {
                        val hookMock = mockk<HookAnotherSubclass>(relaxed = true)
                        every { hookPort.getHookById(any()) } returns hookMock
                        every { executor1.hookType } answers { HookSubclass::class }
                        every { executor2.hookType } answers { HookSubclass::class }

                        val exception = shouldThrow<MatchingExecutorNotFoundException> {
                            service.executeHookById(hookId)
                        }

                        exception.hookType shouldBe HookAnotherSubclass::class

                        verify(exactly = 1) { hookPort.getHookById(hookId) }
                    }
                }
            }
            "succeeds" - {
                "and should call port and executor" - {
                    val hookMock = mockk<HookSubclass>(relaxed = true)
                    every { hookPort.getHookById(any()) } returns hookMock
                    every { executor1.hookType } answers { HookAnotherSubclass::class }
                    every { executor2.hookType } answers { HookSubclass::class }

                    service.executeHookById(hookId)

                    verify(exactly = 1) { hookPort.getHookById(hookId) }
                    verify(exactly = 1) { executor2.executeHook(hookMock) }
                }
            }
        }
    }

}) {
    class HookSubclass() : Hook("2", "", "")
    class HookAnotherSubclass() : Hook("3", "", "")
}
