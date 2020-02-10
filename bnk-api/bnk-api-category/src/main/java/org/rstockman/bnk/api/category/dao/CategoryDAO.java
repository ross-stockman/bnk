package org.rstockman.bnk.api.category.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.rstockman.bnk.api.category.dto.CategoryRequestParams;
import org.rstockman.bnk.api.category.dto.CategoryResult;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAO implements SimpleDAO<CategoryResult, CategoryRequestParams, String> {

	private static final Map<String, CategoryResult> MAP = new HashMap<>();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<CategoryResult> get(String key) {
		return Optional.ofNullable(MAP.get(key));
	}

	@Override
	public List<CategoryResult> getAll(CategoryRequestParams params) {
		return jdbcTemplate.query("select _key, _version, _created, _updated, id, name from category",
				(rs, rowNum) -> CategoryResult.builder().key(rs.getString("_key")).version(rs.getString("_version"))
						.created(rs.getTimestamp("_created")).updated(rs.getTimestamp("_updated")).id(rs.getLong("id"))
						.name(rs.getString("name")).build());
	}

	@Override
	public String create(CategoryResult obj) {
		var key = UUID.randomUUID().toString();
		MAP.put(key, obj);
		return key;
	}

	@Override
	public void put(String key, CategoryResult obj) {
		MAP.put(key, obj);
	}

	@Override
	public void delete(String key) {
		MAP.remove(key);
	}

}
