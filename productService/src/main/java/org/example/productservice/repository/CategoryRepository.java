package org.example.productservice.repository;

import org.example.productservice.dto.CategoryDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Thêm mới Category
    public int createCategory(CategoryDTO categoryDTO) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        return jdbcTemplate.update(sql, categoryDTO.getName());
    }

    // Lấy tất cả các Category
    public List<CategoryDTO> getAllCategories() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CategoryDTO(
                rs.getInt("id"),
                rs.getString("name"),
                null
        ));
    }

    // Lấy Category theo ID
    public CategoryDTO getCategoryById(Integer id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new CategoryDTO(
                rs.getInt("id"),
                rs.getString("name"),
                null
        ));
    }

    // Cập nhật Category
    public int updateCategory(Integer id, CategoryDTO categoryDTO) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, categoryDTO.getName(), id);
    }

    // Xóa Category
    public int deleteCategory(Integer id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
