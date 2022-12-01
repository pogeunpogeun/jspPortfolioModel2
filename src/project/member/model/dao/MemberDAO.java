package project.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import project.common.DB;
import project.member.model.dto.MemberDTO;

public class MemberDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		public int getTotalRecord(MemberDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "";
				sql += "select count(*) counter from member where 1 = 1 ";
				
				if(paramDto.getSearchGubun().equals("id")) {
					sql += "and id like ? ";
				} else if(paramDto.getSearchGubun().equals("name")) {
					sql += "and name like ? ";
				} else if(paramDto.getSearchGubun().equals("id_name")) {
					sql += "and (id like ? or name like ?) ";
				}
				
				pstmt = conn.prepareStatement(sql);
				
				if(paramDto.getSearchGubun().equals("id")) {
					pstmt.setString(1, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("name")) {
					pstmt.setString(1, '%' + paramDto.getSearchData());
				} else if(paramDto.getSearchGubun().equals("id_name")) {
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
		public ArrayList<MemberDTO> getSelectAll(MemberDTO paramDto) {
			ArrayList<MemberDTO> list = new ArrayList<>();
			conn = DB.dbConn();
			try {
				String basicSql = "";		
				basicSql += "select * ";	
				basicSql += "from member where 1 = 1 ";
				
				if(paramDto.getSearchGubun().equals("id")) {
					basicSql += " and id like ? ";
				} else if(paramDto.getSearchGubun().equals("name")) {
					basicSql += " and name like ? ";
				} else if(paramDto.getSearchGubun().equals("id_name")) {
					basicSql += " and (id like ? or name like ?) ";
				}

				basicSql += "order by no desc";
				
				String sql = "";
				sql += " select * from (select A.*, Rownum rnum from( ";
				sql += basicSql;
				sql += " ) A) where rnum >= ? and rnum <= ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				int k = 0;
				if(paramDto.getSearchGubun().equals("id")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("name")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("id_name")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				}
				pstmt.setInt(++k, paramDto.getStartRecord());
				pstmt.setInt(++k, paramDto.getLastRecord());
			
				rs = pstmt.executeQuery();
				while(rs.next()) {
					MemberDTO dto = new MemberDTO();
					dto.setNo(rs.getInt("no"));
					dto.setId(rs.getString("id"));
					dto.setPasswd(rs.getString("passwd"));
					dto.setName(rs.getString("name"));
					dto.setJumin1(rs.getString("jumin1"));
					dto.setJumin2(rs.getString("jumin2"));
					dto.setPhone1(rs.getString("phone1"));
					dto.setPhone2(rs.getString("phone2"));
					dto.setPhone3(rs.getString("phone3"));
					dto.setEmail1(rs.getString("email1"));
					dto.setEmail2(rs.getString("email2"));
					dto.setPostcode(rs.getString("postcode"));
					dto.setAddress(rs.getString("address"));
					dto.setDetailAddress(rs.getString("detailAddress"));
					dto.setExtraAddress(rs.getString("extraAddress"));
					dto.setRegiDate(rs.getDate("regiDate"));
					list.add(dto);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return list;
		}
		public MemberDTO getSelectOne(MemberDTO paramDto) {
			MemberDTO dto = new MemberDTO();
			conn = DB.dbConn();
			try {
				String sql = "";
				sql += "select * from ( ";
				///////////////////////////////////////////////////////////
				sql += "select m.*, ";
				
				sql += "LAG(no) OVER (order by no desc) preNo, ";
				sql += "LAG(name) OVER (order by no desc) preName, ";
				sql += "LEAD(no) OVER (order by no desc) nxtNo, ";
				sql += "LEAD(name) OVER (order by no desc) nxtName ";
							
				sql += "from member m where 1 = 1 ";
				
				if(paramDto.getSearchGubun().equals("id")) {
					sql += "and id like ? ";
				} else if(paramDto.getSearchGubun().equals("name")) {
					sql += "and name like ? ";
				} else if(paramDto.getSearchGubun().equals("id_name")) {
					sql += "and (id like ? or name like ?) ";
				}
								
				sql += "order by no desc";
				//////////////////////////////////////////////////////////
				sql += ") where no = ?";
				pstmt = conn.prepareStatement(sql);
				
				int k = 0;
				if(paramDto.getSearchGubun().equals("id")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("name")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				} else if(paramDto.getSearchGubun().equals("id_name")) {
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
					pstmt.setString(++k, '%' + paramDto.getSearchData() + '%');
				}
				pstmt.setInt(++k, paramDto.getNo());

				rs = pstmt.executeQuery();
				if(rs.next()) {
					dto.setNo(rs.getInt("no"));
					dto.setId(rs.getString("id"));
					dto.setPasswd(rs.getString("passwd"));
					dto.setName(rs.getString("name"));
					dto.setJumin1(rs.getString("jumin1"));
					dto.setJumin2(rs.getString("jumin2"));
					dto.setPhone1(rs.getString("phone1"));
					dto.setPhone2(rs.getString("phone2"));
					dto.setPhone3(rs.getString("phone3"));
					dto.setEmail1(rs.getString("email1"));
					dto.setEmail2(rs.getString("email2"));
					dto.setPostcode(rs.getString("postcode"));
					dto.setAddress(rs.getString("address"));
					dto.setDetailAddress(rs.getString("detailAddress"));
					dto.setExtraAddress(rs.getString("extraAddress"));
					dto.setRegiDate(rs.getDate("regiDate"));
					dto.setPreNo(rs.getInt("preNo"));
					dto.setPreName(rs.getString("preName"));
					dto.setNxtNo(rs.getInt("nxtNo"));
					dto.setNxtName(rs.getString("nxtName"));
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return dto;
		}
		public MemberDTO getLogin(MemberDTO paramDto) {
			MemberDTO dto = new MemberDTO();
			conn = DB.dbConn();
			try {
				String sql = "select no, id, name from member where id = ? and passwd = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paramDto.getId());
				pstmt.setString(2, paramDto.getPasswd());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					dto.setNo(rs.getInt("no"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return dto;
		}
		public int setInsert(MemberDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "insert into member values(seq_member.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paramDto.getId());
				pstmt.setString(2, paramDto.getPasswd());
				pstmt.setString(3, paramDto.getName());
				pstmt.setString(4, paramDto.getJumin1());
				pstmt.setString(5, paramDto.getJumin2());
				pstmt.setString(6, paramDto.getPhone1());
				pstmt.setString(7, paramDto.getPhone2());
				pstmt.setString(8, paramDto.getPhone3());
				pstmt.setString(9, paramDto.getEmail1());
				pstmt.setString(10, paramDto.getEmail2());
				pstmt.setString(11, paramDto.getPostcode());
				pstmt.setString(12, paramDto.getAddress());
				pstmt.setString(13, paramDto.getDetailAddress());
				pstmt.setString(14, paramDto.getExtraAddress());
				result = pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return result;
		}
		public int setUpdate(MemberDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "update member set phone1=?, phone2=?, phone3=?, email1=?, email2=?, postcode=?, address=?, ";
				sql += "detailAddress=?, extraAddress=? where no=? and passwd=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,paramDto.getPhone1());
				pstmt.setString(2,paramDto.getPhone2());
				pstmt.setString(3,paramDto.getPhone3());
				pstmt.setString(4,paramDto.getEmail1());
				pstmt.setString(5,paramDto.getEmail2());
				pstmt.setString(6,paramDto.getPostcode());
				pstmt.setString(7,paramDto.getAddress());
				pstmt.setString(8,paramDto.getDetailAddress());
				pstmt.setString(9,paramDto.getExtraAddress());
				pstmt.setInt(10,paramDto.getNo());
				pstmt.setString(11,paramDto.getPasswd());
				result = pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return result;
		}
		public int setDelete(MemberDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "delete from member where no=? and passwd=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, paramDto.getNo());
				pstmt.setString(2, paramDto.getPasswd());
				result = pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return result;
		}
		public int getIdCheckWin(MemberDTO paramDto) {
			int result = 0;
			conn = DB.dbConn();
			try {
				String sql = "select no from member where id= ?";				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paramDto.getId());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					result = rs.getInt("no");
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DB.dbConnClose(rs, pstmt, conn);
			}
			return result;
		}
}
