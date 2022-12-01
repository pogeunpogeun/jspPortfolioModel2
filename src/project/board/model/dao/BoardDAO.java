package project.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import project._mybatisConfig.MybatisManager;
import project.board.model.dto.BoardDTO;

public class BoardDAO {
	
	String tableName01 = "board";
	
	public List<BoardDTO> getSelectAll(BoardDTO paramDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", paramDto);
		map.put("tableName01", tableName01);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		List<BoardDTO> list = session.selectList("boardMybatis.getSelectAll",map);
		session.close();
		return list;		
	}
	public void getSelectOne() {
		
	}
	public void setInsert() {
		
	}
	public void setUpdate() {
		
	}
	public void setDelete() {
		
	}
}
