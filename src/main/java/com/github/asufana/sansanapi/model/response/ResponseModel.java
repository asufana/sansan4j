package com.github.asufana.sansanapi.model.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ResponseModel<T extends ApiResponse<U>, U> {
    
    private final List<T> results;
    
    //ファクトリ
    public ResponseModel add(ResponseModel other) {
        List<T> newList = new ArrayList<>(results);
        newList.addAll(other.results);
        return new ResponseModel(newList);
    }
    
    //コンストラクタ
    public ResponseModel(T result) {
        this(Arrays.asList(result));
    }
    
    //コンストラクタ
    public ResponseModel(List<T> results) {
        this.results = results;
    }
    
    public List<U> results() {
        return results.stream()
                      .flatMap(result -> result.records().stream())
                      .collect(Collectors.toList());
    }
    
    public Stream<U> stream() {
        return results().stream();
    }
    
    public Stream<U> filter(Predicate<U> predicate) {
        return stream().filter(predicate);
    }
    
    public <S> Stream<S> map(Function<U, S> function) {
        return stream().map(function);
    }
    
    public void forEach(Consumer<U> consumer) {
        stream().forEach(consumer);
    }
    
    public boolean hasNext() {
        return results.stream()
                      .filter(result -> result.hasNext() == false)
                      .findAny()
                      .isPresent() == false;
    }
    
    public Integer size() {
        return results.stream().mapToInt(ApiResponse::size).sum();
    }
    
    @Override
    public String toString() {
        return results.stream()
                      .map(ApiResponse::toString)
                      .collect(joining("\n"));
    }
    
}
