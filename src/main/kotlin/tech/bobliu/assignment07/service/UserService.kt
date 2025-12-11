package tech.bobliu.assignment07.service

import tech.bobliu.assignment07.model.User

object UserService {
    private val users = ArrayList<User>()
    private val onlineUsers = HashMap<Int, User>()

    fun getUserById(id: Int) = users[id]

    fun getUserByUsername(username: String) = users.find { it.username == username }

    fun getOnlineUsers() = onlineUsers.values.toList()

    fun loginAsUser(username: String): User {
        val user = synchronized(users) {
            getUserByUsername(username) ?: run {
                val newUser = User(id = users.size, username = username)
                users.add(newUser)
                newUser
            }
        }
        synchronized(onlineUsers) {
            onlineUsers[user.id] = user
        }
        MessageService.sendMessage("@${user.username} 已加入聊天室", -1, -1)

        return user
    }

    fun setUserAsOffline(user: User) {
        MessageService.sendMessage("@${user.username} 已离开聊天室", -1, -1)
        synchronized(onlineUsers) {
            onlineUsers.remove(user.id)
        }
    }
}