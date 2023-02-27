package io.github.ponderyao.droc.strategy.snowflake.register;

/**
 * RedisAutoRegisterThread：Redis自动注册线程
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class RedisAutoRegisterThread extends Thread {

    private final RedisKeepConnectionRegister register;
    
    public RedisAutoRegisterThread(String key, String value, long expiration) {
        this.register = new RedisKeepConnectionRegister(key, value, expiration);
    }
    
    @Override
    public void run() {
        register.run();
        // 注册中断，重新开启线程进行注册
        Thread nextThread = new RedisAutoRegisterThread(register.getKey(), register.getValue(), register.getExpiration());
        nextThread.start();
    }
    
}
