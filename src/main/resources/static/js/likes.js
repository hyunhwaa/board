//게시글 좋아요
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('likeForm').addEventListener('submit', function(event) {
        event.preventDefault(); // 폼의 기본 동작 방지

        var id = document.getElementById('id').value;
        console.log("id: " + id);

        // PUT 요청 보내기
        fetch(`/api/likes/post/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({}) // PUT 요청에 본문(body)은 비어있어도 됨
        })
        .then(response => response.json()) // JSON 형태로 응답 처리
        .then(data => {
            // 응답 처리 (예: 알림 표시, UI 업데이트 등)
            console.log('좋아요 성공:', data);
            // 좋아요 수 업데이트
            document.getElementById('likeCount').innerText = data.response.likeCount;

            // 클릭 아이콘 변경
             var likeButton = document.getElementById('likeButton'); // 버튼 ID로 찾음
                        likeButton.classList.toggle('clicked');

            // 클릭한 후 아이콘 변경
            if (likeButton.classList.contains('clicked')) {
                likeButton.innerHTML = '<img src="/img/click_save_icon.png" alt="liked_icon" style="width: 15px; height: 15px;"> 좋아요';
            } else {
                likeButton.innerHTML = '<img src="/img/save_icon.png" alt="save_icon" style="width: 15px; height: 15px;"> 좋아요';
            }
                    })
        .catch(error => {
            // 오류 처리
            console.error('좋아요 요청 오류:', error);
        });
    });
});

//댓글 좋아요
    function likeComment(commentId) {
        // AJAX 요청을 통해 댓글 좋아요 처리
        fetch(`/api/likes/comment/${commentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({}) // PUT 요청에 본문(body)은 비어있어도 됨
        })
        .then(response => response.json())
        .then(data => {
            console.log('댓글 좋아요 성공:', data);

            // 아이콘의 스타일 변경을 위해 클래스 토글
            var likeButton = document.querySelector('.like-comment');
            likeButton.classList.toggle('clicked');
        })
        .catch(error => {
            console.error('댓글 좋아요 요청 오류:', error);
        });
    }
