package tech.bobliu.assignment07.model

import java.util.*

data class Message(
    var id: Int,
    var senderId: Int,
    var targetId: Int,
    var content: String,
    var time: Date,
)