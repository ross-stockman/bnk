package org.rstockman.bnk.api.party.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.rstockman.bnk.api.party.dto.Category;
import org.rstockman.bnk.api.party.dto.PartyRequestParams;
import org.rstockman.bnk.api.party.dto.PartyResult;
import org.rstockman.bnk.api.party.dto.Vendor;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.rstockman.bnk.common.rest.SimpleRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PartyDAO implements SimpleDAO<PartyResult, PartyRequestParams, String> {

	private static final Map<String, PartyResult> MAP = new HashMap<>();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${bnk.api.vendor.uri}")
	private String vendorUrl;

	@Value("${bnk.api.category.uri}")
	private String categoryUrl;

	@Autowired
	private SimpleRestClient<Vendor, String> vendorRestClient;

	@Autowired
	private SimpleRestClient<Category, String> categoryRestClient;

	@Override
	public Optional<PartyResult> get(String key) {
		return Optional.ofNullable(MAP.get(key));
	}

	@Override
	public List<PartyResult> getAll(PartyRequestParams params) {
		return jdbcTemplate.query("select _key, _version, _created, _updated, id, name from category",
				(rs, rowNum) -> PartyResult.builder().key(rs.getString("_key")).version(rs.getString("_version"))
						.created(rs.getTimestamp("_created")).updated(rs.getTimestamp("_updated")).id(rs.getLong("id"))
						.name(rs.getString("name")).build());
	}

	@Override
	public String create(PartyResult obj) {
		if (Objects.nonNull(obj.getCategory())) {
			categoryRestClient.get(obj.getCategory().getId());
		}
		if (Objects.nonNull(obj.getVendor())) {
			vendorRestClient.get(obj.getVendor().getId());
		}
		var key = UUID.randomUUID().toString();
		MAP.put(key, obj);
		return key;
	}

	@Override
	public void put(String key, PartyResult obj) {
		if (Objects.nonNull(obj.getCategory())) {
			categoryRestClient.get(obj.getCategory().getId());
		}
		if (Objects.nonNull(obj.getVendor())) {
			vendorRestClient.get(obj.getVendor().getId());
		}
		MAP.put(key, obj);
	}

	@Override
	public void delete(String key) {
		MAP.remove(key);
	}
}
