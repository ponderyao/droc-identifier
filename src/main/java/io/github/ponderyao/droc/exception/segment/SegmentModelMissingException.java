package io.github.ponderyao.droc.exception.segment;

/**
 * SegmentModelMissingException：号段模式数据模型缺失异常
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class SegmentModelMissingException extends RuntimeException {
    
    public SegmentModelMissingException() {
        super("The configuration of model is missing. Please ensure the configuration of models are complete " 
            + "or reset the property 'ponder.droc.segment.enable-default' before you use them in your program");
    }
    
    public SegmentModelMissingException(String model) {
        super("The configuration of model " + model + " is missing. Please ensure the configuration of models are complete " 
            + "or reset the property 'ponder.droc.segment.enable-default' before you use them in your program");
    }
    
}
