document.addEventListener("DOMContentLoaded", function() {
    const signupButton = document.querySelector('button[type="button"]');
    signupButton.addEventListener('click', function() {
        window.location.href = '/auth/register';
    });

    const loginForm = document.getElementById('loginForm');
    const errorMessage = document.getElementById('errorMessage');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(loginForm);
        fetch(loginForm.action, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Login failed');
                }
                return response.json();  // 예상되는 응답 형식이 JSON이라고 가정
            })
            .then(data => {
                localStorage.setItem('userId', data.userId);  // 사용자 ID를 로컬 스토리지에 저장
                window.location.href = '/home';  // 로그인 성공 시 홈 페이지로 이동
            })
            .catch(error => {
                errorMessage.textContent = '회원 ID 또는 비밀번호가 일치하지 않습니다.';  // 오류 메시지 표시
            });
    });
});
