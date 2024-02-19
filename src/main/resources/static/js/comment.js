//댓글 생성
$(document).ready(function() {
    $("#commentForm").submit(function(event) {
        event.preventDefault();
        var userId = $(this).attr("data-user-id");
        var id = $(this).attr("data-id");
        var commentContents = $("textarea[name='contents']").val();

        console.log('id', id);
         // AJAX
        $.ajax({
            url: "/api/comment/" + id,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                contents: commentContents
            }),
            success: function (response) {
                    console.log("댓글 전송 성공:", response);
                    var comment = response.response; // 실제 댓글 데이터는 response.response;
                    // 새로운 댓글 컨테이너 생성
                    var newCommentContainer = $('<div class="comments"></div>');

                    // 새로운 댓글 추가
                    var newComment = '<div class="comment-container">' +
                        '<span class="username">' + comment.username + '</span>' +
                         '<span id="comment-' + comment.id + '" class="contents">' + comment.contents + '</span>' +
                        '<div class="commentUpdate">' +
                        '<a href="#" class="comment-update" onclick="updateComment(' + comment.id + ')">수정</a> ' +
                        '<a href="#" class="comment-delete" onclick="deleteComment(' + comment.id + ')">삭제</a><br>' +
                        '<span class="likeComment">도움됐어요 <a href="#" class="like-comment" onclick="likeComment(' + comment.id + ')"></a></span>' +
                        '</div>';

                    // 수정 폼도 추가
                    newComment += '<div id="updateCommentForm-' + comment.id + '"></div>';

                    // 새로운 댓글을 리스트에 추가
                    var $newComment = $('<li></li>').html(newComment);
                    $("ul#commentList").append($newComment);

                    // 입력 필드 초기화
                    $("textarea[name='contents']").val('');
                    },
                    error: function (error) {
                    console.error("댓글 전송 실패", error.responseText);
                    }
            });
        });
    });



//댓글 수정
function updateComment(commentId) {
    // 해당 댓글의 내용을 가져오기 위해 서버로 요청을 보냅니다.
    fetch(`/api/comment/${commentId}`)
        .then(response => response.json())
        .then(data => {
            // 서버에서 받은 데이터 중 댓글 내용을 가져옵니다.
            const commentResponseDto = data.response; // 서버에서 받은 댓글 응답 데이터
            const commentContents = commentResponseDto.contents; // 댓글 내용

            // 수정 폼의 textarea에 댓글 내용을 채워줍니다.
            const updateCommentForm = document.getElementById(`updateCommentForm-${commentId}`);

            if (updateCommentForm) {
                updateCommentForm.innerHTML = `
                    <textarea id="updatedComment-${commentId}" class="update-comment-textarea" rows="4" cols="50">${commentContents}</textarea>
                    <button type="button" onclick="saveUpdatedComment(${commentId})" class="update-comment-button">저장</button>
                `;
            } else {
                console.error(`ID가 'updateCommentForm-${commentId}'인 요소를 찾을 수 없습니다.`);
            }
        })
        .catch(error => console.error('댓글 가져오기 오류:', error));
}



//댓글 수정 저장
function saveUpdatedComment(commentId){
    const commentElement = document.getElementById(`comment-${commentId}`);
    // 수정된 내용 가져오기
    const updatedContents = document.getElementById(`updatedComment-${commentId}`).value;
    // 서버로 수정된 내용을 PUT 요청으로 전송
    fetch(`/api/comment/${commentId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ contents: updatedContents })
    })
    .then(response => response.json())
    .then(data => {
        console.log('댓글이 성공적으로 업데이트되었습니다:', data);
        // 업데이트된 댓글을 다시 가져와서 화면에 반영
        fetch(`/api/comment/${commentId}`)
            .then(response => response.json())
            .then(data => {
                const updatedCommentContents = data.response.contents;
                // 기존 댓글 요소를 찾아서 내용을 업데이트
                const commentElement = document.getElementById(`comment-${commentId}`);
                if (commentElement) {
                    commentElement.innerText = updatedCommentContents;
                } else {
                    console.error(`ID가 'comment-${commentId}'인 요소를 찾을 수 없습니다.`);
                }

                // 수정 폼 닫기
                const updateCommentForm = document.getElementById(`updateCommentForm-${commentId}`);
                if (updateCommentForm) {
                    updateCommentForm.innerHTML = ''; // 내용 지우기
                } else {
                    console.error(`ID가 'updateCommentForm-${commentId}'인 요소를 찾을 수 없습니다.`);
                }
            })
            .catch(error => console.error('댓글 가져오기 오류:', error));
    })
    .catch(error => console.error('댓글 업데이트 오류:', error));
}



//댓글 삭제
function deleteComment(commentId){
    // 서버로 삭제 요청을 보냅니다.
    fetch(`/api/comment/${commentId}`, {
        method: 'DELETE',
    })
    .then(response => response.json())
    .then(data => {
        console.log('댓글이 성공적으로 삭제되었습니다:', data);
        // 성공적으로 삭제되었으면 해당 댓글과 관련된 요소들을 화면에서 제거합니다.
        const commentElement = document.getElementById(`comment-${commentId}`);
        if(commentElement){
            // commentElement가 포함된 <li> 요소를 찾아서 삭제합니다.
            const listItem = commentElement.closest('li');
            if (listItem) {
                listItem.remove();
            } else {
                console.error('댓글이 포함된 <li> 요소를 찾을 수 없습니다.');
            }
        } else {
            console.error(`ID가 'comment-${commentId}'인 요소를 찾을 수 없습니다.`);
        }
    })
    .catch(error => console.error('댓글 삭제 오류: ', error));
}