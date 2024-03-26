package com.hyfly.unit02;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.training.dataset.ArrayDataset;
import ai.djl.training.dataset.Batch;
import com.hyfly.unit02.entity.DataPoints;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test03 {

    public static void main(String[] args) throws Exception {
        try (NDManager manager = NDManager.newBaseManager()) {

            // 3.2.1. 生成数据集
            NDArray trueW = manager.create(new float[]{2, -3.4f});
            float trueB = 4.2f;

            DataPoints dp = syntheticData(manager, trueW, trueB, 1000);
            NDArray features = dp.getX();
            NDArray labels = dp.getY();

            log.info("features: {}", features.toDebugString(true));
            log.info("labels: {}", labels.toDebugString(true));

            // 3.2.2. 读取数据集
            int batchSize = 10;

            ArrayDataset dataset = new ArrayDataset.Builder()
                    .setData(features) // Set the Features
                    .optLabels(labels) // Set the Labels
                    .setSampling(batchSize, false) // set the batch size and random sampling to false
                    .build();

            for (Batch batch : dataset.getData(manager)) {
                // Call head() to get the first NDArray
                NDArray x = batch.getData().head();
                NDArray y = batch.getLabels().head();
                log.info(x.toDebugString(true));
                log.info(y.toDebugString(true));
                // Don't forget to close the batch!
                batch.close();
                break;
            }
        }
    }

    public static DataPoints syntheticData(NDManager manager, NDArray w, float b, int numExamples) {
        NDArray X = manager.randomNormal(new Shape(numExamples, w.size()));
//        NDArray y = X.dot(w).add(b);
        NDArray y = X.matMul(w).add(b);
        // Add noise
        y = y.add(manager.randomNormal(0, 0.01f, y.getShape(), DataType.FLOAT32));
        return new DataPoints(X, y);
    }
}
