package com.personiv.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.personiv.model.FileItem;

@Repository
@Transactional(readOnly = false)
public class FileUploaderDao extends JdbcDaoSupport{
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }
    
    public void uploadFile(FileItem file) {
    	String sql ="INSERT INTO documents(fileName,content,contentType)values(?,?,?)";
    	jdbcTemplate.update(sql,new Object[] {file.getFileName(),file.getContent(),file.getContentType()});
    }
    
    public List<FileItem> getDocuments(){
    	String sql ="SELECT * FROM documents";
    	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<FileItem>(FileItem.class));
    }

	public FileItem getDocument(Long id) {
		// TODO Auto-generated method stub
		String sql ="SELECT * FROM documents where id =? ";
		return jdbcTemplate.queryForObject(sql,new Object[] {id},  new BeanPropertyRowMapper<FileItem>(FileItem.class));
	}
}
