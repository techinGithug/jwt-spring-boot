package com.tutorial.jwt.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tutorial.jwt.model.UserDetail;

@Repository
public class MainDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public UserDetail getUsername(String username) {
		String qr  = " SELECT * FROM GENEUS.USER ";
			   qr += " WHERE USERNAME = '"+username+"' ";
		System.out.println("Query :: "+qr);
		List<UserDetail> rs = jdbcTemplate.query(qr, new getAllUserMapper());
		return rs.size() == 0 ? null : rs.get(0);	
	}
	
	@SuppressWarnings("rawtypes")
	private class getAllUserMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserDetail user = new UserDetail();
			user.setId(rs.getInt("ID"));
			user.setUsername(rs.getString("USERNAME"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setEmail(rs.getString("EMAIL"));
			return user;
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<UserDetail> getUsers() {
		StringBuilder qr = new StringBuilder();
		qr.append(" select * from geneus.user ");
		System.out.println("Query :: getUsers :: " + qr.toString());
		List<UserDetail> rs = jdbcTemplate.query(qr.toString(), new getAllUserMapper());
		return rs;
	}
	
}
