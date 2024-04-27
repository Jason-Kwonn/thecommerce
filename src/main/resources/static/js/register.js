document.addEventListener("DOMContentLoaded", function() {
    const registerForm = document.getElementById('registerForm');
    const passwordInput = document.getElementById('password');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const mailAddressInput = document.getElementById('mailAddress');

    function checkPassword() {
        const regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])([A-Za-z\d!@#$%^&*]{1,20})$/;
        if (!regex.test(passwordInput.value)) {
            displayMessage(passwordInput, "영문, 숫자, 특수문자를 조합한 비밀번호 최대 20자리를 입력해 주세요.", false);
        } else {
            displayMessage(passwordInput, "사용 가능", true);
        }
    }

    function formatPhoneNumber() {
        let numbers = phoneNumberInput.value.replace(/\D/g, '');
        if (numbers.length > 11) {
            numbers = numbers.substring(0, 11);
        }
        phoneNumberInput.value = numbers.replace(/(\d{3})(\d{4})(\d{4})/, '010-$2-$3');
    }

    function autoCompleteEmail() {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 기본 이메일 형식 검증 정규식
        const email = mailAddressInput.value;
        if (!emailRegex.test(email)) {
            displayMessage(mailAddressInput, "이메일 형식이 올바르지 않습니다.", false);
        } else {
            displayMessage(mailAddressInput, "", true); // 올바른 형식일 때는 메시지를 비워줍니다.
        }
    }

    function displayMessage(input, message, isValid) {
        const messageElement = input.parentNode.querySelector('.message');
        messageElement.textContent = message;
        messageElement.style.color = isValid ? 'green' : 'red';
    }


    passwordInput.addEventListener('blur', checkPassword);
    phoneNumberInput.addEventListener('input', formatPhoneNumber);
    mailAddressInput.addEventListener('input', autoCompleteEmail);

    registerForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = {
            memberId: document.getElementById('memberId').value,
            password: document.getElementById('password').value,
            nickName: document.getElementById('nickName').value,
            name: document.getElementById('name').value,
            phoneNumber: phoneNumberInput.value.replace(/\D/g, ''),
            mailAddress: mailAddressInput.value
        };

        fetch('/api/user/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (response.ok) {
                    alert('회원가입 완료!');
                    window.location.href = '/auth/login';
                } else {
                    throw new Error('회원가입 실패');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('회원가입 실패!');
            });
    });
});
