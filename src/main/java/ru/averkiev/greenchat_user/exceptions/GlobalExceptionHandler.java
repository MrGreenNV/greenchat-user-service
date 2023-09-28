package ru.averkiev.greenchat_user.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.averkiev.greenchat_user.utils.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс отлавливает все исключения возникающие на уровне контроллера, для предоставления ошибки клиенту в виде JSON.
 * @author mrGreenNV
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Позволяет обработать ошибки связанные с валидацией пользовательских данных.
     * @param ex ошибки при валидации данных.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Ошибки при валидации данных",
                request.getRequestURI(),
                errorMessages
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с записью активности пользователя.
     * @param andEx ошибка при создании записи активности.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(ActivityLogNotDataException.class)
    public ResponseEntity<ErrorResponse> handleActivityLogNotDataException(ActivityLogNotDataException andEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                andEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском активности пользователя.
     * @param anfEx ошибка при поиске записи активности.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(ActivityLogNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleActivityLogNotFoundException(ActivityLogNotFoundException anfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                anfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с аутентификацией пользователя.
     * @param authEx ошибка при аутентификации пользователя.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException authEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                authEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с блокировками между пользователя.
     * @param beEx исключение выбрасывает, если данная блокировка уже существует.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(BlockingAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBlockingAlreadyExistsException(BlockingAlreadyExistsException beEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                beEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с блокировками между пользователя.
     * @param bnfEx исключение выбрасывает, если блокировка не найдена.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(BlockingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBlockingNotFoundException(BlockingNotFoundException bnfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                bnfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с контактами пользователя.
     * @param ceEx исключение выбрасывает, если данный контакт уже существует.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(ContactAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleContactAlreadyExistsException(ContactAlreadyExistsException ceEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ceEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с контактами пользователя.
     * @param cnfEx исключение выбрасывает, если контакт не найден.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContactNotFoundException(ContactNotFoundException cnfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                cnfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с изменением пароля пользователя.
     * @param pnmEx исключение выбрасывает, если подтверждение пароля введено неверно.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(PasswordsNotMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordsNotMatchException(PasswordsNotMatchException pnmEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                pnmEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с регистрацией пользователя.
     * @param urEx ошибка при регистрации пользователя.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RegistrationException urEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                urEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с ролями пользователя.
     * @param raeEx исключение выбрасывает, если данная роль уже существует.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleRoleAlreadyExistsException(RoleAlreadyExistsException raeEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                raeEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с ролями пользователя.
     * @param rnfEx исключение выбрасывает, если роль не найден.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException rnfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                rnfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с лентой активности пользователя.
     * @param unfEx ошибка при аутентификации пользователя.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                unfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

