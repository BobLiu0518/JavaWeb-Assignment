package tech.bobliu.assignment07.model

data class Message(
    var id: Int,
    var senderId: Int,
    var targetId: Int,
    var content: String,
)