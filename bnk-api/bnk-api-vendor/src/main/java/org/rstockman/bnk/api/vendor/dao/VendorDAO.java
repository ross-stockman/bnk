package org.rstockman.bnk.api.vendor.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.StringUtils;
import org.rstockman.bnk.api.vendor.dto.VendorRequestParams;
import org.rstockman.bnk.api.vendor.dto.VendorResource;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class VendorDAO implements SimpleDAO<VendorResource, VendorRequestParams, String> {

	private static final Map<String, VendorResource> MAP = new HashMap<>();

	private static final List<String> DEFAULT_COLUMNS = Arrays.asList("_key", "_version", "_created", "_updated", "id",
			"name");

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<VendorResource> get(String key) {
		try {
			return Optional.of(jdbcTemplate.queryForObject("select * from vendor where _key = ?", (rs, rowNum) -> {
				return VendorResource.builder().key(rs.getString("_key")).version(rs.getString("_version"))
						.created(rs.getTimestamp("_created")).updated(rs.getTimestamp("_updated")).id(rs.getLong("id"))
						.name(rs.getString("name")).build();
			}, key));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			log.warn("There should be 1 or 0 rows with this _key=" + key + " but more records were found.", e);
			return Optional.empty();
		}
	}

	@Override
	public List<VendorResource> getAll(VendorRequestParams params) {
		var columns = resolveColumns(params);
		var sorts = resolveSorts(params);
		var query = "select " + (StringUtils.join(columns)) + " from vendor" + sorts;
		log.info(query);
		return jdbcTemplate.query(query, (rs, rowNum) -> {
			var builder = VendorResource.builder();
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

	private String resolveSorts(VendorRequestParams params) {
		if (Objects.isNull(params.getSort())) {
			return " ";
		} else {
			return " ORDER BY "
					+ String.join(",", Arrays.asList(params.getSort().trim().split("\\s*,\\s*")).stream().map(s -> {
						if (s.startsWith("-")) {
							return s.substring(1) + " DESC ";
						} else if (s.startsWith("+")) {
							return s.substring(1) + " ASC ";
						} else {
							return s;
						}
					}).collect(Collectors.toList()));
		}
	}

	@Override
	public String create(VendorResource obj) {
		var key = UUID.randomUUID().toString();
		MAP.put(key, obj);
		return key;
	}

	@Override
	public void put(String key, VendorResource obj) {
		MAP.put(key, obj);
	}

	@Override
	public void delete(String key) {
		MAP.remove(key);
	}

}
