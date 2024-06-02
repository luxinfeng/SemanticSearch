package com.luxinfeng;

import ai.onnxruntime.OrtException;
import com.luxinfeng.model.EmbeddingModel;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws OrtException, IOException {
        System.out.println("Hello world!");

        EmbeddingModel embeddingModel = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");

        Double ret = embeddingModel.calDistance("Hello", "Hello");

        System.out.println(ret);

    }
}