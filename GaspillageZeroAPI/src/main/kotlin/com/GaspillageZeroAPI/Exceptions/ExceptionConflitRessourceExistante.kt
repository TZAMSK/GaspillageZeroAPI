package com.GaspillageZeroAPI.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ExceptionConflitRessourceExistante(message: String, cause: Throwable? = null) : RuntimeException(message) {

}
