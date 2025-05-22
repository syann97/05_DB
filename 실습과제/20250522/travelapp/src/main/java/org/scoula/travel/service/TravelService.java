package org.scoula.travel.service;

public interface TravelService {

    // 전체 여행지 목록 출력하기
    void printTravels();

    // 특정 권역의 여행지목록 출력하기
    void printTravelsByDistrict();

    // 특정 페이지의 여행지 목록 출력하기
    void printTravelsByPage();

    // 여행지 상세보기
    void printTravel();
}