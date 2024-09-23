package org.example.handler

import org.example.Command.Command
import org.example.Event.MoveEvent

interface Handler<T: Command/*, U: Event*/> {
    fun handle(command: T): MoveEvent
}