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
}