package com.hyfly.utils.lstm;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.AbstractBlock;
import lombok.Getter;

/**
 * The base decoder interface for the encoder-decoder architecture.
 */
public abstract class Decoder extends AbstractBlock {

    @Getter
    protected NDArray attentionWeights;

    public abstract NDList initState(NDList encOutputs);

    @Override
    public Shape[] getOutputShapes(Shape[] inputShapes) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
