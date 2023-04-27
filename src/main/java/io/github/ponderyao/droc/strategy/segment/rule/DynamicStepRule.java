package io.github.ponderyao.droc.strategy.segment.rule;

import java.util.Date;

/**
 * DynamicStepRule：动态步长计算规则
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class DynamicStepRule {
    
    private static final double DEFAULT_SHORTEN_FRACTION = 0.75d;
    private static final double DEFAULT_LENGTHEN_FRACTION = 0.25d;
    private static final double ENABLED_TPS_DEVIATION = 0.25d;
    
    
    public static long execute(long step, Date beginTime, long min, long max, double tps) {
        if (beginTime == null) {
            return shorten(step, 1.0, min);
        }
        Date currTime = new Date();
        long duration = currTime.getTime() - beginTime.getTime();
        double currTps = (double) step / duration * 1000;
        double factor = calcFactor(step, min, max, currTps, tps);
        if (currTps < tps) {
            return shorten(step, factor, min);
        }
        if (currTps > tps) {
            return lengthen(step, factor, max);
        }
        return step;
    }
    
    private static long shorten(long step, double factor, long min) {
        double newStep = step * (DEFAULT_SHORTEN_FRACTION + (1 - DEFAULT_SHORTEN_FRACTION) * factor);
        long result = new Double(newStep).longValue();
        return Math.max(result, min);
    }
    
    private static long lengthen(long step, double factor, long max) {
        double newStep = step * (1 + DEFAULT_LENGTHEN_FRACTION * factor);
        long result = new Double(newStep).longValue();
        return Math.min(result, max);
    }
    
    private static double calcFactor(long step, long min, long max, double currTps, double tps) {
        double tpsBottomLine = tps / step * min;
        double tpsTopLine = tps / step * max;
        double tpsBottomDeviationLine = tps - (tpsTopLine - tpsBottomLine) * ENABLED_TPS_DEVIATION;
        double tpsTopDeviationLine = tps + (tpsTopLine - tpsBottomLine) * ENABLED_TPS_DEVIATION;
        if (currTps >= tpsBottomLine && currTps < tpsBottomDeviationLine) {
            return (tpsBottomDeviationLine - currTps) / (tpsBottomDeviationLine - tpsBottomLine);
        }
        if (currTps <= tpsTopLine && currTps > tpsTopDeviationLine) {
            return (currTps - tpsTopDeviationLine) / (tpsTopLine - tpsTopDeviationLine);
        }
        return 1.0;
    }
    
}
