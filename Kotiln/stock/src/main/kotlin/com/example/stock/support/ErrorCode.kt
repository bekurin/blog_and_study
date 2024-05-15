package com.example.stock.support

enum class ErrorCode(val path: String) {
    NOT_ENOUGH_QUANTITY("errors.NotEnoughQuantity"),
    REDIS_COMMAND_ERROR("errors.RedisCommandError"),
    UNKNOWN_ERROR("errors.UnknownError"),
}
