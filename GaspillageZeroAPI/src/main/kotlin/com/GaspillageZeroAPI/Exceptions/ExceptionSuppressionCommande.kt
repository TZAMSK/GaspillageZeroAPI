package com.GaspillageZeroAPI.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ExceptionSuppressionCommande(message: String, cause: Throwable? = null) : RuntimeException(message) {
}