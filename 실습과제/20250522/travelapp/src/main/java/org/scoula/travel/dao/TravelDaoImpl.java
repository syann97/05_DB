package org.scoula.travel.dao;

import org.scoula.database.JDBCUtil;
import org.scoula.travel.domain.TravelImageVO;
import org.scoula.travel.domain.TravelVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TravelDaoImpl implements TravelDao {
    Connection conn = JDBCUtil.getConnection();
    /**
     * ResultSet -> TravelImageVO 객체 매핑하 메서드
     *
     * @param rs 데이터베이스 쿼리 결과셋
     * @return 결과셋의 현재 행을 기반으로 생성된 TravelImageVO 객체
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    private TravelImageVO mapImage(ResultSet rs) throws SQLException {
        return TravelImageVO.builder()
                .no(rs.getLong("tino"))              // 이미지 번호
                .filename(rs.getString("filename"))  // 파일명
                .travelNo(rs.getLong("travel_no"))   // 연관된 여행지 번호
                .build();
    }

    @Override
    public void insert(TravelVO travel) {
        String sql = "insert into tbl_travel(no, district,title,description, address, phone) values(?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, travel.getNo());
            pstmt.setString(2, travel.getDistrict());
            pstmt.setString(3, travel.getTitle());
            pstmt.setString(4, travel.getDescription());
            pstmt.setString(5, travel.getAddress());
            pstmt.setString(6, travel.getPhone());

            int count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void insertImage(TravelImageVO image) {
        String sql = "insert into tbl_travel_image(filename, travel_no) values(?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, image.getFilename());
            pstmt.setLong(2, image.getTravelNo());

            int count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalCount() {
        String sql = "select count(*) from tbl_travel";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 권역 목록 얻기
    @Override
    public List<String> getDistricts() {
        List<String> districts = new ArrayList<>();
        String sql = "SELECT DISTINCT(district) FROM tbl_travel ORDER BY district";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                districts.add(rs.getString("district"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return districts;
    }

    private TravelVO map(ResultSet rs) throws SQLException {
        return TravelVO.builder()
                .no(rs.getLong("no"))
                .district(rs.getString("district"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .address(rs.getString("address"))
                .phone(rs.getString("phone"))
                .build();
    }
    @Override
    public List<TravelVO> getTravels() {
        List<TravelVO> travels = new ArrayList<>();
        String sql = "select * from tbl_travel order by district, title";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                TravelVO travel = map(rs);
                travels.add(travel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return travels;
    }


    @Override
    public List<TravelVO> getTravels(int page) {
        List<TravelVO> travels = new ArrayList<>();
        String sql = "select * from tbl_travel order by district, title limit ?,?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int count = 10;
            int start = (page - 1) * count;
            pstmt.setInt(1, start);
            pstmt.setInt(2, count);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TravelVO travel = map(rs);
                    travels.add(travel);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return travels;
    }

    @Override
    public List<TravelVO> getTravels(String district) {
        List<TravelVO> travels = new ArrayList<TravelVO>();
        String sql = "select * from tbl_travel where district = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, district);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TravelVO travel = map(rs);
                    travels.add(travel);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return travels;
    }

    /**
     * 특정 여행지의 상세 정보 + 관련 이미지 조회 메서드
     * - LEFT OUTER JOIN을 사용하여 이미지가 없는 여행지도 조회
     * - Optional을 사용하여 결과의 존재 여부 표현
     *
     * @param no 조회할 여행지 번호
     * @return 여행지 상세 정보와 이미지 목록을 포함한 Optional<TravelVO>
     */
    @Override
    public Optional<TravelVO> getTravel(Long no) {
        TravelVO travel = null;

        String sql = """
                SELECT 
                    t.*, ti.no AS tino, 
                    ti.filename, 
                    ti.travel_no
                FROM 
                    tbl_travel t
                 LEFT OUTER JOIN 
                    tbl_travel_image ti ON t.no = ti.travel_no
                WHERE t.no = ?
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, no); // 여행지 번호 파라미터 바인딩

            try (ResultSet rs = pstmt.executeQuery()) {


                if (rs.next()) {

                    // 첫 번째 행에서 여행지 기본 정보 매핑
                    travel = map(rs);

                    // 이미지 정보를 저장할 List 생성
                    List<TravelImageVO> images = new ArrayList<>();

                    // 조회 결과에서 이미지 정보만 추출
                    try {
                        do {
                            TravelImageVO image = mapImage(rs);
                            images.add(image);
                        } while (rs.next()); // 다음 행으로 이동
                    } catch (SQLException e) {
                        // 이미지가 없는 경우 발생하는 예외 (무시)
                    }

                    travel.setImages(images); // TravelVO 필드에 이미지 리스트 세팅
                    return Optional.of(travel);
                } else {
                    return Optional.empty(); // 여행지가 없다면 빈 Optional 객체 반환
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

