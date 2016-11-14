package com.example.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alokkulkarni on 30/10/16.
 */
public class UserRowMapper implements org.springframework.jdbc.core.RowMapper<UserDetails> {
    @Override
    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserDetails(rs.getLong("id"),
                rs.getString("firstName"),
                rs.getString("lastName"));
    }
}
