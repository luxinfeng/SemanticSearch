package com.luxinfeng.entity;

import com.luxinfeng.utils.VecDistanceUtils;

public enum VecDistanceEnum {

    COSINE_SIMILARITY(1, "CosineSimilarity", "余弦相似度") {
        @Override
        public double calculate(float[] vecA, float[] vecB) {
            return VecDistanceUtils.cosineSimilarity(vecA, vecB);
        }
    },
    EUCLIDEAN_DISTANCE(2, "EuclideanDistance", "欧几里得距离") {
        @Override
        public double calculate(float[] vecA, float[] vecB) {
            return VecDistanceUtils.minkowskiDistance(vecA, vecB, 2);
        }
    },
    MANHATTAN_DISTANCE(3, "ManhattanDistance", "曼哈顿距离") {
        @Override
        public double calculate(float[] vecA, float[] vecB) {
            return VecDistanceUtils.minkowskiDistance(vecA, vecB, 1);
        }
    };

    private int type;
    private String name;
    private String description;

    VecDistanceEnum(int type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public abstract double calculate(float[] vecA, float[] vecB);

    public static VecDistanceEnum fromType(int type) {
        for (VecDistanceEnum distanceType : VecDistanceEnum.values()) {
            if (distanceType.type == type) {
                return distanceType;
            }
        }
        throw new IllegalArgumentException("Invalid distance type: " + type);
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
