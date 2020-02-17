package org.rstockman.bnk.api.bank.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rstockman.bnk.api.bank.dto.BankRequestParams;
import org.rstockman.bnk.api.bank.dto.BankResource;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.rstockman.bnk.common.service.KeyProvisionerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BankDAO implements SimpleDAO<BankResource, BankRequestParams, String, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankDAO.class);

	private static final List<String> DEFAULT_COLUMNS = Arrays.asList("_key", "_version", "_created", "_updated", "id",
			"name");

	@Autowired
	private KeyProvisionerService keyProvisioner;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<BankResource> get(String key) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(
					"select " + String.join(",", DEFAULT_COLUMNS) + " from bank where _key = ?", (rs, rowNum) -> {
						return new BankResource(rs.getLong("id"), rs.getString("name"), rs.getString("_key"),
								rs.getString("_version"), rs.getTimestamp("_created"), rs.getTimestamp("_updated"));

					}, key));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			LOGGER.warn("There should be 1 or 0 rows with this _key=" + key + " but more records were found.", e);
			return Optional.empty();
		}
	}

	@Override
	public List<BankResource> getAll(BankRequestParams params) {
		var columns = resolveColumns(params);
		var sorts = resolveSorts(params);
		var pagination = resolvePagination(params);
		var query = "select " + (String.join(",", columns)) + " from bank" + sorts + pagination;
		LOGGER.debug(query);
		return jdbcTemplate.query(query, (rs, rowNum) -> {
			var obj = new BankResource();
			if (columns.contains("_key")) {
				obj.setKey(rs.getString("_key"));
			}
			if (columns.contains("_version")) {
				obj.setVersion(rs.getString("_version"));
			}
			if (columns.contains("_created")) {
				obj.setCreated(rs.getTimestamp("_created"));
			}
			if (columns.contains("_updated")) {
				obj.setUpdated(rs.getTimestamp("_updated"));
			}
			if (columns.contains("id")) {
				obj.setId(rs.getLong("id"));
			}
			if (columns.contains("name")) {
				obj.setName(rs.getString("name"));
			}
			return obj;
		});
	}

	private List<String> resolveColumns(BankRequestParams params) {
		if (Objects.isNull(params.getFields())) {
			return DEFAULT_COLUMNS;
		} else {
			return Arrays.asList(params.getFields().trim().split("\\s*,\\s*"));
		}
	}

	private String resolveSorts(BankRequestParams params) {
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

	private String resolvePagination(BankRequestParams params) {
		if (Objects.isNull(params.getPage()) && Objects.isNull(params.getLimit())) {
			return " ";
		} else {
			return " LIMIT " + (Objects.isNull(params.getPage()) ? 0 : params.getPage()) + ","
					+ (Objects.isNull(params.getLimit()) ? Integer.MAX_VALUE : params.getLimit()) + " ";
		}
	}

	@Override
	public String create(BankResource obj) {
		var key = keyProvisioner.getKey();
		jdbcTemplate.update("insert into bank (_key, id, name) values (?,?,?)", key, obj.getId(), obj.getName());
		return key;
	}

	@Override
	public void put(String key, BankResource obj) {
		jdbcTemplate.update("update bank set id = ?, name = ? where _key = ?", obj.getId(), obj.getName(), key);
	}

	@Override
	public void put(String key, String version, BankResource obj) {
		jdbcTemplate.update("update bank set id = ?, name = ?, _version = ? where _key = ?", obj.getId(), obj.getName(),
				key, version);
	}

	@Override
	public void delete(String key) {
		jdbcTemplate.update("delete from bank where _key = ?", key);
	}

}
