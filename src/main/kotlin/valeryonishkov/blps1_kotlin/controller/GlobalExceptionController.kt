package valeryonishkov.blps1_kotlin.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionController {
    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchUserExceptionHandler(exception: NoSuchElementException): ProblemDetail{
        println(exception.cause)
        println(exception.printStackTrace())
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "no such user")
    }
}