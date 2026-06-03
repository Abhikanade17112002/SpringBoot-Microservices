package com.microsercives.userservice.controllers;
import com.microsercives.userservice.dtos.UserResponseDTO;
import com.microsercives.userservice.dtos.request.DeleteUserAccountRequestDTO;
import com.microsercives.userservice.dtos.request.UpdateUserPasswordRequestDTO;
import com.microsercives.userservice.dtos.request.UpdateUserProfileRequestDTO;
import com.microsercives.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getLoggedInUserProfile(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getLoggedInUserProfile());
    }
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllRegisteredUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllRegisteredUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getRegisteredUserById(@PathVariable(name = "userId") String userId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getRegisteredUserById(userId));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteRegisteredUserById(@PathVariable(name = "userId") String userId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteRegisteredUserById(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updatedLoggedInUserProfile(@RequestBody UpdateUserProfileRequestDTO updateUserProfileRequestDTO){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updatedLoggedInUserProfile(updateUserProfileRequestDTO)) ;
    }

    @PutMapping("/me/change-password")
    public ResponseEntity<Boolean> updatedLoggedInUserPassword(@RequestBody UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updatedLoggedInUserPassword(updateUserPasswordRequestDTO)) ;
    }

    @DeleteMapping("/me/deleteaccount")
    public ResponseEntity deleteUserAccount(@RequestBody DeleteUserAccountRequestDTO deleteUserAccountRequestDTO){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteUserAccount(deleteUserAccountRequestDTO)) ;
    }






















































//
//
//
//    // CREATE USER
//    @PostMapping
//    public ResponseEntity<User> addNewUser(
//            @RequestBody CreateUserRequestDTO createUserRequestDTO) {
//
//        User savedUser = userService.addNewUser(createUserRequestDTO);
//
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }
//
//    /*
//     * Retry + Circuit Breaker
//     * -----------------------
//     *
//     * Retry is used for temporary failures where a subsequent
//     * attempt may succeed.
//     *
//     * Circuit Breaker is used for persistent failures and prevents
//     * continuously calling a failing service.
//     *
//     * When both are used together:
//     *
//     * @Retry(name = "ratingRetry")
//     * @CircuitBreaker(name = "ratingBreaker")
//     *
//     * Flow:
//     *
//     * Request
//     *    |
//     * Attempt 1 -> FAIL
//     * Attempt 2 -> FAIL
//     * Attempt 3 -> FAIL
//     *    |
//     * Final Result = FAIL
//     *    |
//     * Circuit Breaker records ONE failed operation
//     *
//     * Important:
//     * Circuit Breaker tracks the final outcome of the operation,
//     * not the number of retry attempts.
//     *
//     * Therefore:
//     *
//     * 3 Failed Retry Attempts
//     *            !=
//     * 3 Circuit Breaker Failures
//     *
//     * Instead:
//     *
//     * 3 Failed Retry Attempts
//     *            =
//     * 1 Failed Operation
//     *            =
//     * 1 Circuit Breaker Failure Count
//     *
//     * Benefits:
//     * - Retry handles temporary network glitches.
//     * - Circuit Breaker handles long-term service outages.
//     * - Together they improve resilience and prevent
//     *   unnecessary load on failing services.
//     *
//     * Memory Trick:
//     * Retry counts attempts.
//     * Circuit Breaker counts outcomes.
//     */
//
//
//    /*
//     * Retry + Circuit Breaker with Fallback
//     * -------------------------------------
//     *
//     * @Retry attempts the operation multiple times before giving up.
//     *
//     * If all retry attempts fail:
//     *
//     * Attempt 1 -> FAIL
//     * Attempt 2 -> FAIL
//     * Attempt 3 -> FAIL
//     *
//     * The final exception is propagated to the Circuit Breaker.
//     *
//     * Circuit Breaker then:
//     * - Records the operation as FAILED
//     * - Updates its failure statistics
//     * - Checks whether the failure threshold is reached
//     * - Opens the circuit if required
//     * - Executes the fallback method
//     *
//     * Important:
//     * Retry counts ATTEMPTS.
//     * Circuit Breaker counts FINAL OUTCOMES.
//     *
//     * Therefore:
//     *
//     * 3 Failed Retry Attempts
//     *          =
//     * 1 Failed Operation
//     *          =
//     * 1 Circuit Breaker Failure
//     *
//     * If Retry has its own fallback method, that fallback is
//     * executed immediately after retries are exhausted and the
//     * exception may never reach the Circuit Breaker.
//     *
//     * Recommended Approach:
//     *
//     * @Retry(name = "ratingRetry")
//     * @CircuitBreaker(
//     *     name = "ratingBreaker",
//     *     fallbackMethod = "fallback"
//     * )
//     *
//     * Keep a single fallback method at the Circuit Breaker level
//     * so Retry handles temporary failures and Circuit Breaker
//     * handles persistent failures.
//     */
//
//
//    int temp = 1 ;
//
//    // GET USER BY ID
//    @GetMapping("/{userId}")
////    @Retry(name = "getSingleUserRetry",fallbackMethod = "getUserByIdFallback")
////    @CircuitBreaker(name = "getSingleUserBreaker",fallbackMethod = "getUserByIdFallback" )
////    @RateLimiter(name ="getSingleUserRateLimiter" , fallbackMethod ="getUserByIdFallback" )
//    public ResponseEntity<User> getUserById(
//            @PathVariable String userId) {
//        System.out.println("getUserById " + userId + " Retry Attempt ==> " + temp++);
//
//        User user = userService.getUserById(userId);
////        List<Rating> userRatingsList = ratingService.getUserRatings(user.getId());
//
////        userRatingsList = userRatingsList.stream().map((rating)->{
////            ResponseEntity<Hotel> retreivedHotel = hotelService.getHotelById(rating.getHotelId());
////            rating.setHotel(retreivedHotel.getBody());
////           return rating ;
////        }).toList();
////
////        user.setRatings(userRatingsList);
//
//        return ResponseEntity.ok(user);
//
//    }
//
//    public ResponseEntity<User> getUserByIdFallback(String userId , Exception e){
//        System.out.println("GetUserByIdFallback Fallback Methode Executed ==> " + e.getMessage());
//        List<Rating> userRatingsList = List.of();
////        return ResponseEntity.ok(new User("Id","First Name","Last Name","first.last@email.com", LocalDate.now(),userRatingsList));
//
//        return null ;
//    }
//
//    // GET ALL USERS
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//
//        List<User> users = userService.getAllUsers();
//
//        return ResponseEntity.ok(users);
//    }
//
//    // UPDATE USER
//    @PutMapping("/{userId}")
//    public ResponseEntity<User> updateUser(
//            @PathVariable String userId,
//            @RequestBody CreateUserRequestDTO updateUserRequestDTO) {
//
//        User updatedUser = userService.updateUser(userId,
//                updateUserRequestDTO);
//
//        return ResponseEntity.ok(updatedUser);
//    }
//
//    // DELETE USER
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<String> deleteUser(
//            @PathVariable String userId) {
//
//        userService.deleteUser(userId);
//
//        return ResponseEntity.ok("User Deleted Successfully");
//    }
}