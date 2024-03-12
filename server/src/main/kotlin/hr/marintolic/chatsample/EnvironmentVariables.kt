package hr.marintolic.chatsample

/**
 * Contains environment variables.
 */
internal object EnvironmentVariables {

    /**
     * The JWT secret used to generate tokens.
     */
    val jwtSecret = System.getenv("CHAT_SAMPLE_JWT_SECRET")

    /**
     * JWT issuer information.
     */
    val jwtIssuer = System.getenv("CHAT_SAMPLE_JWT_ISSUER")

    /**
     * JWT audience information.
     */
    val jwtAudience = System.getenv("CHAT_SAMPLE_JWT_AUDIENCE")

    /**
     * JWT realm information.
     */
    val jwtRealm = System.getenv("CHAT_SAMPLE_JWT_REALM")

    /**
     * The name of the database holding user information.
     */
    val chatSampleUserDbName = System.getenv("CHAT_SAMPLE_USER_DB_NAME")

    /**
     * The name of the user with privileges to the user database.
     */
    val chatSampleUserDbUserName = System.getenv("CHAT_SAMPLE_USER_DB_USER_NAME")

    /**
     * The password of the user with the privileges to the user database.
     */
    val chatSampleUserDbUserPassword = System.getenv("CHAT_SAMPLE_USER_DB_USER_PASSWORD")
}