package core.balance.domain

import core.balance.util.exception.BadRequestException
import core.balance.util.exception.InternalServerException
import core.balance.util.message.MessageSourceCode.INVALID_VERSION
import core.balance.util.message.MessageSourceCode.UNKNOWN

data class Version(
    val major: Int,
    val minor: Int,
    val patch: Int,
) : Comparable<Version> {
    companion object {
        fun of(version: String): Version {
            val splitVersion = version.split('.')
            if (splitVersion.size != 3) {
                throw BadRequestException(INVALID_VERSION)
            }
            return kotlin.runCatching {
                val major = splitVersion[0].toInt()
                val minor = splitVersion[1].toInt()
                val patch = splitVersion[2].toInt()
                return@runCatching Version(major, minor, patch)
            }
                .getOrElse { throw InternalServerException(UNKNOWN) }
        }
    }

    override fun compareTo(other: Version): Int {
        major.compareTo(other.major).let { if (it != 0) return it }
        minor.compareTo(other.minor).let { if (it != 0) return it }
        patch.compareTo(other.patch).let { if (it != 0) return it }
        return this.major.compareTo(other.major)
    }
}
