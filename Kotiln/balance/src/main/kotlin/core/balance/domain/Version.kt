package core.balance.domain

import core.balance.exception.BadRequestException

data class Version(
    val major: Int,
    val minor: Int,
    val patch: Int,
) : Comparable<Version> {
    companion object {
        fun of(version: String): Version {
            val splitVersion = version.split('.')
            if (splitVersion.size != 3) {
                throw BadRequestException("unavailable version")
            }
            return kotlin.runCatching {
                val major = splitVersion[0].toInt()
                val minor = splitVersion[1].toInt()
                val patch = splitVersion[2].toInt()
                return@runCatching Version(major, minor, patch)
            }
                .getOrElse { throw BadRequestException("exception throw while parsing version") }
        }
    }

    override fun compareTo(other: Version): Int {
        major.compareTo(other.major).let { if (it != 0) return it }
        minor.compareTo(other.minor).let { if (it != 0) return it }
        patch.compareTo(other.patch).let { if (it != 0) return it }
        return this.major.compareTo(other.major)
    }
}
