package com.vishu.neovault.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.vishu.neovault.utils.Constants;

import com.vishu.neovault.model.NeoUserModel;
import com.vishu.neovault.model.UtlityModel;

@Repository
public class NeoUserRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    protected RowMapper<NeoUserModel> getRowMapper() {
        return new RowMapper<NeoUserModel>() {
            @Override
            public NeoUserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                NeoUserModel neoUserModel = new NeoUserModel();
                neoUserModel.setId(rs.getLong("id"));
                neoUserModel.setUsername(rs.getString("username"));
                neoUserModel.setPassword(rs.getString("password"));
                neoUserModel.setRole(rs.getString("role"));
                neoUserModel.setStatus(rs.getString("status"));
                neoUserModel.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                neoUserModel.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return neoUserModel;
            }
        };
    }
	
    public List<NeoUserModel> getAllUsers() {
        String sql = "SELECT * FROM neo_users";
        return jdbcTemplate.query(sql, getRowMapper());
    }
    
    public NeoUserModel findByUsername(String username) {

        String sql = "SELECT * FROM neo_users WHERE username = ?";

        try {
            return jdbcTemplate.queryForObject(sql,getRowMapper(),username);
        } catch (Exception e) {
            return null; // user not found
        }
    }

    
    public UtlityModel InsertRecord(NeoUserModel neoUserModel) {
    	int rtValue = 0;
    	UtlityModel utlityModel = new UtlityModel();
    	String sql = "INSERT INTO neo_users (username,password,role,status,created_at,updated_at) values (?,?,?,?,NOW(),NOW())";
    	Object[] args = {neoUserModel.getUsername(),neoUserModel.getPassword(),neoUserModel.getRole(),neoUserModel.getStatus()};
    	try {
    		rtValue = jdbcTemplate.update(sql, args);
    		if(rtValue == Constants.SUCCESS_STATUS) {
    			utlityModel.setStatus(rtValue);
    			utlityModel.setError(Constants.SUCCESSFULL_INSERT_MSG);
    		}
    		return utlityModel;
    	}catch(Exception e) {
    		utlityModel.setStatus(Constants.ERROR_STATUS);
    		utlityModel.setError(e.getMessage());
    		return utlityModel;
    	}
    }
}
