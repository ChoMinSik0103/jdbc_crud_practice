package com.ohgiraffers.section01.model.run;

import com.ohgiraffers.section01.model.dao.DamageTypeDAO;
import com.ohgiraffers.section01.model.dao.IncidentHistoryDAO;
import com.ohgiraffers.section01.model.dao.MemberDAO;
import com.ohgiraffers.section01.model.dto.DamageTypeDTO;
import com.ohgiraffers.section01.model.dto.MemberDTO;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

//        String currentDirectory = System.getProperty("user.dir");
//        System.out.println("현재 작업 디렉토리: " + currentDirectory);

        Connection con = getConnection();
        IncidentHistoryDAO incidentDAO = new IncidentHistoryDAO();
        MemberDAO memberDAO = new MemberDAO();

        Scanner sc = new Scanner(System.in);

        // 입력번호 오류 횟수 시정. 5회이상이면 프로세스 종료
        int retryCnt = 0;
        int stopflag = 0;

        while (true) {
            List<MemberDTO> memberDTOList = memberDAO.selectMembersList(con);
            System.out.println("============================== 회원현황 ==============================");
            for (MemberDTO memberDTO : memberDTOList) {
                System.out.println(memberDTO.toString());
            }
            System.out.println("============================== 회원현황 ==============================");

            DamageTypeDAO damageTypeDAO = new DamageTypeDAO();
            List<DamageTypeDTO> damageTypeDTOList = damageTypeDAO.selectDamageTypeList(con);
            System.out.println("============================== 피해유형 ==============================");
            for (DamageTypeDTO damageTypeDTO : damageTypeDTOList) {
                System.out.println(damageTypeDTO.toString());
            }
            System.out.println("============================== 피해유형 ==============================");

            System.out.println("============================   메뉴선택   ============================");
            System.out.println(" 1. 사고 내역 입력");
            System.out.println(" 2. 입력 내역 확인");
            System.out.println(" 3. 사고 내역 수정");
            System.out.println(" 4. 사고 내역 삭제");
            System.out.println(" 5. 프로세스 종료");
            System.out.println("=====================================================================");
            System.out.print("메뉴를 입력하세요 : ");

            int selectNum = sc.nextInt();
            sc.nextLine();

            switch (selectNum) {
                case 1 :

                    // resultSeq 에 insert seq 를 받아온다
                    int resultSeq = incidentDAO.insertIncidentHistory(con, sc);

                    if (resultSeq > 0) {
                        System.out.println("데이터 입력처리 되었습니다.");
                        System.out.print("입력데이터 확인하시겠습니까? ");
                    }
                    else {
                        break;
                    }

                    String insertCheck = sc.nextLine();

                    if ("Y".equalsIgnoreCase(insertCheck)) {

                        System.out.println("============================== 입력데이터 =============================");
                        incidentDAO.checkInsertIncidentHistory(con, resultSeq);
                        System.out.println("=====================================================================");
                    }
                    else {
                        System.out.print("메인메뉴로 돌아가시겠습니까 ? ");

                        String goHome = sc.nextLine();
                        if ("Y".equalsIgnoreCase(goHome))  break;
                    }

                    break;

                case 2 :
                    System.out.println("내역 확인 조건을 입력하세요 : ");
                    System.out.println("1 : 날짜로조회  2 : 가해자 회원 ID로 조회");

                    int insertListCheck = sc.nextInt();
                    sc.nextLine();

                    String inputdate = null;
                    int inputVictimId = 0;

                    if (insertListCheck == 1) {
                        System.out.print("조회 날짜를 입력하세요 : ");
                        inputdate = sc.nextLine();
                    }
                    else if (insertListCheck == 2) {
                        System.out.print("가해자 회원번호 를 입력하세요 : ");
                        inputVictimId = sc.nextInt();
                    }
                    else {
                        System.out.println("입력오류");
                    }

                    System.out.println("======================================================================");
                    incidentDAO.incidentSelectList(con, inputdate, inputVictimId);
                    System.out.println("======================================================================");

                    break;

                case 3 :
                    System.out.println("============================== 사고 내역  =============================");
                    // 전체 조회
                    incidentDAO.incidentSelectList(con, null, 0);
                    System.out.println("============================== 사고 내역  =============================");
                    System.out.print("수정할 사고번호를 입력하세요. : ");

                    int inputUpdate = sc.nextInt();
                    sc.nextLine();
                    int resultUpdate = incidentDAO.incidentUpdate(con, sc, inputUpdate);

                    if (resultUpdate > 0) {
                        System.out.println("정상 Update 처리되었습니다. ");
                    }
                    break;

                case 4:
                    System.out.println("============================== 사고 내역  =============================");
                    // 전체 조회
                    incidentDAO.incidentSelectList(con, null, 0);
                    System.out.println("============================== 사고 내역  =============================");
                    System.out.print("삭제할 사고번호를 입력하세요. : ");

                    int inputDelete = sc.nextInt();
                    sc.nextLine();

                    int resultDelete = incidentDAO.incidentDelete(con, sc, inputDelete);
                    if (resultDelete > 0) {
                        System.out.println("정상 Delete 처리되었습니다. ");
                    }

                    break;

                case 5 :
                    stopflag = 1;
                    break;

                default:
                    System.out.println("메뉴 선택번호 확인후 다시 입력 바랍니다. 입력번호 : " + selectNum);
                    retryCnt++;
                    break;
            }

            if (retryCnt > 5) {
                System.out.println("메뉴 선택번호 오류 입력 횟수가 5회를 넘어 프로세스를 종료합니다. ");
                break;
            }

            // 메뉴에서 종료 선택했을 때
            if (stopflag == 1) break;

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\n");

        }

        close(con);
    }

}
