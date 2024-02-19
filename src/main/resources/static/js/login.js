    function goSignup() {
        var signupUrl = '/members/new';
        window.location.href = signupUrl;

    }

    function goLogin() {
        var loginUrl = '/members/login';
        window.location.href = loginUrl;
    }

   function closeAlert() {
       var alertContainer = document.getElementById('successAlert');
       alertContainer.classList.add('fade-out');

       // 2초 후에 창을 완전히 숨김
       setTimeout(function() {
           alertContainer.style.display = 'none';
       }, 2000); // 2초 후에 실행
   }


