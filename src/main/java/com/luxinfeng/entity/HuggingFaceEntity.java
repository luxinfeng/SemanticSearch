package com.luxinfeng.entity;

import lombok.Data;

import java.io.File;

@Data
public class HuggingFaceEntity {

    /**
     * 模型文件-仅ONNX格式
     */
    private File modelFile;

    /**
     * 模型对应词汇表
     */
    private File vocabFile;

}
