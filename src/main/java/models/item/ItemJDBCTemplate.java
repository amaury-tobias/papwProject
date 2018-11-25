package models.item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ItemJDBCTemplate implements JDBCTemplateInt<Item> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Item> findAll() {
        String sql = "SELECT * FROM item";
        List<Item> items = jdbcTemplate.query(sql, new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getFloat("price"));
                item.setValoration(rs.getInt("valoration"));
                item.setSearchedTimes(rs.getInt("searchedTimes"));
                item.setSearchedTimes(rs.getInt("viewedTimes"));

                //item.setPicture1(rs.getBytes("picture1"));
                //item.setPicture2(rs.getBytes("picture2"));
                //item.setPicture2(rs.getBytes("picture3"));

                return item;
            }
        });
        return items;
    }

    public List<Item> findhighlights() {
        String sql = "SELECT * FROM item ORDER BY searchedTimes desc LIMIT 6";
        List<Item> items = jdbcTemplate.query(sql, new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getFloat("price"));
                item.setValoration(rs.getInt("valoration"));
                item.setSearchedTimes(rs.getInt("searchedTimes"));
                item.setSearchedTimes(rs.getInt("viewedTimes"));

                return item;
            }
        });
        return items;
    }

    public void create(Item item) {
        String sql = "INSERT INTO item VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = new Object[] { 0, item.getName(), item.getDescription(), item.getPrice(),
                item.getValoration(), item.getSearchedTimes(), item.getViewedTimes(), item.getPicture1(),
                item.getPicture2(), item.getPicture3(), item.getVideoPath() };
        jdbcTemplate.update(sql, params);
    }

    public Item getItemImage(int id) {
        String sql = "SELECT * FROM item WHERE id = ?";
        if (id == 0) {
            return new Item();
        }
        return jdbcTemplate.queryForObject(sql, new Object[] { id }, new RowMapper<Item>() {
            @Override
            public models.item.Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setPicture1(rs.getBytes("picture1"));
                item.setPicture2(rs.getBytes("picture2"));
                item.setPicture3(rs.getBytes("picture3"));
                return item;
            }
        });
    }

}