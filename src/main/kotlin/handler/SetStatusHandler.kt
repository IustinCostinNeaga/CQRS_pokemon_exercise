package org.example.handler

import org.example.Command.SetStatus
import org.example.Event.MoveEvent
import org.example.Event.MoveEventError
import org.example.Event.MoveEventStatusChange

class SetStatusHandler: Handler<SetStatus> {
    override fun handle(command: SetStatus): MoveEvent {
        if(command.target.status == null) {
            command.target.status = command.status
            println("${command.target.name} was burned")
            return MoveEventStatusChange(command.status)
        }
        return MoveEventError("Status already set on Pokemon")
    }
}