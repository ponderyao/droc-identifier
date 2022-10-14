package io.github.ponderyao.droc.exception.properties;

/**
 * BadCustomPropertiesException：不符合要求的自定义参数配置的异常
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class BadCustomPropertiesException extends RuntimeException {
    
    public BadCustomPropertiesException(String message) {
        super(message);
    }
    
    public BadCustomPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BadCustomPropertiesException(String source, String compare, Object target) {
        this("The configuration of property \"" + source + "\" is bad. Please adjust it to a value " + compare + " than " + target);
    }
    
}
