package io.github.ponderyao.droc.util;

import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * JdbcUtils：JDBC工具类
 *
 * @author PonderYao
 * @since 1.3.0
 */
@Component("dRocJdbcUtils")
public class JdbcUtils {
    
    @Autowired(required = false)
    private JdbcTemplate configJdbcTemplate;
    
    private static JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void init() {
        jdbcTemplate = configJdbcTemplate;
    }
    
    public static int update(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }
    
    public static <T> T select(String sql, Class<T> clazz, Object... args) {
        List<T> result = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(clazz));
        if (CollectionUtils.isEmpty(result)) {
            return null;
        } else {
            return result.get(0);
        }
    }
    
}
