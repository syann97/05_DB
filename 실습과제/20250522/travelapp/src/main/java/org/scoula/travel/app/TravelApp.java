package org.scoula.travel.app;

import org.scoula.database.JDBCUtil;
import org.scoula.travel.dao.TravelDao;
import org.scoula.travel.dao.TravelDaoImpl;
import org.scoula.travel.service.TravelService;
import org.scoula.travel.service.TravelServiceImpl;

import java.util.Scanner;

/**
 * 애플리케이션 실행 클래스
 * - Command 패턴을 활용한 메뉴 실행
 * - 계층화 아키텍처 (App -> Service -> DAO) 구현
 */
public class TravelApp {

    Scanner sc = new Scanner(System.in);
    // 비즈니스 로직 처리를 위한 Service 객체
    private TravelService service;
    //메뉴 항목들을 저장하는 배열
    // - MenuItem 객체로 메뉴 제목과 실행할 명령을 함께 관리
    private MenuItem[] menu;

    // TravelApp 생성자
    public TravelApp() {

        // 계층별 객체 생성 및 의존성 주입
        TravelDao dao = new TravelDaoImpl();           // DAO 계층 생성
        service = new TravelServiceImpl(dao);          // Service 계층 생성 (DAO 주입)

        // 메뉴 항목 배열 초기화
        // Command 패턴: 각 메뉴 항목에 실행할 명령(Runnable)을 연결
        menu = new MenuItem[]{
                new MenuItem("전체목록", service::printTravels),           // 메서드 레퍼런스로 명령 연결
                new MenuItem("페이지목록", service::printTravelsByPage),   // 페이징 조회
                new MenuItem("권역별목록", service::printTravelsByDistrict), // 지역별 필터링
                new MenuItem("상세보기", service::printTravel),            // 상세 정보 조회
                new MenuItem("종료", this::exit),                        // 애플리케이션 종료
        };
    }

    // 실행용 메서드
    public static void main(String[] args) {
        TravelApp app = new TravelApp();
        app.run();
    }

    /**
     * 애플리케이션 메인 실행 루프
     * - 무한 루프를 통한 지속적인 사용자 인터랙션
     * - 메뉴 출력 -> 사용자 선택 -> 명령 실행 반복
     */
    public void run() {
        while (true) {
            printMenu();              // 메뉴 출력
            int ix = getMenuIndex();  // 사용자 선택 입력 받기

            // Command 패턴: 선택된 메뉴의 명령 객체 획득 및 실행
            Runnable command = menu[ix].getCommand();
            command.run();            // 해당 명령 실행
        }
    }

    /**
     * 애플리케이션 종료 처리 메서드
     */
    public void exit() {
        sc.close();          // Scanner 리소스 해제
        JDBCUtil.close();    // 데이터베이스 연결 해제
        System.exit(0);      // 정상 종료 (종료 코드 0)
    }

    /**
     * 메뉴 출력 메서드
     */
    public void printMenu() {
        System.out.println("==========================================================================");

        // 메뉴 배열을 순회하며 번호와 제목 출력
        for (int i = 0; i < menu.length; i++) {
            MenuItem menuItem = menu[i];

            // 1부터 시작하는 번호와 메뉴 제목 출력
            System.out.printf("%d) %s ", i + 1, menuItem.getTitle());
        }
        System.out.println();
        System.out.println("==========================================================================");
    }

    /**
     * 메뉴 번호 입력 메서드
     * @return 선택된 메뉴의 배열 인덱스 (0부터 시작)
     */
    public int getMenuIndex() {
        System.out.print("선택: ");
        String line = sc.nextLine();

        // 사용자 입력 (1부터 시작)을 배열 인덱스 (0부터 시작)로 변환
        int ix = Integer.parseInt(line) - 1;
        return ix;
    }
}
