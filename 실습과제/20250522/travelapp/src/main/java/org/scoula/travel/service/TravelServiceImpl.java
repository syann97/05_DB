package org.scoula.travel.service;

import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import org.scoula.travel.dao.TravelDao;
import org.scoula.travel.domain.TravelImageVO;
import org.scoula.travel.domain.TravelVO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Lombok: final 필드에 대한 생성자 자동 생성
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {

    /**
     * 데이터 액세스를 위한 DAO 객체
     * final 키워드로 불변성 보장, RequiredArgsConstructor로 생성자 주입
     */
    private final TravelDao dao;

    /**
     * 입력 받은 문자열 -> 숫자 변환 메서드
     *
     * @param prompt 사용자에게 보여줄 입력 안내 메시지
     * @return 사용자가 입력한 정수값
     */
    private int getNumber(String prompt) {
        System.out.print(prompt);
        Scanner sc = new Scanner(System.in);
        // nextLine()으로 전체 라인을 읽어 공백 문제 방지
        int num = Integer.parseInt(sc.nextLine());

        return num;
    }


    /**
     * 지역 목록 출력 메서드
     *
     * @param districts 출력할 지역명 리스트
     */
    private void printDistricts(List<String> districts) {
        for (int i = 0; i < districts.size(); i++) {

            // 1부터 시작하는 번호와 함께 지역명 출력
            System.out.printf("%d] %s\n", i + 1, districts.get(i));
        }
    }


    /**
     * 여행지 목록 출력 메서드
     *
     * @param travels 출력할 여행지 목록
     */
    private void printTravels(List<TravelVO> travels) {

        for (TravelVO travel : travels) {
            System.out.printf("%3d %6s  %s\n", travel.getNo(), travel.getDistrict(), travel.getTitle());
        }
    }


    /**
     * 전체 여행지 목록 출력 메서드
     * - DAO에서 모든 여행지 데이터를 조회하여 콘솔에 출력
     */
    @Override
    public void printTravels() {
        // DAO를 통해 전체 여행지 목록 조회
        List<TravelVO> travels = dao.getTravels();
        // 헬퍼 메서드로 정렬된 형태로 출력
        printTravels(travels);
    }


    /**
     * 지역별 여행지 목록 출력 메서드
     * - 먼저 사용 가능한 지역 목록 출력
     * - 사용자로 부터 지역 입력 받음
     */
    @Override
    public void printTravelsByDistrict() {
        // 모든 지역 목록 조회
        List<String> districts = dao.getDistricts();

        // 지역 목록을 번호와 함께 출력
        printDistricts(districts);

        // 지역 번호 입력 받기
        int ix = getNumber("선택: ");

        // 선택된 인덱스에 해당하는 지역명 획득 (1부터 시작하므로 -1)
        String district = districts.get(ix - 1);

        // 해당 지역의 여행지 목록 조회
        List<TravelVO> travels = dao.getTravels(district);

        // 조회된 여행지 목록 출력
        printTravels(travels);
    }

    /**
     * 페이지별 여행지 목록 출력 메서드
     * - 전체 데이터 개수를 기반으로 총 페이지 수 계산
     * - 사용자가 선택한 페이지의 여행지만 출력 (페이지당 10개)
     */
    @Override
    public void printTravelsByPage() {

        // 전체 페이지 수 계산 (페이지당 10개 기준)
        int totalPage = (int) Math.ceil(dao.getTotalCount() / 10.0);
        System.out.printf("총 %d 페이지\n", totalPage);

        // 페이지 번호 입력 받기
        int page = getNumber(String.format("페이지 선택(1~%d): ", totalPage));

        // 선택된 페이지의 여행지 목록 조회
        List<TravelVO> travels = dao.getTravels(page);

        // 조회된 여행지 목록 출력
        printTravels(travels);
    }

    /**
     * 특정 여행지의 상세 정보 출력 메서드
     * - 사용자로부터 여행지 번호 입력 받음
     * - 해당 여행지의 모든 상세 정보와 관련 이미지 출력
     * - 존재하지 않는 번호 입력 시 NoSuchElementException 발생
     */
    @Override
    public void printTravel() {

        // 여행지 번호 입력 받기
        long no = getNumber("관광지 No: ");

        // 여행지 상세 정보 조회
        // orElseThrow()로 데이터가 없으면 예외 발생
        TravelVO travel = dao.getTravel(no).orElseThrow(NoSuchElementException::new);

        // 여행지 기본 정보 출력
        System.out.println("권역: " + travel.getDistrict());
        System.out.println("제목: " + travel.getTitle());
        System.out.println("설명: " + travel.getDescription());
        System.out.println("주소: " + travel.getAddress());
        System.out.println("전화번호: " + travel.getPhone());

        // 관련 이미지 정보 출력
        System.out.println("사진들");
        for (TravelImageVO image : travel.getImages()) {
            // 이미지 번호와 파일명을 정렬하여 출력
            System.out.printf("  [%3d] %s\n", image.getNo(), image.getFilename());
        }
    }
}
