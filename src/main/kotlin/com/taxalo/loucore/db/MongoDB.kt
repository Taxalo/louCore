package com.taxalo.loucore.db

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.taxalo.loucore.models.PermissionObject
import com.taxalo.loucore.models.User
import com.taxalo.loucore.permissions.PermissionsManager
import com.taxalo.loucore.utils.Settings
import org.bson.UuidRepresentation
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.permissions.PermissionAttachment
import org.litote.kmongo.*
import org.litote.kmongo.util.KMongoUtil
import java.time.LocalDateTime
import java.util.UUID

object MongoDB {
    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(""))
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .codecRegistry(KMongoUtil.defaultCodecRegistry)

        .build()
    private val client =
        KMongo.createClient(settings)
    private val dataBase = client.getDatabase("lougg")
    private val userCol = dataBase.getCollection<User>()

    private val userColors = HashMap<UUID, String> ()
    val userPermissions = HashMap<UUID, Map<String, PermissionObject>> ()
    fun loadDb() {
        Bukkit.getScheduler().runTaskAsynchronously(Settings.plugin, Runnable {
            val users = userCol.find()
            for (user in users) {
                userColors[user.uuid] = user.color
                userPermissions[user.uuid] = user.permissions
            }
        })
    }

    fun loadPermissions(uuid: UUID, attachment: PermissionAttachment) {
        Bukkit.getScheduler().runTaskAsynchronously(Settings.plugin, Runnable {
            val user = userCol.findOne(User::uuid eq uuid)
            if (user === null || user.permissions.isEmpty()) return@Runnable
            for (permission in user.permissions) {
                attachment.setPermission(permission.key, permission.value.value)
            }
        })
    }

    fun getColor(uuid: UUID): String {
        val userColor = userColors[uuid]
        if (userColor === null) {
            return ChatColor.GRAY.toString()
        }
        return userColor
    }

    fun setColor(uuid: UUID, name: String, color: ChatColor) {
        Bukkit.getScheduler().runTaskAsynchronously(Settings.plugin, Runnable {
            val user: User? = userCol.findOne(User::uuid eq uuid)
            val current = LocalDateTime.now()
            if (user === null) {
                val permissions =
                    if (PermissionsManager.attachments[uuid] === null) null else PermissionsManager.attachments[uuid]!!.permissions
                val permissionObjectList = HashMap<String, PermissionObject>()
                if (permissions != null) {
                    for (permission in permissions) {
                        val permissionObject = PermissionObject(permission.value, uuid, name, current)
                        permissionObjectList[permission.key] = permissionObject
                    }
                }
                userCol.insertOne(User(uuid, color.toString(), permissionObjectList))
            } else {
                userCol.updateOne(User::uuid eq uuid, setValue(User::color, color.toString()))
            }
            userColors[uuid] = color.toString()
        })
    }

    fun setPermissions(uuid: UUID, name: String, attachment: PermissionAttachment, permissionName: String, userPermissionObject: PermissionObject) {
        Bukkit.getScheduler().runTaskAsynchronously(Settings.plugin, Runnable {
            val user: User? = userCol.findOne(User::uuid eq uuid)
            val current = LocalDateTime.now()
            if (user === null) {
                val permissions = attachment.permissions
                val permissionObjectList = HashMap<String, PermissionObject>()
                for (permission in permissions) {
                    val permissionObject = PermissionObject(permission.value, uuid, name, current)
                    permissionObjectList[permission.key] = permissionObject
                }
                userCol.insertOne(User(uuid, ChatColor.GRAY.toString(), permissionObjectList))
                userPermissions[uuid] = permissionObjectList
            } else {
                val permissionObjectList = user.permissions as HashMap
                permissionObjectList[permissionName] = userPermissionObject
                userCol.updateOne(User::uuid eq uuid, setValue(User::permissions, permissionObjectList))
                userPermissions[uuid] = permissionObjectList
            }
        })
    }

}