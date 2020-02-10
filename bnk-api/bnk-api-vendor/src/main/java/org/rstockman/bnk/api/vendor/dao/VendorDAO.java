package org.rstockman.bnk.api.vendor.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.apache.tomcat.util.buf.StringUtils;
import org.rstockman.bnk.api.vendor.dto.VendorRequestParams;
import org.rstockman.bnk.api.vendor.dto.VendorResult;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VendorDAO implements SimpleDAO<VendorResult, VendorRequestParams, String> {

	private static final Map<String, VendorResult> MAP = new HashMap<>();

	private static final List<String> DEFAULT_COLUMNS = Arrays.asList("_key", "_version", "_created", "_updated", "id",
			"name");

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<VendorResult> get(String key) {
		return Optional.ofNullable(MAP.get(key));
	}

	@Override
	public List<VendorResult> getAll(VendorRequestParams params) {
		var columns = resolveColumns(params);
		return jdbcTemplate.query("select " + (StringUtils.join(columns)) + " from vendor", (rs, rowNum) -> {
			var builder = VendorResult.builder();
			if (columns.contains("_key")) {
				builder = builder.key(rs.getString("_key"));
			}
			if (columns.contains("_version")) {
				builder = builder.version(rs.getString("_version"));
			}
			if (columns.contains("_created")) {
				builder = builder.created(rs.getTimestamp("_created"));
			}
			if (columns.contains("_updated")) {
				builder = builder.updated(rs.getTimestamp("_updated"));
			}
			if (columns.contains("id")) {
				builder = builder.id(rs.getLong("id"));
			}
			if (columns.contains("name")) {
				builder = builder.name(rs.getString("name"));
			}
			return builder.build();
		});
	}

	private List<String> resolveColumns(VendorRequestParams params) {
		if (Objects.isNull(params.getFields())) {
			return DEFAULT_COLUMNS;
		} else {
			return Arrays.asList(params.getFields().trim().split("\\s*,\\s*"));
		}
	}

	@Override
	public String create(VendorResult obj) {
		var key = UUID.randomUUID().toString();
		MAP.put(key, obj);
		return key;
	}

	@Override
	public void put(String key, VendorResult obj) {
		MAP.put(key, obj);
	}

	@Override
	public void delete(String key) {
		MAP.remove(key);
	}

}
