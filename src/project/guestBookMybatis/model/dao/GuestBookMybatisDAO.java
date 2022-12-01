package project.guestBookMybatis.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import project._mybatisConfig.MybatisManager;
import project.guestBookMybatis.model.dto.GuestBookMybatisDTO;

public class GuestBookMybatisDAO {
	
	String tableName01 = "guestBook";
	public int getTotalRecord(GuestBookMybatisDTO paramDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", paramDto);
		map.put("tableName01", tableName01);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.selectOne("guestBookMybatis.getTotalRecord",map);
		session.close();
		return result;
	}
	public List<GuestBookMybatisDTO> getSelectAll(GuestBookMybatisDTO paramDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", paramDto);
		map.put("tableName01", tableName01);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		List<GuestBookMybatisDTO> list = session.selectList("guestBookMybatis.getSelectAll",map);
		session.close();
		return list;
	}
	public GuestBookMybatisDTO getSelectOne(GuestBookMybatisDTO paramDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", paramDto);
		map.put("tableName01", tableName01);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		GuestBookMybatisDTO dto = session.selectOne("guestBookMybatis.getSelectOne",map);
		session.close();
		return dto;
	}
	public int setInsert(GuestBookMybatisDTO paramDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", paramDto);
		map.put("tableName01", tableName01);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.insert("guestBookMybatis.setInsert",map);
		session.commit();
		session.close();
		return result;
	}
	public int setUpdate(GuestBookMybatisDTO paramDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", paramDto);
		map.put("tableName01", tableName01);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.update("guestBookMybatis.setUpdate",map);
		session.commit();
		session.close();
		return result;
	}
	public int setDelete(GuestBookMybatisDTO paramDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", paramDto);
		map.put("tableName01", tableName01);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.delete("guestBookMybatis.setDelete",map);
		session.commit();
		session.close();
		return result;
	}
}
