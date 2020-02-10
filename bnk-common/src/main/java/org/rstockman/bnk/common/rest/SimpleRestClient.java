package org.rstockman.bnk.common.rest;

public interface SimpleRestClient<T, K> {
	T get(K id);
}
