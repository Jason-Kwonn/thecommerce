document.addEventListener("DOMContentLoaded", function() {
    const signupButton = document.getElementById('signupButton');
    const loginForm = document.getElementById('loginForm');
    const errorMessage = document.getElementById('errorMessage');

    if (signupButton) {
        signupButton.addEventListener('click', function() {
            window.location.href = '/auth/register';
        });
    }

    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const formData = new FormData(loginForm);
            fetch('http://localhost:8080/api/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    memberId: formData.get('memberId'),
                    password: formData.get('password')
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Login failed');
                    }
                    return response.json();
                })
                .then(data => {
                    localStorage.setItem("id", data.id);
                    window.location.href = '/home';
                })
                .catch(error => {
                    errorMessage.textContent = '회원 ID 또는 비밀번호가 일치하지 않습니다.';
                });
        });
    } else {
        console.error('Login form is not found');
    }
});
