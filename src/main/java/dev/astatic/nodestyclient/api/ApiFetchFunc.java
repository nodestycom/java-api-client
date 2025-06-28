package dev.astatic.nodestyclient.api;

import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ApiFetchFunc {

    <T> CompletableFuture<ApiResponse<T>> fetch(String path, String method, Object body, Class<T> responseDataType);

    <T> CompletableFuture<ApiResponse<List<T>>> fetch(String path, String method, Object body, TypeToken<List<T>> typeToken);

    default <T> TypeToken<List<T>> getTypeReferenceForList(Class<T> innerClass) {
        return (TypeToken<List<T>>) TypeToken.getParameterized(List.class, innerClass);
    }
}
