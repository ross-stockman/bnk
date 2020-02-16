package org.rstockman.bnk.api.vendor.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rstockman.bnk.api.vendor.dto.VendorRequestParams;
import org.rstockman.bnk.api.vendor.dto.VendorResource;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.rstockman.bnk.common.service.KeyProvisionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VendorDAO implements SimpleDAO<VendorResource, VendorRequestParams, String, String> {

	private static final List<String> DEFAULT_COLUMNS = Arrays.asList("_key", "_version", "_created", "_updated", "id",
			"name");

	@Autowired
	private KeyProvisionerService keyProvisioner;

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
		var pagination = resolvePagination(params);
		var query = "select " + (String.join(",", columns)) + " from vendor" + sorts + pagination;
		log.info(query);
		return jdbcTemplate.query(query, (rs, rowNum) -> {
			var obj = new VendorResource();
			if (columns.contains("_key")) {
				obj..setKey(rs.getString("_key"));
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

	private String resolvePagination(VendorRequestParams params) {
		if (Objects.isNull(params.getPage()) && Objects.isNull(params.getLimit())) {
			return " ";
		} else {
			return " LIMIT " + (Objects.isNull(params.getPage()) ? 0 : params.getPage()) + ","
					+ (Objects.isNull(params.getLimit()) ? Integer.MAX_VALUE : params.getLimit()) + " ";
		}
	}

	@Override
	public String create(VendorResource obj) {
		var key = keyProvisioner.getKey();
		jdbcTemplate.update("insert into vendor (_key, id, name) values (?,?,?)", key, obj.getId(), obj.getName());
		return key;
	}

	@Override
	public void put(String key, VendorResource obj) {
		jdbcTemplate.update("update vendor set id = ?, name = ? where _key = ?", obj.getId(), obj.getName(), key);
	}

	@Override
	public void put(String key, String version, VendorResource obj) {
		jdbcTemplate.update("update vendor set id = ?, name = ?, _version = ? where _key = ?", obj.getId(),
				obj.getName(), key, version);
	}

	@Override
	public void delete(String key) {
		jdbcTemplate.update("delete vendor where _key = ?", key);
	}

}
