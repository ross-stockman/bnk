package org.rstockman.bnk.common.dao;

import java.util.List;
import java.util.Optional;

import org.rstockman.bnk.common.dto.StandardRequestParamsDTO;
import org.rstockman.bnk.common.dto.StandardResourceDTO;

public interface SimpleDAO<T extends StandardResourceDTO, P extends StandardRequestParamsDTO, K> {

	Optional<T> get(K key);

	List<T> getAll(P params);

	K create(T obj);

	void put(K key, T obj);

	void delete(K key);
}
