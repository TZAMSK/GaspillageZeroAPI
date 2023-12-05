package com.GaspillageZeroAPI.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class ExceptionAuthentification(s: String, cause : Throwable? = null): RuntimeException(s, cause) {

}
