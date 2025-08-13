package com.ohgiraffers.section01.model.dao;

import com.ohgiraffers.section01.model.dto.DamageTypeDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class DamageTypeDAO {

    Properties prop = new Properties();

    public DamageTypeDAO() {
        try {
            prop.loadFromXML(ClassLoader.getSystemResourceAsStream("mapper/damageType-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DamageTypeDTO> selectDamageTypeList(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        List<DamageTypeDTO> damageTypeDTOList = null;

        String query = prop.getProperty("selectDamageTypeList");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            damageTypeDTOList = new ArrayList<>();

            while (rset.next()) {
                DamageTypeDTO damageTypeDTO = new DamageTypeDTO();

                damageTypeDTO.setDamageTypeId(rset.getInt("DAMAGE_TYPE_ID"));
                damageTypeDTO.setPenalty(rset.getString("PENALTY"));
                damageTypeDTO.setScore(rset.getInt("SCORE"));
                damageTypeDTO.setMemberType(rset.getInt("MEMBER_TYPE"));

                damageTypeDTOList.add(damageTypeDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }

        return damageTypeDTOList;
    }
}
