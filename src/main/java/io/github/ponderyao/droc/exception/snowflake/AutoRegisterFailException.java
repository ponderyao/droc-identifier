package io.github.ponderyao.droc.exception.snowflake;

/**
 * AutoRegisterFailException：自动注册失败异常
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class AutoRegisterFailException extends RuntimeException {
    
    public AutoRegisterFailException(String message) {
        super(message);
    }
    
    public AutoRegisterFailException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
