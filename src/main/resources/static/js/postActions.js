function deletePost(postId) {
    console.log('postID', postId);
    if (confirm("정말로 삭제하시겠습니까?")) {
        $.ajax({
            url: `/board/debate/post/${postId}`,
            type: 'DELETE',
            dataType: 'html',
            headers: {
            },
            success: function(data) {
                console.log('게시글이 성공적으로 삭제되었습니다:');
                window.location.href = '/board/debate';
            },
            error: function(xhr, status, error) {
                console.error('게시글 삭제 오류:', xhr.status, error);
            }
        });
    }
}