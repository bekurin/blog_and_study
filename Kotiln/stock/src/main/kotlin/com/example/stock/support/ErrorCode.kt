package com.example.stock.support

enum class ErrorCode(val path: String) {
    NOT_ENOUGH_QUANTITY("errors.NotEnoughQuantity"),
    UNKNOWN_ERROR("errors.UnknownError"),
}
