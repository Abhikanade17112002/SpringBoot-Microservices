package com.microsercives.userservice.errorhandling;

import com.microsercives.userservice.dtos.response.ApiErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;


//No.
//
//@RestControllerAdvice handles exceptions that occur during Spring MVC request processing, typically inside controllers and service layers.
//
//Exceptions thrown inside servlet filters occur before the request reaches DispatcherServlet, so they are not intercepted by Global Exception Handlers.
//
//For filter-level exceptions, we use AuthenticationEntryPoint, AccessDeniedHandler, or handle the exception directly inside the filter.






//Way To Handle The Exception In The Filter
/**
 *
 * @Override
 * protected void doFilterInternal(
 *         HttpServletRequest request,
 *         HttpServletResponse response,
 *         FilterChain filterChain)
 *         throws ServletException, IOException {
 *
 *     try {
 *
 *         String token = request.getHeader("Authorization");
 *
 *         if(token == null){
 *             throw new RuntimeException("JWT Missing");
 *         }
 *
 *         filterChain.doFilter(request, response);
 *
 *     }
 *     catch (Exception ex){
 *
 *         response.setStatus(
 *                 HttpServletResponse.SC_UNAUTHORIZED
 *         );
 *
 *         response.setContentType(
 *                 "application/json"
 *         );
 *
 *         response.getWriter().write(
 *                 """
 *                 {
 *                   "status":401,
 *                   "message":"JWT Missing"
 *                 }
 *                 """
 *         );
 *     }
 * }
 * **/



//          Request
//           │
//           ▼
//        JWT Filter
//           │
//           ▼
//        DispatcherServlet
//           │
//           ▼
//        Controller
//           │
//           ▼
//        Service




@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDTO>
    handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField()
                                + " "
                                + error.getDefaultMessage())
                .toList();

        ApiErrorResponseDTO response =
                new ApiErrorResponseDTO(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Validation Failed",
                        request.getRequestURI(),
                        errors
                );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponseDTO>
    handleUserAlreadyExists(
            UserAlreadyExistsException ex,
            HttpServletRequest request) {

        ApiErrorResponseDTO response =
                new ApiErrorResponseDTO(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDTO>
    handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiErrorResponseDTO response =
                new ApiErrorResponseDTO(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponseDTO>
    handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ApiErrorResponseDTO response =
                new ApiErrorResponseDTO(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
