package io.github.ponderyao.droc.strategy;

/**
 * Strategy：策略接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface Strategy {

    boolean match(String type);
    
}
