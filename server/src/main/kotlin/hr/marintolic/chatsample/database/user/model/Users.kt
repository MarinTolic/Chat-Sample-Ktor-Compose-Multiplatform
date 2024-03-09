package hr.marintolic.chatsample.database.user.model

import org.jetbrains.exposed.sql.Table


/**
 * A table of users for the database.
 */
object Users : Table() {
    /**
     * The name for the column containing the usernames.
     */
    private const val USERNAME = "username"

    /**
     * The name for the column containing the passwords.
     */
    private const val PASSWORD = "password"

    /**
     * The username for the given user.
     * @see [User]
     */
    val username = varchar(name = USERNAME, length = 20)

    /**
     * The password for the given user.
     * @see [User]
     */
    val password = varchar(PASSWORD, length = 128)
}