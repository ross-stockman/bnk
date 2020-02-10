package org.rstockman.bnk.api.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.rstockman.bnk.api.customer.dto.CustomerRequestParams;
import org.rstockman.bnk.api.customer.dto.CustomerResult;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO implements SimpleDAO<CustomerResult, CustomerRequestParams, String> {

	private static final Map<String, CustomerResult> MAP = new HashMap<>();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<CustomerResult> get(String key) {
		return Optional.ofNullable(MAP.get(key));
	}

	@Override
	public List<CustomerResult> getAll(CustomerRequestParams params) {
		return jdbcTemplate.query("select _key, _version, _created, _updated, id, name from customer",
				(rs, rowNum) -> CustomerResult.builder().key(rs.getString("_key")).version(rs.getString("_version"))
						.created(rs.getTimestamp("_created")).updated(rs.getTimestamp("_updated")).id(rs.getLong("id"))
						.name(rs.getString("name")).build());
	}

	@Override
	public String create(CustomerResult obj) {
		var key = UUID.randomUUID().toString();
		MAP.put(key, obj);
		return key;
	}

	@Override
	public void put(String key, CustomerResult obj) {
		MAP.put(key, obj);
	}

	@Override
	public void delete(String key) {
		MAP.remove(key);
	}

}
