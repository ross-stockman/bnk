package org.rstockman.bnk.common.service;

import org.rstockman.bnk.common.dto.StandardRequestParamsDTO;
import org.rstockman.bnk.common.dto.StandardResourceDTO;

import java.util.List;
import java.util.Optional;

public interface FaultTolerantCrudService<T extends StandardResourceDTO, P extends StandardRequestParamsDTO, K, V> {

    Optional<T> get(K key);
    Optional<T> getFallback(K key);

    List<T> getAll(P params);
    List<T> getAllFallback(P params);

    K create(T obj);
    K createFallback(T obj);

    void put(K key, T obj);
    void putFallback(K key, T obj);

    void put(K key, V version, T obj);
    void putFallback(K key, V version, T obj);

    void delete(K key);
    void deleteFallback(K key);
}
