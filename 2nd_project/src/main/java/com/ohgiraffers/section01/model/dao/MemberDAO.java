package com.ohgiraffers.section01.model.dao;

import com.ohgiraffers.section01.model.dto.MemberDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class MemberDAO {

    Properties prop = new Properties();

    public MemberDAO() {

        try {
            prop.loadFromXML(ClassLoader.getSystemResourceAsStream("mapper/member-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MemberDTO> selectMembersList(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        List<MemberDTO> memberDTOList = null;

        String query = prop.getProperty("selectMembersList");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            
            memberDTOList = new ArrayList<>();
            
            while (rset.next()) {
                MemberDTO memberDTO = new MemberDTO();

                memberDTO.setMemberId(rset.getInt("MEMBER_ID"));
                memberDTO.setName(rset.getString("NAME"));
                memberDTO.setPhoneNumber(rset.getString("PHONE_NUMBER"));
                memberDTO.setMemberType(rset.getInt("MEMBER_TYPE"));

                memberDTOList.add(memberDTO);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }

        return memberDTOList;
    }

    public int selectMemberType(Connection con, int damageType, int memberType) {

        Statement stmt = null;
        int result = 0;

        String query = prop.getProperty("selectMemberType");

        try {
            stmt = con.createStatement();
            result = stmt.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(stmt);
        }

        return result;
    }
}
