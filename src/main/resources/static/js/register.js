document.addEventListener("DOMContentLoaded", function() {
    const memberIdInput = document.getElementById('memberId');
    const nickNameInput = document.getElementById('nickName');
    const passwordInput = document.getElementById('password');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const mailAddressInput = document.getElementById('mailAddress');
    const registerForm = document.getElementById('registerForm');

    memberIdInput.addEventListener('blur', checkMemberIdOrNickname);
    nickNameInput.addEventListener('blur', checkMemberIdOrNickname);
    passwordInput.addEventListener('blur', checkPassword);
    phoneNumberInput.addEventListener('input', formatPhoneNumber);
    mailAddressInput.addEventListener('input', autoCompleteEmail);

    function checkMemberIdOrNickname(event) {
        const input = event.target;
        const value = input.value;
        const id = input.id;
        if (value.length > 15) {
            displayMessage(input, "최대 15자까지 가능합니다.", false);
        } else {
            // Simulating an API request for checking duplication
            fetch(`/api/check/${id}/${value}`) // URL needs to be adjusted according to actual API
                .then(response => response.json())
                .then(data => {
                    if (data.isDuplicate) {
                        displayMessage(input, "사용 불가", false);
                    } else {
                        displayMessage(input, "사용 가능", true);
                    }
                });
        }
    }

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
        const domain = mailAddressInput.value.split('@')[1];
        if (domain !== 'gmail.com' && domain !== 'naver.com') {
            displayMessage(mailAddressInput, "이메일 양식이 올바르지 않습니다.", false);
        } else {
            displayMessage(mailAddressInput, "", true);
        }
    }

    function displayMessage(input, message, isValid) {
        const messageElement = input.nextElementSibling || document.createElement('span');
        messageElement.textContent = message;
        messageElement.style.color = isValid ? 'green' : 'red';
        if (!input.nextElementSibling) {
            input.parentNode.insertBefore(messageElement, input.nextSibling);
        }
    }

    registerForm.addEventListener('submit', submitForm);
});

function submitForm(event) {
    event.preventDefault();
    const formData = {
        memberId: document.getElementById('memberId').value,
        password: document.getElementById('password').value,
        nickName: document.getElementById('nickName').value,
        name: document.getElementById('name').value,
        phoneNumber: document.getElementById('phoneNumber').value.replace(/\D/g, ''),
        mailAddress: document.getElementById('mailAddress').value
    };

    fetch('/api/user/join', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => response.json())
        .then(data => {
            alert('회원가입 완료!');
            window.location.href = '/auth/login';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('회원가입 실패!');
        });
}
