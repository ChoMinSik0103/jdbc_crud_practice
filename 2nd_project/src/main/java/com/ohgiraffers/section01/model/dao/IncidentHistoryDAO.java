package com.ohgiraffers.section01.model.dao;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class IncidentHistoryDAO {

    Properties prop = new Properties();

    public IncidentHistoryDAO() {
        try {
            prop.loadFromXML(ClassLoader.getSystemResourceAsStream("mapper/incidentHistory-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertIncidentHistory(Connection con, Scanner sc) {

        System.out.println(" Usage : A(회원현황 )/ B(피해유형) 내용 참조하여 입력해 주세요.");
        System.out.println(" 입력예시 : 일시 / 가해자회원번호 / 피해자회원번호 / 피해유형번호");

        String inputArg = sc.nextLine();

        String[] splitArg = inputArg.split("/");

        String incidentTimestamp = splitArg[0];
        int perpetratorId = Integer.parseInt(splitArg[1].trim());
        int victimId = Integer.parseInt(splitArg[2].trim());
        int damageTypeId = Integer.parseInt(splitArg[3].trim());

        Date convertTimestamp = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        System.out.println("incidentTimestamp = " + incidentTimestamp);
        
        try {
            convertTimestamp = simpleDateFormat.parse(incidentTimestamp);
//            System.out.println("convertTimestamp = " + convertTimestamp);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (inputDataCheck(con, damageTypeId, perpetratorId) <= 0) {
            System.out.println("입력데이터 오류입니다. ");
            System.out.println("가해자 ID : " + perpetratorId + ", 피해유형 : " + damageTypeId);
            return -1;
        }

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        String query = prop.getProperty("insertIncidentHistory");
        int insertSeq = 0;

        try {
            pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            java.sql.Date sqlDate = new java.sql.Date(convertTimestamp.getTime());

            pstmt.setDate(1, (java.sql.Date) sqlDate);
            pstmt.setInt(2, perpetratorId);
            pstmt.setInt(3, victimId);
            pstmt.setInt(4, damageTypeId);
            pstmt.setInt(5, damageTypeId);

            if (pstmt.executeUpdate() > 0) {
                // insert 된 데이터에 대한 확인
                rset = pstmt.getGeneratedKeys();

                if (rset.next()) {
                    insertSeq = rset.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
        }

        return insertSeq;
    }

    public int inputDataCheck(Connection con, int damageTypeId, int perpetratorId) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        int result = 0;

        String query = prop.getProperty("selectMemberType");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, perpetratorId);
            pstmt.setInt(2, damageTypeId);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                result = rset.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
        }

        return result;
    }

    public void checkInsertIncidentHistory(Connection con, int selectId) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        String query = prop.getProperty("checkInsertIncidentHistory");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, selectId);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                int incidentId = rset.getInt(1);
                Date incidentTimestamp = rset.getDate(2);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String convertDate = sdf.format(incidentTimestamp);

                String perpetratorName = rset.getString(3);
                String victimName = rset.getString(4);
                int damageScore = rset.getInt(5);
                String penatly = rset.getString(6);

                System.out.printf("사고순번 %2d    사고일자 : %8s    가해자명 : %-10s  피해자명 : %-10s  피해점수 : %02d   피해유혀 : %-20s \n",
                        incidentId, convertDate, perpetratorName, victimName, damageScore, penatly);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
        }

    }

    public void incidentSelectList (Connection con, String inDate, int inVictimId) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        String query = null;

        if (inDate != null) {
            query = prop.getProperty("incidentSelectListDate");

            try {
                pstmt = con.prepareStatement(query);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date convertDate = sdf.parse(inDate);
                java.sql.Date sqlDate = new java.sql.Date(convertDate.getTime());

                pstmt.setDate(1, sqlDate);

                rset = pstmt.executeQuery();

                while (rset.next()) {
                    int incidentId = rset.getInt(1);
                    Date incidentTimestamp = rset.getDate(2);

                    String perpetratorName = rset.getString(3);
                    String victimName = rset.getString(4);
                    int damageScore = rset.getInt(5);
                    String penatly = rset.getString(6);

                    System.out.printf("사고순번 %2d    사고일자 : %8s    가해자명 : %-10s  피해자명 : %-10s  피해점수 : %02d   피해유형 : %-20s \n",
                            incidentId, inDate, perpetratorName, victimName, damageScore, penatly);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } finally {
                close(rset);
                close(pstmt);
            }
        }
        else if (inVictimId != 0) {
            query = prop.getProperty("incidentSelectListVictimId");

            try {
                pstmt = con.prepareStatement(query);

                pstmt.setInt(1, inVictimId);

                rset = pstmt.executeQuery();

                while (rset.next()) {
                    int incidentId = rset.getInt(1);
                    Date incidentTimestamp = rset.getDate(2);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

                    String dateString = simpleDateFormat.format(incidentTimestamp);

                    String perpetratorName = rset.getString(3);
                    String victimName = rset.getString(4);
                    int damageScore = rset.getInt(5);
                    String penatly = rset.getString(6);

                    System.out.printf("사고순번 %2d    사고일자 : %8s    가해자명 : %-10s  피해자명 : %-10s  피해점수 : %02d   피해유형 : %-20s \n",
                            incidentId, dateString, perpetratorName, victimName, damageScore, penatly);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                close(rset);
                close(pstmt);
            }
        }
        else {
            // 전체 출력
            query = prop.getProperty("incidentSelectList");

            try {
                pstmt = con.prepareStatement(query);

                rset = pstmt.executeQuery();
                
                while (rset.next()) {
                    int incidentId = rset.getInt(1);
                    Date incidentTimestamp = rset.getDate(2);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

                    String dateString = simpleDateFormat.format(incidentTimestamp);

                    String perpetratorName = rset.getString(3);
                    String victimName = rset.getString(4);
                    int damageScore = rset.getInt(5);
                    String penatly = rset.getString(6);

                    System.out.printf("사고순번 %2d    사고일자 : %8s    가해자명 : %-10s  피해자명 : %-10s  피해점수 : %02d   피해유형 : %-20s \n",
                            incidentId, dateString, perpetratorName, victimName, damageScore, penatly);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int incidentUpdate(Connection con, Scanner sc, int updateSeq) {

        PreparedStatement pstmt = null;
        int resultUpdate = 0;

        System.out.println(" 수정할 내용을 입력하세요. ");
        System.out.println(" Usage : A(회원현황 )/ B(피해유형) 내용 참조하여 입력해 주세요.");
        System.out.println(" 입력예시 : 사고순번 / 일시 / 가해자회원번호 / 피해자회원번호 / 피해유형번호");
        String query = prop.getProperty("updateIncident");

        String updateArg = sc.nextLine();

        String[] splitArg = updateArg.split("/");

        int incidentSeq = Integer.parseInt(splitArg[0].trim());
        String incidentTimestamp = splitArg[1].trim();
        int perpetratorId = Integer.parseInt(splitArg[2].trim());
        int victimId = Integer.parseInt(splitArg[3].trim());
        int damageTypeId = Integer.parseInt(splitArg[4].trim());

        query = prop.getProperty("updateIncident");

        if (inputDataCheck(con, damageTypeId, perpetratorId) <= 0) {
            System.out.println("입력데이터 오류입니다. ");
            System.out.println("가해자 ID : " + perpetratorId + ", 피해유형 : " + damageTypeId);
            return -1;
        }

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, incidentTimestamp);
            pstmt.setInt(2, perpetratorId);
            pstmt.setInt(3, victimId);
            pstmt.setInt(4, damageTypeId);
            pstmt.setInt(5, damageTypeId);
            pstmt.setInt(6, incidentSeq);

            resultUpdate = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return resultUpdate;
    }


    public int incidentDelete (Connection con, Scanner sc, int deleteSeq) {

        PreparedStatement pstmt = null;
        int resultDelete = 0;

        String query = prop.getProperty("deleteIncident");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, deleteSeq);

            resultDelete = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return resultDelete;
    }
}
