package io.github.ponderyao.droc.exception.snowflake;

/**
 * SystemClockRewindException：系统时钟回拨异常
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class SystemClockRewindException extends RuntimeException {
    
    public SystemClockRewindException(String message) {
        super(message);
    }
    
    public SystemClockRewindException() {
        this("The machine's system clock is in a rewind status so that the DRoc-snowflake-id cannot be generated normally.");
    }
    
}
