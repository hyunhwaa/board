//검색
function searchBooks() {
    // 검색어 가져오기
    var keyword = document.getElementById("searchInput").value;
    console.log('keyword', keyword);
    // AJAX를 이용하여 서버에 검색 요청 보내기
    $.ajax({
        url: "/api/search",
        method: "GET",
        data: { keyword: keyword },
        success: function (response) {
            // 서버에서 받아온 검색 결과를 이용하여 원하는 동작 수행
            console.log("Search Results:", response);

            // Redirect to the search page with the keyword
            window.location.href = "/book/search?keyword=" + encodeURIComponent(keyword);
        },
        error: function (error) {
            console.error("검색 오류:", error);
        }
    });
}

// 책 상세 페이지
function showBookDetail(element) {
    var isbn = element.getAttribute('data-isbn');
    console.log('선택한 isbn: ' + isbn);


    // AJAX를 이용하여 서버에 상세 정보 요청 보내기
    $.ajax({
        url: "/book/bookDetail/" + isbn,
        method: "GET",
        success: function (response) {
            // 서버에서 받아온 상세 정보를 이용하여 상세 페이지로 이동
            console.log("Book Detail:", response);
            // 여기에서 적절한 상세 페이지로의 이동 로직을 추가할 수 있음
            window.location.href = "/book/bookDetail/" + isbn;
        },
        error: function (error) {
            console.error("Error during fetching book detail:", error);
        }
    });
}

