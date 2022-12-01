package project.memo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import project.common.DB;
import project.memo.model.dto.MemoDTO;

public class MemoDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
		public int getTotalRecord(MemoDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "";
				sql += "select count(*) counter from memo where 1 = 1 ";
				
				if(paramDto.getSearchGubun().equals("subject")) {
					sql += "and subject like ? ";
				} else if(paramDto.getSearchGubun().equals("content")) {
					sql += "and content like ? ";
				} else if(paramDto.getSearchGubun().equals("sub_con")) {
					sql += "and (subject like ? or content like ?) ";
				}
				
				pstmt = conn.prepareStatement(sql);
				
				if(paramDto.getSearchGubun().equals("subject")) {
					pstmt.setString(1, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("content")) {
					pstmt.setString(1, '%' + paramDto.getSearchData());
				} else if(paramDto.getSearchGubun().equals("sub_con")) {
					pstmt.setString(1, '%' + paramDto.getSearchData() + '%');
					pstmt.setString(2, '%' + paramDto.getSearchData() + '%');
				}
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					result = rs.getInt("counter");
				} 
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DB .dbConnClose(rs, pstmt, conn);
			}
			return result;
		}
		public ArrayList<MemoDTO> getSelectAll(MemoDTO paramDto) {
			ArrayList<MemoDTO> list = new ArrayList<>();
			conn = DB.dbConn();
			try {
				String basicSql = "";		
				basicSql += "select * from memo where 1 = 1 ";	

				if(paramDto.getSearchGubun().equals("subject")) {
					basicSql += " and subject like ? ";
				} else if(paramDto.getSearchGubun().equals("content")) {
					basicSql += " and content like ? ";
				} else if(paramDto.getSearchGubun().equals("subject_content")) {
					basicSql += " and (subject like ? or content like ?) ";
				}
				basicSql += " order by memoNo desc ";
				
				String sql = "";
				sql += "select * from (select A.*, Rownum rnum from ( ";
				sql += basicSql;
				sql += ") A) where rnum >= ? and rnum <= ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				int k = 0;
				if(paramDto.getSearchGubun().equals("subject")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("content")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("subject_content")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				}
				pstmt.setInt(++k, paramDto.getStartRecord());
				pstmt.setInt(++k, paramDto.getLastRecord());
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					MemoDTO dto = new MemoDTO();
					dto.setMemoNo(rs.getInt("memoNo"));
					dto.setSubject(rs.getString("subject"));
					dto.setWriter(rs.getString("writer"));
					dto.setContent(rs.getString("content"));
					dto.setRegiDate(rs.getDate("regiDate"));
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return list;			
		}
		public MemoDTO getSelectOne(MemoDTO paramDto) {
			MemoDTO dto = new MemoDTO();
			conn = DB.dbConn();
			try {
				String sql = "";
				sql += "select * from ( ";
				////////////////////////////////////////////////////////////////
				sql += "select m.*, ";
				
				sql += "LAG(memoNo) OVER (order by memoNo desc) preNo, ";
				sql += "LAG(subject) OVER (order by memoNo desc) preName, ";
				sql += "LEAD(memoNo) OVER (order by memoNo desc) nxtNo, ";
				sql += "LEAD(subject) OVER (order by memoNo desc) nxtName ";
				
				sql += "from memo m where 1 = 1 ";
				
				if(paramDto.getSearchGubun().equals("subject")) {
					sql += " and subject like ? ";
				} else if(paramDto.getSearchGubun().equals("content")) {
					sql += " and content like ? ";
				} else if(paramDto.getSearchGubun().equals("subject_content")) {
					sql += " and (subject like ? or content like ?) ";
				}
				
				sql += "order by memoNo desc";
				////////////////////////////////////////////////////////////////
				sql += ") where memoNo = ?";
				
				pstmt = conn.prepareStatement(sql);
				
				int k = 0;
				if(paramDto.getSearchGubun().equals("subject")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("content")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("subject_content")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				}
				pstmt.setInt(++k, paramDto.getMemoNo());
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					dto.setMemoNo(rs.getInt("memoNo"));
					dto.setSubject(rs.getString("subject"));
					dto.setWriter(rs.getString("writer"));
					dto.setContent(rs.getString("content"));
					dto.setRegiDate(rs.getDate("regiDate"));
					dto.setPreNo(rs.getInt("preNo"));
					dto.setNxtNo(rs.getInt("nxtNo"));
					dto.setPreName(rs.getString("preName"));
					dto.setNxtName(rs.getString("nxtName"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return dto;
		}
		public int setInsert(MemoDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "insert into memo values (seq_memo.nextval,?,?,?,sysdate)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paramDto.getSubject());
				pstmt.setString(2, paramDto.getWriter());
				pstmt.setString(3, paramDto.getContent());
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return result;
		}
		public int setUpdate(MemoDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "update memo set subject = ?, content = ? where memoNo = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paramDto.getSubject());
				pstmt.setString(2, paramDto.getContent());
				pstmt.setInt(3, paramDto.getMemoNo());
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return result;
		}
		public int setDelete(MemoDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "delete from memo where memoNo = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, paramDto.getMemoNo());
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return result;
		}
}
